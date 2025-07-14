package com.epam.cora.esa.service.git;

import com.epam.cora.esa.model.git.GitEvent;

public interface GitHookService {

    void processGitEvent(GitEvent event);
}
