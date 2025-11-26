package com.example.tododbapp.repository;

import com.example.tododbapp.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByTitleContaining(String title);
    List<TodoEntity> findBycompleted(boolean completed);
    void deleteByCompleted(boolean completed);
}
