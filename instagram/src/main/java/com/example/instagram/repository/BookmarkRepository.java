package com.example.instagram.repository;

import com.example.instagram.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // 특정 사용자의 북마크 목록 조회
    List<Bookmark> findByUserId(Long userId);

    // 특정 게시물이 북마크 되었는지 확인
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    // 특정 게시물에 대한 특정 사용자의 북마크 삭제
    void deleteByPostIdAndUserId(Long postId, Long userId);

    // (LikeRepository와 유사하게) 특정 게시물의 북마크 개수 조회
    long countByPostId(Long postId);
}
