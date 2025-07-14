package com.epam.cora.esa.rest.git;

import com.epam.cora.esa.model.git.GitEvent;
import com.epam.cora.esa.rest.AbstractRestController;
import com.epam.cora.esa.service.git.GitHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(
        value = "git.hook.endpoint.disable",
        matchIfMissing = true,
        havingValue = "false"
)
@RequiredArgsConstructor
@Slf4j
@RestController
public class GitHookController extends AbstractRestController {

    private final GitHookService gitHookService;

    @PostMapping(value = "/githook/event")
    public ResponseEntity processGitEvent(@RequestBody final GitEvent gitEvent) {
        log.debug("Event: {}", gitEvent);
        gitHookService.processGitEvent(gitEvent);
        return new ResponseEntity(HttpStatus.OK);
    }
}
