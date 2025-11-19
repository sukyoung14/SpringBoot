package com.example.crud;

import com.example.crud.dto.TodoDto;
import com.example.crud.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
    @Bean
    public CommandLineRunner init(TodoRepository todoRepository) {
        return args -> {
            todoRepository.save(new TodoDto(null, "study", "JAVA" , false));
            todoRepository.save(new TodoDto(null, "cook", "kimbob" , false));
            todoRepository.save(new TodoDto(null, "workout", "run" , false));
        };
    }
}
