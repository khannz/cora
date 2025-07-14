package com.epam.cora.entity.issue;

import com.epam.cora.vo.EntityVO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Issue {
    private Long id;
    private String name;
    private String text;
    private String author;
    private EntityVO entity;
    private Date createdDate;
    private Date updatedDate;
    private IssueStatus status;
    private List<String> labels;
    private List<IssueComment> comments;
    private List<Attachment> attachments;
}
