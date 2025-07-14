package com.epam.cora.esa.app;

import com.epam.cora.entity.cluster.MasterPodInfo;
import com.epam.cora.esa.rest.cluster.PodMasterStatusApi;
import com.epam.cora.esa.rest.cluster.PodMasterStatusApiBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(
        name = "ha.deploy.enabled",
        havingValue = "true"
)
public class ScheduledTasksSynchronizationAspect {

    @Value("${kube.master.pod.check.url}")
    private String baseUrl;

    @Value("${kube.current.pod.name}")
    private String currentPodName;

    @Around("@annotation(net.javacrumbs.shedlock.core.SchedulerLock)")
    public void skipScheduledMethodInvocation(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isMasterHost()) {
            joinPoint.proceed();
        } else {
            log.warn("Scheduled method skipped :" + joinPoint.getSignature().toString());
        }
    }

    /**
     * Check if current host is master or not
     *
     * @return true - if a host is master or no master found, false - otherwise
     */
    private boolean isMasterHost() {
        final String masterName = receiveMasterName();
        return masterName == null || masterName.equals(currentPodName);
    }

    /**
     * Obtains a master pod name.
     *
     * @return master name or <code>null</code> if no such found
     */
    private String receiveMasterName() {
        final PodMasterStatusApi masterPodApi = new PodMasterStatusApiBuilder(baseUrl).build();
        try {
            final MasterPodInfo masterInfo = masterPodApi.getMasterName().execute().body();
            return (masterInfo != null) ? masterInfo.getName() : null;
        } catch (IOException e) {
            log.warn("Can't receive master info!");
            return null;
        }
    }
}
