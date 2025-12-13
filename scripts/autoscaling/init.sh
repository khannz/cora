#!/usr/bin/env bash

set -euo pipefail
# trap 'echo "Error: $?" >&2; exit 1' ERR

_API_URL="@API_URL@"
_API_TOKEN="@API_TOKEN@"
SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd -P)"

USER_DATA_PREFIX="/run/cora"
mkdir -p "${USER_DATA_PREFIX}"
#
# # MARK: LOCK LOGIC
# LOCK="${USER_DATA_PREFIX}/user-data.lock"
# exec 9>"$LOCK"
#
# if ! flock -n 9; then
#     echo "Already running, exiting." >&2
#     exit 0
# fi
# ### END OF LOCK LOGIC

# MARK: GET CLOUD VARS
get_cloud_vars() {
    local INSTANCE_IDENTITY_FILE="${USER_DATA_PREFIX}/instance-identity.json"
    local TOKEN=$(curl -Ss -XPUT -H "X-aws-ec2-metadata-token-ttl-seconds: 21600" http://169.254.169.254/latest/api/token)

    curl -Ss \
    -H "X-aws-ec2-metadata-token: ${TOKEN}" \
    http://169.254.169.254/latest/dynamic/instance-identity/document \
    >"${INSTANCE_IDENTITY_FILE}"

    CLOUD_REGION="$(jq -r .region "${INSTANCE_IDENTITY_FILE}")"
    CLOUD_INSTANCE_AZ="$(jq -r .availabilityZone "${INSTANCE_IDENTITY_FILE}")"
    CLOUD_INSTANCE_ID="$(jq -r .instanceId "${INSTANCE_IDENTITY_FILE}")"
    CLOUD_INSTANCE_TYPE="$(jq -r .instanceType "${INSTANCE_IDENTITY_FILE}")"
    CLOUD_INSTANCE_IMAGE_ID="$(jq -r .imageId "${INSTANCE_IDENTITY_FILE}")"
    # CLOUD_INSTANCE_IP="$(jq -r .privateIp "${INSTANCE_IDENTITY_FILE}")"
}
### END OF GET CLOUD VARS

USER_DATA_LOG="/var/log/cora"
mkdir -p "${USER_DATA_LOG}"
# set -x
# exec >"${USER_DATA_LOG}/user-data.log" 2>&1

# FS_TYPE="btrfs"
MOUNT_POINT="/ebs"
mkdir -p "${MOUNT_POINT}"

modprobe nfs
modprobe nfsd

FIRST_EBS_VOL="/dev/nvme1n1"
if wipefs -a "${FIRST_EBS_VOL}"; then
    mkfs.btrfs -L data -f -d single "${FIRST_EBS_VOL}"
    udevadm settle
    mount -t btrfs -o noatime LABEL=data "${MOUNT_POINT}"
    btrfs filesystem show "${MOUNT_POINT}"
    mountpoint -q "${MOUNT_POINT}"
fi

rm -rf "${MOUNT_POINT}/lost+found"
mkdir -p "${MOUNT_POINT}/runs" "${MOUNT_POINT}/reference"

# # MARK: FIND EBS VOLUMES
# USER_DATA_VOLUMES_FILE="${USER_DATA_PREFIX}/user-data-volumes"
# rm -f "${USER_DATA_VOLUMES_FILE}"
# touch "${USER_DATA_VOLUMES_FILE}"

# shopt -s nullglob
# for l in /dev/disk/by-id/nvme-Amazon_Elastic_Block_Store_*; do
#     [[ -L "$l" ]] || continue
#     r="$(readlink -f -- "$l")" || continue
#     [[ "$(lsblk -no TYPE "$r" 2>/dev/null)" == "disk" ]] || continue
#     echo "[I] Found a unmounted EBS volume for '/ebs': ${r}"
#     echo "${r}" >> ${USER_DATA_VOLUMES_FILE}
# done
# shopt -u nullglob
# # cat "${USER_DATA_VOLUMES_FILE}"
# ### END OF FIND EBS VOLUMES

systemctl stop docker
mkdir -p /etc/docker/certs.d
jq -n \
    --arg dr "${MOUNT_POINT}/docker" \
    '{
        "data-root": $dr,
        "storage-driver": "btrfs"
    }' >/etc/docker/daemon.json
echo 'STORAGE_DRIVER=' >>/etc/sysconfig/docker-storage
sed -Ei 's/(--default-ulimit[[:space:]+nofile=])[0-9]+:[0-9]+/\165535:65535/g' /etc/sysconfig/docker

get_cloud_vars

cat >/etc/sysconfig/kubelet <<EOL
KUBELET_EXTRA_ARGS=--hostname-override=${CLOUD_INSTANCE_ID} \
--logtostderr=false --log-dir=/var/log/kubelet \
--system-reserved=cpu=300m,memory=250Mi,ephemeral-storage=1Gi \
--kube-reserved=cpu=300m,memory=250Mi,ephemeral-storage=1Gi \
--node-labels=cloud_provider=AWS,cloud-region=${CLOUD_REGION},cloud_ins_id=${CLOUD_INSTANCE_ID},cloud_ins_type=${CLOUD_INSTANCE_TYPE},cloud_az=${CLOUD_INSTANCE_AZ},cloud_image=${CLOUD_INSTANCE_IMAGE_ID} \
--eviction-hard= --eviction-soft= --eviction-soft-grace-period= \
--pod-max-pids=-1 \
--fail-swap-on=true \
--image-pull-progress-deadline=10m \
--enable-cadvisor-json-endpoints
EOL

systemctl daemon-reload
systemctl start docker

for f in /opt/docker-system-images/*.tar; do
    docker load -i $f
done

systemctl enable kubelet
kubeadm join \
--token @KUBE_TOKEN@ @KUBE_IP@ \
--discovery-token-unsafe-skip-ca-verification \
--node-name ${CLOUD_INSTANCE_ID} \
--ignore-preflight-errors all
systemctl start kubelet

chattr -i /etc/resolv.conf
sed -i '/nameserver/d' /etc/resolv.conf
echo 'nameserver 10.96.0.10' >>/etc/resolv.conf
chattr +i /etc/resolv.conf

useradd -m pipeline
cp -r /home/ec2-user/.ssh /home/pipeline/.ssh
chown -R pipeline:pipeline /home/pipeline
chmod 700 /home/pipeline/.ssh
usermod -a -G wheel pipeline
echo 'pipeline ALL=(ALL) NOPASSWD:ALL' >>/etc/sudoers.d/cloud-init

# TODO: deliver filesystem autoscaler script
# TODO: make systemd service for filesystem autoscaler script
# TODO: make systemd timer for filesystem autoscaler script

nc -l -k 8888 &

# echo "${SCRIPT_DIR}"
