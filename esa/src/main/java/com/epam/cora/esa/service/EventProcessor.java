package com.epam.cora.esa.service;

import com.epam.cora.esa.model.PipelineEvent;

public interface EventProcessor {
    void process(PipelineEvent event);
}
