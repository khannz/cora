package com.epam.cora.entity.log;

import lombok.Builder;
import lombok.Data;

/**
 * Class that contains information about ElasticSearch result page parameters.
 * pageSize - number of entries to be sent back as a search response
 * token - {@link LogEntry} object that represent start of the current page
 */
@Data
@Builder
public class LogPaginationRequest {
    private PageMarker token;
    private Integer pageSize;
}
