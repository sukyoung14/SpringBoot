package com.example.instagram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 30)
    private String email;
    @Column(nullable = false)
    private Role role;
    @Column(length = 200)
    private String bio;

    public User(String username, String password, String email, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.USER;
        this.bio = bio;
    }

    public User(String username, String password, String email, Role role, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role != null ? role : Role.USER;
        this.bio = bio;
    }
}
