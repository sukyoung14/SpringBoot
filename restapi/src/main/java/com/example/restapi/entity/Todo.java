package com.example.restapi.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    private boolean completed;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;


    @Builder
    public Todo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}














