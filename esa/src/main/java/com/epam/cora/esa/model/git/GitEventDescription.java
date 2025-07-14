package com.epam.cora.esa.model.git;

import com.epam.cora.esa.model.PipelineEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GitEventDescription {
    private PipelineEvent pipelineEvent;
    private GitEventData eventData;
}
