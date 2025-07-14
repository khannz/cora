package com.epam.cora.esa.service.impl.converter.tool;

import com.epam.cora.esa.model.EntityContainer;
import com.epam.cora.esa.model.ToolWithDescription;
import com.epam.cora.esa.service.EntityMapper;
import com.epam.cora.entity.docker.ToolDescription;
import com.epam.cora.entity.docker.ToolVersionAttributes;
import com.epam.cora.entity.scan.ToolDependency;
import com.epam.cora.entity.scan.ToolVersionScanResult;
import com.epam.cora.entity.search.SearchDocumentType;
import com.epam.cora.entity.pipeline.Tool;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.epam.cora.esa.service.ElasticsearchSynchronizer.DOC_TYPE_FIELD;

@Component
public class ToolMapper implements EntityMapper<ToolWithDescription> {

    @Override
    public XContentBuilder map(final EntityContainer<ToolWithDescription> doc) {
        try (XContentBuilder jsonBuilder = XContentFactory.jsonBuilder()) {
            final Tool tool = doc.getEntity().getTool();
            final ToolDescription toolDescription = doc.getEntity().getToolDescription();
            jsonBuilder
                    .startObject()
                    .field(DOC_TYPE_FIELD, SearchDocumentType.TOOL.name())
                    .field("id", tool.getId())
                    .field("registry", tool.getRegistry())
                    .field("registryId", tool.getRegistryId())
                    .field("image", tool.getImage())
                    .field("name", tool.getImage())
                    .field("createdDate", parseDataToString(tool.getCreatedDate()))
                    .field("description", tool.getDescription())
                    .field("shortDescription", tool.getShortDescription())
                    .field("defaultCommand", tool.getDefaultCommand())
                    .field("toolGroupId", tool.getToolGroupId())
                    .field("parentId", tool.getToolGroupId());

            buildLabels(tool.getLabels(), jsonBuilder);
            buildVersions(toolDescription, jsonBuilder);
            buildPackages(toolDescription, jsonBuilder);

            buildUserContent(doc.getOwner(), jsonBuilder);
            buildMetadata(doc.getMetadata(), jsonBuilder);
            buildPermissions(doc.getPermissions(), jsonBuilder);

            jsonBuilder.endObject();
            return jsonBuilder;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create elasticsearch document for tool: ", e);
        }
    }

    private void buildLabels(final List<String> labels, final XContentBuilder jsonBuilder) throws IOException {
        if (!CollectionUtils.isEmpty(labels)) {
            jsonBuilder.array("labels", labels.toArray());
        }
    }

    private void buildVersions(final ToolDescription toolDescription, final XContentBuilder jsonBuilder)
            throws IOException {
        final List<ToolVersionAttributes> versions = toolDescription.getVersions();
        if (CollectionUtils.isNotEmpty(versions)) {
            final String[] versionArray = versions.stream()
                    .map(ToolVersionAttributes::getVersion)
                    .toArray(String[]::new);
            jsonBuilder.array("version", versionArray);
        }
    }

    private void buildPackages(final ToolDescription toolDescription, final XContentBuilder jsonBuilder)
            throws IOException {
        final List<ToolVersionAttributes> versions = toolDescription.getVersions();
        if (CollectionUtils.isNotEmpty(versions)) {
            final String[] toolPackagesNames = versions.stream()
                    .map(ToolVersionAttributes::getScanResult)
                    .filter(Objects::nonNull)
                    .map(ToolVersionScanResult::getDependencies)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .map(ToolDependency::getName)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toArray(String[]::new);
            jsonBuilder.array("packages", toolPackagesNames);
        }
    }
}
