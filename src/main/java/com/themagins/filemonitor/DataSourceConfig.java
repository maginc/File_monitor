package com.themagins.filemonitor;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(Config.DATABASE_URL);
        dataSourceBuilder.username(Config.DATABASE_USER);
        dataSourceBuilder.password(Config.DATABASE_PASSWORD);
        return dataSourceBuilder.build();
    }
}
