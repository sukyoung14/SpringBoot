package com.example.instagram.service;

import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.entity.Bookmark;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    @Transactional
    public void toggleBookmark(Long postId, Long userId) {
        if (bookmarkRepository.existsByPostIdAndUserId(postId, userId)) {
            bookmarkRepository.deleteByPostIdAndUserId(postId, userId);
        } else {
            User user = userService.findById(userId);
            Post post = postService.findById(postId);
            Bookmark bookmark = Bookmark.builder()
                    .user(user)
                    .post(post)
                    .build();
            bookmarkRepository.save(bookmark);
        }
    }

    @Override
    public boolean isBookmarked(Long postId, Long userId) {
        return bookmarkRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public long getBookmarkCount(Long postId) {
        return bookmarkRepository.countByPostId(postId);
    }

    @Override
    public List<PostResponse> getBookmarkedPosts(Long userId) {
        // 사용자가 존재하는지 먼저 확인
        userService.findById(userId);

        return bookmarkRepository.findByUserId(userId).stream()
                .map(Bookmark::getPost)
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
}
