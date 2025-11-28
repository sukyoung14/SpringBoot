package com.example.board.repository;

import com.example.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    // 개본 CRUD 메서드
    // 저장 (INSERT or UPDATE)
    // Post save(Post entity)

    // 조회
    // Optional<Post> findById(Long id);
    // List<Post> findAll();
    // List<Post> findAll(Sort sort);

    // 삭제
    // void deleteById(Long id);
    // void delete(Post entity);

    // 개수 조회
    // logn count();
    // 존재 여부 확인
    // boolean existsByid(Long id);

    // findBy + 필드명 + 조건
    // LIKE %keyword%
    List<Post> findByTitleContaining(String keyword);

    // @Query 방식
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> searchByTitle(@Param("keyword") String keyword);

    // Like keyword%
    List<Post> findByTitleStartingWith(String keyword);

    // >
    List<Post> findByIdGreaterThan(Long id);

    // ORDER BY ID DESC
    List<Post> findByOrderByIdDesc();
    
    // title or content 으로 검색
    List<Post> findByTitleContainingOrContentContaining(String TitleKeyword, String ContentKeyword);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE  %:keyword%")
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    @Query(value="""
         SELECT * FROM  post 
         WHERE title LIKE %:keyword% 
         ORDER BY id DESC
    """, nativeQuery = true)
    List<Post> searchByTitleNative(@Param("keyword") String keyword);


    List<Post> findTop3ByOrderByCreatedAtDesc();
    @Query("""
        SELECT p FROM Post p
        ORDER BY p.createdAt DESC
    """)
    List<Post> findRecentPosts(Pageable pageable);

    @Query(value="""
         SELECT * FROM  post 
         ORDER BY created_at DESC
         LIMIT 3
    """, nativeQuery = true)
    List<Post> findRecentPostsNative();

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
