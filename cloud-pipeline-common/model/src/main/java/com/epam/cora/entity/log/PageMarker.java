package com.epam.cora.entity.log;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PageMarker {
    private Long id;
    private LocalDateTime messageTimestamp;
}
