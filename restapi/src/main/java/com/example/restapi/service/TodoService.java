package com.example.restapi.service;

import com.example.restapi.dto.reaponse.TodoResponse;
import com.example.restapi.dto.request.TodoCreateRequest;
import com.example.restapi.dto.request.TodoUpdateRequest;

import java.util.List;

public interface TodoService {
    TodoResponse create(TodoCreateRequest todoCreateRequest);
    List<TodoResponse> findAll();
    TodoResponse findById(Long id);
    void delete(Long id);
    TodoResponse update(Long id, TodoUpdateRequest todoUpdateRequest);
}
