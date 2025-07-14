package com.epam.cora.esa.service.impl.converter.issue;

import com.epam.cora.entity.issue.Issue;
import com.epam.cora.entity.security.acl.AclClass;
import com.epam.cora.esa.model.PermissionsContainer;
import com.epam.cora.esa.service.impl.CloudPipelineAPIClient;
import com.epam.cora.esa.service.impl.converter.AbstractCloudPipelineEntityLoader;
import org.springframework.stereotype.Component;


@Component
public class IssueLoader extends AbstractCloudPipelineEntityLoader<Issue> {

    public IssueLoader(final CloudPipelineAPIClient apiClient) {
        super(apiClient);
    }

    @Override
    public Issue fetchEntity(final Long id) {
        return getApiClient().loadIssue(id);
    }

    @Override
    protected String getOwner(final Issue entity) {
        return entity.getAuthor();
    }

    @Override
    protected AclClass getAclClass(final Issue entity) {
        return entity.getEntity().getEntityClass();
    }

    @Override
    protected PermissionsContainer loadPermissions(final Long id, final AclClass entityClass) {
        Issue issue = fetchEntity(id);
        return super.loadPermissions(issue.getEntity().getEntityId(), issue.getEntity().getEntityClass());
    }
}
