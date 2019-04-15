package com.epam.itemcatalog.repository.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
public class EmbeddedDbConfiguration {

    @Bean
    public DataSource dataSource()  throws IOException {
        EmbeddedPostgres postgres = EmbeddedPostgres.start();
        return postgres.getPostgresDatabase();
    }

    @Bean(initMethod = "migrate")
    @DependsOn("dataSource")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure().mixed(true).dataSource(dataSource).load();
    }
}
