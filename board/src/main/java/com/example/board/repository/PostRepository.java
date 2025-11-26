package com.example.board.repository;

import com.example.board.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager em;

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public  Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        String jpql = "SELECT p FROM Post p";
        return em.createQuery(jpql, Post.class).getResultList();
    }

    public Post update(Post post) {
        return em.merge(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public List<Post> findByTitleContaining(String keyword) {
        String jpql = "SELECT p FROM Post p WHERE p.title LIKE :keyword ";
        return em.createQuery(jpql, Post.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    // 1. 비영속 (id가 부여되지 않음): 데이터가 저장되어 있지 않은 상태
    // new Post("title", "content");
    // 2. 영속(id가 부여됨)
    // em.persist(post);
    // 3. 준영속(detached 수정하는 중)
    // em.datach(post)
    // 영속 => 준영속 : datach() , clear()
    // 준영속 => 영속 : merger()
    // 4. 삭제
    // em.remove(post)
}

//package com.example.board.repository;//package com.example.board.repository;
//
//import com.example.board.dto.PostDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class PostRepository {
//    private final JdbcTemplate jdbcTemplate;
////    @RequiredArgsConstructor를 통해 자동생성
//
//    private final RowMapper<PostDto> rowMapper = (rs, rowNum) -> {
//        return new PostDto(
//                rs.getLong("id"),
//                rs.getString("title"),
//                rs.getString("content"),
//                rs.getTimestamp("created_at").toLocalDateTime()
//        );
//    };
//
//    public List<PostDto> findAll() {
//        String sql = "SELECT * FROM post";
//        return jdbcTemplate.query(sql, rowMapper);
//    }
//
//    public PostDto findById(Long id) {
//        String sql = "SELECT * FROM post WHERE id = ?";
//        PostDto post = jdbcTemplate.queryForObject(sql, rowMapper, id);
//        return post;
//    }
//
//    public void save(PostDto post) {
//        String sql = "INSERT INTO post (title, content) VALUES (?, ?)";
//        jdbcTemplate.update(sql, post.getTitle(), post.getContent());
//    }
//
//    public void updateTodoById(Long id, PostDto post) {
//        String sql = "UPDATE post SET title = ?, content = ? where id = ?";
//        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getId());
//    }
//
//    public void deleteTodoById(Long id) {
//        String sql = "DELETE FROM post WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//}
