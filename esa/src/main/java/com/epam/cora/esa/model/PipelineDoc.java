package com.epam.cora.esa.model;

import com.epam.cora.entity.pipeline.Pipeline;
import com.epam.cora.entity.pipeline.Revision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PipelineDoc {

    private Pipeline pipeline;

    private List<Revision> revisions;
}
