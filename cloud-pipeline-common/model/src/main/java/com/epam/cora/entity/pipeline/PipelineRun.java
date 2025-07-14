package com.epam.cora.entity.pipeline;

import com.epam.cora.entity.AbstractSecuredEntity;
import com.epam.cora.entity.configuration.PipeConfValueVO;
import com.epam.cora.entity.pipeline.run.ExecutionPreferences;
import com.epam.cora.entity.pipeline.run.RestartRun;
import com.epam.cora.entity.pipeline.run.RunStatus;
import com.epam.cora.entity.pipeline.run.parameter.PipelineRunParameter;
import com.epam.cora.entity.pipeline.run.parameter.RunSid;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.entity.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class PipelineRun extends AbstractSecuredEntity {

    public static final String PARENT_ID_PARAM = "parent-id";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String PARAM_DELIMITER = "|";
    private static final Pattern PARAMS_REGEXP = Pattern.compile("([a-zA-Z0-9_]*=[a-zA-Z0-9_]*)");

    // Describes the user who actually launch this run, in common case will be the same as owner field,
    // but in case of runAs functionality will hold a name of the user who initially initiate a launch process
    private String originalOwner;

    private Long pipelineId;
    private Date startDate;
    private Date instanceStartDate;
    private String version;
    private Date endDate;
    private TaskStatus status;
    private CommitStatus commitStatus;
    private Date lastChangeCommitTime;
    private String params;

    private String dockerImage;
    private String actualDockerImage;
    private String platform;
    private String cmdTemplate;
    private String actualCmd;
    private Map<String, String> serviceUrl;

    private Boolean terminating = false;
    private Boolean sensitive;
    private String podId;
    private String pipelineName;
    private List<PipelineRunParameter> pipelineRunParameters;
    private RunInstance instance;
    private Long timeout;
    private String repository;
    private String revisionName;
    private String podIP;
    private String sshPassword;
    private String configName;
    private Integer nodeCount;
    private Long parentRunId;
    private List<PipelineRun> childRuns;
    private Boolean initialized;
    private Boolean queued;
    private List<Long> entitiesIds;
    private Long configurationId;
    private String podStatus;
    private List<RunSid> runSids;
    private Map<String, String> envVars;
    /**
     * Last time the notification on long-running pipeline was issued
     */
    private Date lastNotificationTime;
    /**
     * Last time the notification on idle pipeline was issued
     */
    private LocalDateTime lastIdleNotificationTime;
    private LocalDateTime prolongedAtTime;
    private ExecutionPreferences executionPreferences = ExecutionPreferences.getDefault();
    private String prettyUrl;
    /**
     * Pipeline run overall instance price per hour.
     */
    private BigDecimal pricePerHour;
    /**
     * Pipeline instance virtual machine price per hour.
     */
    private BigDecimal computePricePerHour;
    /**
     * Run filesystem price per hour (including size).
     */
    private BigDecimal fsPricePerHour;
    /**
     * Pipeline run instance disk gigabyte price per hour.
     */
    private BigDecimal diskPricePerHour;
    private String stateReasonMessage;
    private List<RestartRun> restartedRuns;
    private List<RunStatus> runStatuses;
    private boolean nonPause;

    /**
     * For CMD runs parent is TOOL, for usual runs - it is a PIPELINE
     */
    @JsonIgnore
    private AbstractSecuredEntity parent;
    private AclClass aclClass = AclClass.PIPELINE;
    private Map<String, String> tags;
    private boolean kubeServiceEnabled;

    public PipelineRun() {
        this.terminating = false;
        this.tags = new HashMap<>();
    }

    public PipelineRun(Long id, String name) {
        super(id, name);
    }

    public Boolean isTerminating() {
        return terminating;
    }

    public void convertParamsToString(Map<String, PipeConfValueVO> parameters) {
        params = parameters.entrySet().stream().map(entry -> {
            String param = entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue().getValue();
            if (StringUtils.hasText(entry.getValue().getType())) {
                param += KEY_VALUE_DELIMITER + (entry.getValue().getType());
            }
            return param;
        }).collect(Collectors.joining(PARAM_DELIMITER));
    }

    public void parseParameters() {
        pipelineRunParameters = new ArrayList<>();
        if (StringUtils.hasText(params)) {
            String[] parts = params.split("\\|");

            pipelineRunParameters = Arrays.stream(parts).map(part -> {
                String[] chunks = part.split(KEY_VALUE_DELIMITER);
                if (chunks.length == 2) {
                    return new PipelineRunParameter(chunks[0], chunks[1]);
                } else if (chunks.length == 3) {
                    return new PipelineRunParameter(chunks[0], chunks[1], chunks[2]);
                }
                return new PipelineRunParameter(part);
            }).collect(Collectors.toList());
        }

        if (parentRunId != null && pipelineRunParameters.stream().noneMatch(p -> p.getName().equals(PARENT_ID_PARAM))) {
            pipelineRunParameters.add(new PipelineRunParameter(PARENT_ID_PARAM, parentRunId.toString()));
        }
    }

    public Map<String, PipeConfValueVO> convertParamsToMap() {
        return ListUtils.emptyIfNull(pipelineRunParameters).stream().collect(Collectors.toMap(PipelineRunParameter::getName, p -> new PipeConfValueVO(p.getValue(), p.getType()), (p1, p2) -> p1));
    }

    public String getTaskName() {
        return StringUtils.isEmpty(pipelineName) ? podId : pipelineName;
    }

    @JsonIgnore
    public LocalDateTime getInstanceStartDateTime() {
        return Optional.ofNullable(getInstanceStartDate()).map(DateUtils::convertDateToLocalDateTime).orElse(null);
    }

    @JsonIgnore
    public void setInstanceStartDateTime(final LocalDateTime date) {
        instanceStartDate = Optional.ofNullable(date).map(DateUtils::convertLocalDateTimeToDate).orElse(null);
    }
}
