package com.example.restapi.service;

import com.example.restapi.dto.reaponse.TodoResponse;
import com.example.restapi.dto.request.TodoCreateRequest;

public interface TodoService {
    TodoResponse create(TodoCreateRequest todoCreateRequest);

}
