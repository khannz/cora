package com.epam.cora.entity.cluster.pool.filter.instancefilter;

import com.epam.cora.entity.cluster.pool.filter.value.ValueMatcher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RunOwnerPoolInstanceFilter.class, name = "RUN_OWNER"),
        @JsonSubTypes.Type(value = RunOwnerGroupPoolInstanceFilter.class, name = "RUN_OWNER_GROUP"),
        @JsonSubTypes.Type(value = PipelinePoolInstanceFilter.class, name = "PIPELINE_ID"),
        @JsonSubTypes.Type(value = ConfigurationPoolInstanceFilter.class, name = "CONFIGURATION_ID"),
        @JsonSubTypes.Type(value = DockerPoolInstanceFilter.class, name = "DOCKER_IMAGE"),
        @JsonSubTypes.Type(value = ParameterPoolInstanceFilter.class, name = "RUN_PARAMETER")
})
public interface PoolInstanceFilter<T> {

    PoolInstanceFilterOperator getOperator();

    T getValue();

    PoolInstanceFilterType getType();

    @JsonIgnore
    ValueMatcher<T> getMatcher();

    default boolean evaluate(T value) {
        return getOperator().evaluate(getMatcher(), value);
    }

    default boolean evaluate(Collection<T> value) {
        return getOperator().evaluate(getMatcher(), value);
    }
}
