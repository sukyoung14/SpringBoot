package com.example.restapi.dto.reaponse;

import com.example.restapi.dto.MessageReponse;
import com.example.restapi.entity.Todo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TodoResponse {
    private Long id;
    private String title;
    private String content;
    private boolean complete;
    private LocalDateTime createAt;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .complete(todo.isCompleted())
                .createAt(todo.getCreateAt())
                .build();

    }
}
