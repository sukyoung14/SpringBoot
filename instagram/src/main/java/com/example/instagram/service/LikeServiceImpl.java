package com.example.instagram.service;

import com.example.instagram.entity.Like;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    @Override
    @Transactional
    public void toggleLike(Long postId, Long userId){
        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, userId);

        if (existingLike.isPresent()){
            likeRepository.delete(existingLike.get());
        }
        else {
            Post post = postService.findById(postId);
            User user = userService.findById(userId);

            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();

            likeRepository.save(like);
        }
    }
    public boolean isLike(Long postId, Long userId){
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }
    public long getLikeCount(Long postId){
        return likeRepository.countByPostId(postId);
    }

}
