package ru.akirakozov.sd.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.akirakozov.sd.mvc.dao.TaskInMemoryDao;

/**
 * @author akirakozov
 */
@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskInMemoryDao taskInMemoryDao() {
        return new TaskInMemoryDao();
    }
}