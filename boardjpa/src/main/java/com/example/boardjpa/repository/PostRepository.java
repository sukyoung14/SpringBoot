package com.example.boardjpa.repository;

import com.example.boardjpa.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Post> findAll() {
        String jpql = "SELECT p FROM Post p";
        return em.createQuery(jpql, Post.class).getResultList();
    }

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public Post update(Post post) {
        return em.merge(post);
    }

    public  Post findById(Long id) {
        return em.find(Post.class, id);
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
}
