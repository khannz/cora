package com.epam.cora.entity.issue;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class IssueComment {
    private Long id;
    private Long issueId;
    private String text;
    private String author;
    private Date createdDate;
    private Date updatedDate;
    private List<Attachment> attachments;
}
