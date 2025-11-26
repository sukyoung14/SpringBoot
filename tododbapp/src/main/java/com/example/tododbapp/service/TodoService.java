package com.example.tododbapp.service;

import com.example.tododbapp.dto.TodoDto;
import com.example.tododbapp.entity.TodoEntity;
import com.example.tododbapp.exception.ResourceNotFoundException;
import com.example.tododbapp.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    private TodoDto toDto(TodoEntity todoEntity) {
        TodoDto dto = new TodoDto();
        dto.setId(todoEntity.getId());
        dto.setTitle(todoEntity.getTitle());
        dto.setContent(todoEntity.getContent());
        dto.setCompleted(todoEntity.isCompleted());
        return dto;
    }

    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TodoDto getTodoById(Long id) {
        TodoEntity entity = findEntityById(id);
        return toDto(entity);
    }

    private TodoEntity findEntityById(Long id) {
        return  todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found : " + id));
    }

    public TodoDto createTodo(TodoDto dto) {
        validateTitle(dto.getTitle());
        TodoEntity entity = new TodoEntity(dto.getTitle(), dto.getContent(), dto.isCompleted());
        return toDto(todoRepository.save(entity));
    }

    private void validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (title.length() > 50){
            throw new IllegalArgumentException("제목은 50자 미만입니다.");
        }
    }

    public TodoDto updateTodoById(Long id, TodoDto dto) {
        validateTitle(dto.getTitle());

        TodoEntity entity = findEntityById(id);
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setCompleted(dto.isCompleted());

        return toDto(entity);
    }

    public void deleteTodoById(Long id) {
        findEntityById(id);
        todoRepository.deleteById(id);
    }

    public List<TodoDto> searchTodos(String keyword) {
        return todoRepository.findByTitleContaining(keyword).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TodoDto> getTodosByCompleted(boolean completed) {
        return todoRepository.findBycompleted(completed).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TodoDto toggleCompleted(Long id) {
        TodoEntity entity = findEntityById(id);
        entity.setCompleted(!entity.isCompleted());
        return toDto(entity);
    }

    public void deleteCompletedTodos() {
        todoRepository.deleteByCompleted(true);
    }

    public long getTotalCount(){
        return todoRepository.findAll().size();
    }
    public long getCompletedCount(){
        return todoRepository.findBycompleted(true).size();
    }
    public long getActiveCount(){
        return todoRepository.findBycompleted(false).size();
    }
}
