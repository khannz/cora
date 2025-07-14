package com.epam.cora.entity.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(
        name = "notification_queue",
        schema = "pipeline"
)
public class NotificationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private NotificationTemplate template;

    @Column(name = "to_user_id")
    private Long toUserId;

    @Convert(converter = UserIdsConverter.class)
    @Column(name = "user_ids")
    private List<Long> copyUserIds;

    @Column(name = "template_parameters")
    @Convert(converter = ParameterConverterJson.class)
    private Map<String, Object> templateParameters;

    public static class ParameterConverterJson implements AttributeConverter<Map<String, Object>, String> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(Map<String, Object> attribute) {
            try {
                return mapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public Map<String, Object> convertToEntityAttribute(String dbData) {
            try {
                return StringUtils.isEmpty(dbData) ? Collections.emptyMap() : mapper.readValue(dbData, new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public static class UserIdsConverter implements AttributeConverter<List<Long>, String> {

        @Override
        public String convertToDatabaseColumn(List<Long> attribute) {
            return attribute.stream().map(Object::toString).collect(Collectors.joining(","));
        }

        @Override
        public List<Long> convertToEntityAttribute(String dbData) {
            return Arrays.stream(dbData.split(",")).filter(s -> !StringUtils.isEmpty(s)).map(Long::parseLong).collect(Collectors.toList());
        }
    }
}
