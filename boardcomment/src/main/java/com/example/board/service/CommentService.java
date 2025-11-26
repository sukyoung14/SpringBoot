package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(@PathVariable Long postId, @ModelAttribute Comment comment) {
        commentRepository.save(comment);
        return comment;
    }
}
}
