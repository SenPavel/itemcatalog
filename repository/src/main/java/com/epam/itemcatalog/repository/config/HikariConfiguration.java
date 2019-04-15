package com.epam.itemcatalog.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HikariConfiguration {

    private static final String PROPERTY_FILE_NAME = "/database.properties";

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig(PROPERTY_FILE_NAME);
        return new HikariDataSource(hikariConfig);
    }

}
