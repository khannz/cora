package com.epam.cora.esa.service.git;

import com.epam.cora.esa.model.git.GitEvent;
import com.epam.cora.esa.model.git.GitEventType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GitHookServiceImpl implements GitHookService {

    private final Map<GitEventType, GitEventProcessor> eventProcessors;

    public GitHookServiceImpl(final List<GitEventProcessor> processors) {
        this.eventProcessors = ListUtils.emptyIfNull(processors).stream().collect(Collectors.toMap(GitEventProcessor::supportedEventType, Function.identity()));
    }

    @Override
    public void processGitEvent(final GitEvent event) {
        GitEventType eventType = event.getEventType();
        if (eventType == null) {
            log.error("Unspecified git event type");
            return;
        }
        if (!eventProcessors.containsKey(eventType)) {
            log.error("Unsupported event type: {}", eventType);
            return;
        }

        log.debug("Processing git hook with type: {}", eventType);
        eventProcessors.get(eventType).processEvent(event);
    }
}
