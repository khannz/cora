package com.epam.cora.esa.service.impl.converter;

import com.epam.cora.entity.metadata.MetadataEntry;
import com.epam.cora.entity.metadata.PipeConfValue;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.entity.user.PipelineUser;
import com.epam.cora.esa.exception.EntityNotFoundException;
import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.model.PermissionsContainer;
import com.epam.cora.esa.service.EntityLoader;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.exception.PipelineResponseException;
import com.epam.cora.vo.EntityPermissionVO;
import com.epam.cora.vo.EntityVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

@Getter
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCloudPipelineEntityLoader<T> implements EntityLoader<T> {

    private final CloudPipelineAPIClient apiClient;

    @Override
    public Optional<EntityContainer<T>> loadEntity(final Long id) throws EntityNotFoundException {
        try {
            return Optional.of(buildContainer(id));
        } catch (PipelineResponseException e) {
            log.error(e.getMessage(), e);
            final String errorMessageWithId = buildNotFoundErrorMessage(id);
            log.debug("Expected error message: {}", errorMessageWithId);
            if (e.getMessage().replaceAll("[^\\w\\s]", "").contains(errorMessageWithId)) {
                throw new EntityNotFoundException(e);
            }
            return Optional.empty();
        }
    }

    protected abstract T fetchEntity(Long id);

    protected abstract String getOwner(T entity);

    protected abstract AclClass getAclClass(T entity);

    protected EntityContainer<T> buildContainer(final Long id) throws EntityNotFoundException {
        final T entity = fetchEntity(id);
        if (entity == null) {
            throw new EntityNotFoundException(String.format("%s: Failed to find entity with id %d.", this.getClass().getSimpleName(), id));
        }
        return EntityContainer.<T>builder().entity(entity).owner(loadUser(getOwner(entity))).metadata(loadMetadata(id, getAclClass(entity))).permissions(loadPermissions(id, getAclClass(entity))).build();
    }

    protected PermissionsContainer loadPermissions(final Long id, final AclClass entityClass) {
        PermissionsContainer permissionsContainer = new PermissionsContainer();
        if (entityClass == null) {
            return permissionsContainer;
        }
        EntityPermissionVO entityPermission = apiClient.loadPermissionsForEntity(id, entityClass);

        if (entityPermission != null) {
            String owner = entityPermission.getOwner();
            permissionsContainer.add(entityPermission.getPermissions(), owner);
        }

        return permissionsContainer;
    }

    protected PipelineUser loadUser(final String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return apiClient.loadUserByName(username);
    }

    protected Map<String, String> loadMetadata(final Long id, final AclClass aclClass) {
        if (aclClass == null) {
            return Collections.emptyMap();
        }
        List<MetadataEntry> metadataEntries = apiClient.loadMetadataEntry(Collections.singletonList(new EntityVO(id, aclClass)));
        return prepareMetadataForEntity(metadataEntries);
    }

    protected Map<String, String> prepareMetadataForEntity(final List<MetadataEntry> metadataEntries) {
        Map<String, String> metadata = null;
        if (!CollectionUtils.isEmpty(metadataEntries) && metadataEntries.size() == 1) {
            metadata = Stream.of(MapUtils.emptyIfNull(metadataEntries.get(0).getData())).map(Map::entrySet).flatMap(Set::stream).collect(HashMap::new, (map, entry) -> {
                final String value = Optional.ofNullable(entry.getValue()).map(PipeConfValue::getValue).orElse(null);
                map.put(entry.getKey(), value);
            }, HashMap::putAll);
        }
        return MapUtils.emptyIfNull(metadata);
    }

    protected String buildNotFoundErrorMessage(final Long id) {
        return String.format("%d was not found", id);
    }
}
