package com.epam.cora.entity.configuration;

import com.epam.cora.entity.pipeline.run.PipelineStart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Configuration entry for {@link ExecutionEnvironment#CLOUD_PLATFORM}, supports both:
 * pipeline run and tool/service run
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunConfigurationEntry extends AbstractRunConfigurationEntry {
    private ExecutionEnvironment executionEnvironment = ExecutionEnvironment.CLOUD_PLATFORM;
    private Long pipelineId;
    private String pipelineVersion;

    @Delegate(excludes = AbstractRunConfigurationEntry.class)
    @JsonIgnore
    private ConfigurationEntry configurationEntry;

    public RunConfigurationEntry() {
        this.configurationEntry = new ConfigurationEntry();
    }

    @Override
    public boolean checkConfigComplete() {
        if (!StringUtils.hasText(getName())) {
            return false;
        }

        if (pipelineId != null && !StringUtils.hasText(pipelineVersion)) {
            return false;
        }

        if (pipelineId == null &&
                (configurationEntry.getConfiguration() == null ||
                        !StringUtils.hasText(configurationEntry.getConfiguration().getCmdTemplate()))) {
            return false;
        }
        return true;
    }

    @Override
    public PipelineStart toPipelineStart() {
        PipelineStart startVO = new PipelineStart();
        startVO.setPipelineId(getPipelineId());
        startVO.setVersion(getPipelineVersion());
        startVO.setConfigurationName(getConfigName());
        startVO.setRunSids(getRunSids());
        PipelineConfiguration configuration = getConfiguration();
        if (configuration != null) {
            startVO.setCloudRegionId(configuration.getCloudRegionId());
            startVO.setIsSpot(configuration.getIsSpot());
            startVO.setCmdTemplate(configuration.getCmdTemplate());
            startVO.setDockerImage(configuration.getDockerImage());
            if (configuration.getInstanceDisk() != null) {
                startVO.setHddSize(Integer.parseInt(configuration.getInstanceDisk()));
            }
            startVO.setInstanceType(configuration.getInstanceType());
            startVO.setNodeCount(configuration.getNodeCount());
            startVO.setParams(configuration.getParameters());
            startVO.setTimeout(configuration.getTimeout());
            startVO.setWorkerCmd(configuration.getWorkerCmd());
            if (!StringUtils.hasText(startVO.getWorkerCmd())) {
                startVO.setWorkerCmd(configuration.getCmdTemplate());
            }
            startVO.setNonPause(configuration.isNonPause());
        }
        return startVO;
    }

    @Override
    public Integer getWorkerCount() {
        return getConfiguration() == null ? null : getConfiguration().getNodeCount();
    }

    public void setDefaultConfiguration(boolean defaultConfiguration) {
        Optional.ofNullable(configurationEntry)
                .ifPresent(conf -> conf.setDefaultConfiguration(defaultConfiguration));
    }

    public boolean isDefaultConfiguration() {
        return Optional.ofNullable(configurationEntry)
                .map(ConfigurationEntry::getDefaultConfiguration)
                .orElse(false);
    }
}
