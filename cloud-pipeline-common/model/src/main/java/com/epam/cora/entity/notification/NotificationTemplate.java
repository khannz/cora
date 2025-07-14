package com.epam.cora.entity.notification;

import com.epam.cora.entity.notification.NotificationSettings.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification_template", schema = "pipeline")
@NoArgsConstructor
public class NotificationTemplate {

    private static final String EMPTY = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String subject;

    private String body;

    public NotificationTemplate(Long id) {
        this.setId(id);
    }

    public static NotificationTemplate getDefault(NotificationType type) {
        NotificationTemplate template = new NotificationTemplate(type.getId());
        template.setName(type.name());
        template.setBody(EMPTY);
        template.setSubject(EMPTY);
        return template;
    }
}
