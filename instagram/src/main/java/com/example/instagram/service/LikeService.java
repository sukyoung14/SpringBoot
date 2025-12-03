package com.example.instagram.service;

public interface LikeService {
    void toggleLike(Long postId, Long userId);
    boolean isLike(Long postId, Long userId);
    long getLikeCount(Long postId);
}
