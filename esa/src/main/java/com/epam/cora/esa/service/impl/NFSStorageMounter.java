package com.epam.cora.esa.service.impl;

import com.epam.cora.cmd.CmdExecutionException;
import com.epam.cora.cmd.CmdExecutor;
import com.epam.cora.cmd.PlainCmdExecutor;
import com.epam.cora.entity.datastorage.AbstractDataStorage;
import com.epam.cora.entity.datastorage.FileShareMount;
import com.epam.cora.entity.datastorage.MountType;
import com.epam.cora.entity.datastorage.NFSDataStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class NFSStorageMounter {

    private static final String NFS_MOUNT_CMD_PATTERN = "sudo mount -t %s %s %s %s";
    private static final String LUSTRE_MGS_NID_REGEX = "([^:]+(:\\d+)?@\\w+)";
    private static final Pattern NFS_LUSTRE_ROOT_PATTERN = Pattern.compile(String.format("^%1$s(:%1$s)*(:\\/)[^\\/]+", LUSTRE_MGS_NID_REGEX));
    private static final String PATH_SEPARATOR = "/";
    private static final String NFS_HOST_DELIMITER = ":/";
    private static final String OPTIONS_FLAG = "-o ";

    private final CloudPipelineAPIClient cloudPipelineAPIClient;
    private final CmdExecutor cmdExecutor = new PlainCmdExecutor();

    public synchronized boolean tryToMountStorage(final NFSDataStorage storage, final File mountFolder) {
        Assert.isTrue(mountFolder.mkdirs(), "Could not mount NFS: unable to create a directory!");
        final String mountCommand = buildMountCommand(storage, mountFolder);
        try {
            cmdExecutor.executeCommand(mountCommand);
            return true;
        } catch (CmdExecutionException executionException) {
            log.error("Could not mount NFS using [{}]: {} ", mountCommand, executionException.getMessage());
            tryRemoveFolder(storage, mountFolder);
            return false;
        }
    }

    private void tryRemoveFolder(final NFSDataStorage storage, final File mountFolder) {
        try {
            deleteFolderIfEmpty(mountFolder);
        } catch (IOException cleanupException) {
            log.error(String.format("Unable to remove mount point folder [%s] after [%s] mount failure.", mountFolder.getAbsoluteFile(), storage.getPath()));
        }
    }

    private String buildMountCommand(final NFSDataStorage storage, final File mountFolder) {
        final FileShareMount fileShareMount = cloudPipelineAPIClient.loadFileShareMount(storage.getFileShareMountId());
        final String protocol = fileShareMount.getMountType().getProtocol();
        return String.format(NFS_MOUNT_CMD_PATTERN, protocol, buildMountOptions(storage), formatNfsPath(storage.getPath(), protocol), mountFolder.getAbsolutePath());
    }

    private String buildMountOptions(final AbstractDataStorage dataStorage) {
        return Optional.ofNullable(dataStorage.getMountOptions()).map(StringUtils::trimToEmpty).filter(StringUtils::isNotEmpty).map(options -> OPTIONS_FLAG + options).orElse(StringUtils.EMPTY);
    }

    private void deleteFolderIfEmpty(final File folder) throws IOException {
        final String[] files = folder.list();
        if (ArrayUtils.isEmpty(files)) {
            FileUtils.deleteDirectory(folder);
        }
    }

    private String formatNfsPath(final String path, final String protocol) {
        if (protocol.equalsIgnoreCase(MountType.LUSTRE.getProtocol())) {
            if (!NFS_LUSTRE_ROOT_PATTERN.matcher(path).find()) {
                throw new IllegalArgumentException("Invalid Lustre path format!");
            } else if (path.endsWith(PATH_SEPARATOR)) {
                return StringUtils.chop(path);
            }
        }
        if (protocol.equalsIgnoreCase(MountType.NFS.getProtocol()) && !path.contains(NFS_HOST_DELIMITER)) {
            return path.replaceFirst(PATH_SEPARATOR, NFS_HOST_DELIMITER);
        }
        return path;
    }
}
