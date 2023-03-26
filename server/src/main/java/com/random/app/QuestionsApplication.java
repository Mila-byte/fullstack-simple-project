package com.random.app;

import controller.QuestionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import repository.ConnectionSingleton;
import repository.QuestionRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@ComponentScan({"controller", "repository"})
public class QuestionsApplication {


    public static void main(String[] args) throws SQLException {
        SpringApplication.run(QuestionsApplication.class, args);
    }

}
