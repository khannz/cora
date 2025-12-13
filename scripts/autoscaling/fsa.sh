#!/usr/bin/env bash

#set -euo pipefail

RUNDIR=/run/cora
cleanup() { rm -f ${RUNDIR}/*; }
trap cleanup EXIT INT TERM

if [[ -z "${FSA_API}" ]] || [[ -z "${FSA_API_TOKEN}" ]] || [[ -z "${FSA_NODE}" ]] || [[ -z "${FSA_MOUNT_POINT}" ]]; then
    echo -e "\tFSA_API:\t${FSA_API}"
    echo -e "\tFSA_API_TOKEN:\t${FSA_API_TOKEN}"
    echo -e "\tFSA_NODE:\t${FSA_NODE}"
    echo -e "\tFSA_MOUNT_POINT:\t${FSA_MOUNT_POINT}"
    exit 1
fi

all_disks() { lsblk -sndo NAME,TYPE |awk '$2=="disk"{print "/dev/"$1}' |sort; }
btrfs_members() { btrfs filesystem show --raw 2>/dev/null |awk '/path[[:space:]]/ {print $NF}' |sort -u; }

LOG_TASK=FilesystemAutoscaling

HEADERS=(
    -H 'Accept: application/json'
    -H 'Content-Type: application/json'
    -H "Authorization: Bearer ${FSA_API_TOKEN}"
)

RUNID_FILE="${RUNDIR}/run-id"
curl -Ss -XGET "${HEADERS[@]}" "${FSA_API}/cluster/node/${FSA_NODE}/run" |jq -r ".payload.runId // empty" >"${RUNID_FILE}"

PREF_FILE="${RUNDIR}/preferences.json"
curl -Ss -XGET "${HEADERS[@]}" "${FSA_API}/preferences" |jq --tab -r '.payload | [ .[] | {name, value: (try (.value | fromjson) catch .value?)} ]' >"${PREF_FILE}"

AUTOSCALING_ENABLED=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.enabled") | .value) // false' ${PREF_FILE})
MONITORING_DELAY=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.monitoring.delay") | .value) // 10' ${PREF_FILE})
DELTA=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.delta.ratio") | .value) // 0.5' ${PREF_FILE})
MAX_DEVICE_NUMBER=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.max.devices") | .value) // 40' ${PREF_FILE})
MAX_FS_SIZE=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.max.size") | .value) // 16384' ${PREF_FILE})
MIN_DISK_SIZE=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.disk.min.size") | .value) // 10' ${PREF_FILE})
MAX_DISK_SIZE=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.disk.max.size") | .value) // 16384' ${PREF_FILE})
THRESHOLD_RATIO=$(jq -r '(.[] | select(.name=="cluster.instance.hdd.scale.threshold.ratio") | .value) // 0.75' ${PREF_FILE})
THRESHOLD=$(echo "${THRESHOLD_RATIO#*.}")

[[ ! ${AUTOSCALING_ENABLED} == "true" ]] && exit 0

CURRENT_USAGE=$(findmnt -n -o USE% ${FSA_MOUNT_POINT} |awk 'NR==1{print 0+$1}')
[[ "${CURRENT_USAGE}" -lt "${THRESHOLD}" ]] && exit 0

CURRENT_SIZE=$(df --output=size -BG ${FSA_MOUNT_POINT} |awk 'NR==2{ sub(/G$/,""); print $1}')
TARGET_SIZE=$(echo "(${CURRENT_SIZE} + ${CURRENT_SIZE} * ${DELTA})/1" |bc)
[[ "${TARGET_SIZE}" -ge "${MAX_FS_SIZE}" ]] && exit 0

MOUNTED_DEVICES_COUNT=$(btrfs filesystem show --raw ${FSA_MOUNT_POINT} |grep -c '^\s*devid')
[[ "${MOUNTED_DEVICES_COUNT}" -ge "${MAX_DEVICE_NUMBER}" ]] && exit 0

ADDITIONAL_DISK_SIZE=$(( TARGET_SIZE - CURRENT_SIZE ))
# FIXME: this would always return 0 exit code no matter what
curl -Ss -XPOST "${HEADERS[@]}" "${FSA_API}/run/$(cat ${RUNID_FILE}")/disk/attach --json "{\"size\":\"${ADDITIONAL_DISK_SIZE}\"}"
udevadm settle

mapfile -t newdevs < <(comm -23 <(all_disks) <(btrfs_members))

for dev in "${newdevs[@]}"; do
    btrfs device add "${dev}" "${FSA_MOUNT_POINT}"
done

echo "Disk for ${FSA_MOUNT_POINT} has been scaled up!"
