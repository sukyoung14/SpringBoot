package com.example.instagram.service;

import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.entity.Post;

import java.util.List;

public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, Long userId);
    Post findById(Long postId);
    PostResponse getPost(Long postId, Long userId); // userId 파라미터 추가
    List<PostResponse> getAllPosts(Long userId); // userId 파라미터 추가
    List<PostResponse> getPostsByUsername(String username);
    long countByUserId(Long userId);
    List<PostResponse> getAllPostsWithStats(); // 이 메소드는 getAllPosts(userId)로 대체될 예정
}