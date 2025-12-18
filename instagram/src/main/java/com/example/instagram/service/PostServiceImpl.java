package com.example.instagram.service;

import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.repository.BookmarkRepository;
import com.example.instagram.repository.CommentRepository;
import com.example.instagram.repository.LikeRepository;
import com.example.instagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository; // BookmarkRepository 주입

    @Override
    @Transactional
    public PostResponse create(PostCreateRequest postCreateRequest, Long userId) {
        User user = userService.findById(userId);

        Post post = Post.builder()
                .content(postCreateRequest.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    @Override
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow();
    }

    @Override
    public PostResponse getPost(Long postId, Long userId) {
        Post post = findById(postId);
        return createPostResponse(post, userId);
    }

    @Override
    public List<PostResponse> getAllPosts(Long userId) {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(post -> createPostResponse(post, userId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userService.findByUsername(username);
        // 로그인한 사용자의 ID가 필요하므로, 이 메소드는 컨트롤러에서 userId를 받아오도록 수정 필요
        // 임시로 0L을 사용하거나, 인증된 사용자 정보를 가져오는 로직 추가 필요
        return postRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(post -> createPostResponse(post, 0L)) // 임시로 userId 0L 사용
                .collect(Collectors.toList());
    }

    @Override
    public long countByUserId(Long userId) {
        return postRepository.countByUserId(userId);
    }

    @Override
    public List<PostResponse> getAllPostsWithStats() {
        // 이 메소드는 새로운 getAllPosts(Long userId)로 대체되므로,
        // 호환성을 위해 임시로 userId 0L (비로그인 사용자)를 기준으로 데이터를 반환
        return getAllPosts(0L);
    }

    private PostResponse createPostResponse(Post post, Long userId) {
        long commentCount = commentRepository.countByPostId(post.getId());
        long likeCount = likeRepository.countByPostId(post.getId());
        boolean isLiked = userId != 0L && likeRepository.existsByPostIdAndUserId(post.getId(), userId);
        long bookmarkCount = bookmarkRepository.countByPostId(post.getId());
        boolean isBookmarked = userId != 0L && bookmarkRepository.existsByPostIdAndUserId(post.getId(), userId);

        return PostResponse.from(post, commentCount, likeCount, isLiked, isBookmarked, bookmarkCount);
    }
}