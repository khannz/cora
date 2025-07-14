package com.epam.cora.entity.filter;

import java.util.List;

public interface AclSecuredFilter {
    void setAllowedPipelines(List<Long> pipelineIds);

    List<Long> getAllowedPipelines();

    void setOwnershipFilter(String owner);

    String getOwnershipFilter();
}
