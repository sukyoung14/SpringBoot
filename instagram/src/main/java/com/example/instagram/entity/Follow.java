package com.example.instagram.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="follow_id", nullable=false)
    private User follower;
    @ManyToOne
    @JoinColumn(name="following_id", nullable=false)
    private User following;

    @Builder
    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }
}
