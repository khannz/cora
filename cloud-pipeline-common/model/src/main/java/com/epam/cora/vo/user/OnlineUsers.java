package com.epam.cora.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineUsers {
    private Long id;
    private LocalDateTime logDate;
    private List<Long> userIds;
}
