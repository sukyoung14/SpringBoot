package com.example.tododbapp;

import com.example.tododbapp.entity.TodoEntity;
import com.example.tododbapp.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TododbappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TododbappApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(TodoRepository todoRepository) {
        return args -> {
            todoRepository.save(new TodoEntity( "study", "JAVA" , false));
            todoRepository.save(new TodoEntity("cook", "kimbob" , false));
            todoRepository.save(new TodoEntity("workout", "run" , false));
        };
    }

}
