package com.example.instagram.dto.response;


import com.example.instagram.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private Long userId;
    private String username;

    private long commentCount;
    private long likeCount;
    private boolean isLiked;      // 좋아요 여부 추가
    private boolean isBookmarked; // 북마크 여부 추가
    private long bookmarkCount; // 북마크 수 추가

    // Entity => DTO 변환
    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .commentCount(0)
                .likeCount(0)
                .isLiked(false)
                .isBookmarked(false)
                .bookmarkCount(0)
                .build();
    }

    public static PostResponse from(Post post, long commentCount, long likeCount) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .commentCount(commentCount)
                .likeCount(likeCount)
                .isLiked(false)
                .isBookmarked(false)
                .bookmarkCount(0)
                .build();
    }

    // 좋아요, 북마크 정보까지 포함하는 새로운 from 메소드
    public static PostResponse from(Post post, long commentCount, long likeCount, boolean isLiked, boolean isBookmarked, long bookmarkCount) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .commentCount(commentCount)
                .likeCount(likeCount)
                .isLiked(isLiked)
                .isBookmarked(isBookmarked)
                .bookmarkCount(bookmarkCount)
                .build();
    }
}
