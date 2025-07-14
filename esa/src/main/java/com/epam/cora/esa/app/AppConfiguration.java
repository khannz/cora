package com.epam.cora.esa.app;

import com.epam.cora.client.pipeline.CloudPipelineApiExecutor;
import com.epam.cora.client.pipeline.RetryingCloudPipelineApiExecutor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT3M")
public class AppConfiguration {

    @Bean(name = "elasticsearchAgentThreadPool")
    public ExecutorService elasticsearchAgentThreadPool(final @Value("${sync.submit.threads:1}") int submitThreads) {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(submitThreads);
        pool.prestartAllCoreThreads();
        return pool;
    }

    @Bean(name = "lockProvider")
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }

    @Bean
    public CloudPipelineApiExecutor cloudPipelineApiExecutor(@Value("${cloud.pipeline.retry.attempts:3}") int retryAttempts, @Value("${cloud.pipeline.retry.delay:5000}") int retryDelay) {
        return new RetryingCloudPipelineApiExecutor(retryAttempts, Duration.ofMillis(retryDelay));
    }
}
