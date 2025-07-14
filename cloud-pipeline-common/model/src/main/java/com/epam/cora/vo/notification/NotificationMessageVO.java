package com.epam.cora.vo.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Custom notification view object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessageVO {

    /**
     * Notification subject.
     */
    private String subject;

    /**
     * Notification body.
     */
    private String body;

    /**
     * Template parameters that will be used to fill the subject and the body of the notification.
     */
    private Map<String, Object> parameters;

    /**
     * User name of the notification receiver.
     */
    private String toUser;

    /**
     * User names of ones who are in the notification CC list.
     */
    private List<String> copyUsers;
}
