package ru.akirakozov.sd.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.akirakozov.sd.mvc.dao.TaskDao;
import ru.akirakozov.sd.mvc.dao.TaskJdbcDao;

import javax.sql.DataSource;

/**
 * @author akirakozov
 */
@Configuration
public class JdbcDaoContextConfiguration {
    @Bean
    public TaskDao taskDao(DataSource dataSource) {
        return new TaskJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:tasks.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
