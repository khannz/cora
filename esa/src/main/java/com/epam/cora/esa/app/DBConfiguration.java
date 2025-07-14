package com.epam.cora.esa.app;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@ImportResource({"classpath*:dao/db-*.xml"})
public class DBConfiguration {
    @Value("${database.url}")
    private String jdbcUrl;

    @Value("${database.username}")
    private String jdbcUsername;

    @Value("${database.password}")
    private String jdbcPassword;

    @Value("${database.driverClass}")
    private String driverClass;

    @Value("${database.max.pool.size}")
    private int maxPoolSize;

    @Value("${database.initial.pool.size}")
    private int initialPoolSize;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setMaxPoolSize(maxPoolSize);
        dataSource.setInitialPoolSize(initialPoolSize);
        return dataSource;
    }
}
