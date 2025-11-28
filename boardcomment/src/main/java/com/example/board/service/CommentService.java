package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.entity.Post;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public Comment createComment(@PathVariable Long postId, @ModelAttribute Comment comment) {
        Post post = postService.getPostById(postId);

        System.out.println("=== 댓글 추가 전===");
        System.out.println("댓글 수 : " + post.getComments().size());

        //comment.setPost(post);
        post.addComment(comment);
        Comment saved = commentRepository.save(comment);

        System.out.println("=== 댓글 추가 후===");
        System.out.println("댓글 수 : " + post.getComments().size());

        return saved;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow();
        // 고아 객체 삭제
        //Post post = comment.getPost();
        //post.removeComment(comment);

        commentRepository.delete(comment);
    }
}
