package com.example.boardjpa.service;

import com.example.boardjpa.entity.Post;
import com.example.boardjpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long id, Post updatedPost) {
        Post post = getPostById(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        postRepository.update(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }
}
