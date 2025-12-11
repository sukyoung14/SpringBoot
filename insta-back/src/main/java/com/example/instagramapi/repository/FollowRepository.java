package com.example.instagramapi.repository;

import com.example.instagramapi.entity.Follow;
import com.example.instagramapi.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends CrudRepository<Follow, Long> {
    // 팔로우 정보 조회
    Optional<Follow> findByFollowerIdAndFollowingId(Long followingId, Long followerId);

    // 팔로우 여부 확인
    boolean existsByFollowerIdAndFollowingId(Long followingId, Long followerId);

    // 팔로우 수
    long countByFollowingId(Long followingId);
    
    // 팔로잉 수
    long countByFollowerId(Long followerId);

    // 팔로잉 ID 목록 조회
    @Query("SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId")
    List<Long> findFollowingIdsByFollowerId(@Param("userId") Long followerId);
    
    // 팔로월 목록 (나를 팔로우 하는 사람들)
    @Query("SELECT f FROM Follow f JOIN FETCH f.follower WHERE f.following.id = :userId")
    List<Follow> findFollowersByFollowingId(@Param("userId") Long followerId);
    // 팔로잉 목록 (내가 팔로우 하는 사람들)
    @Query("SELECT f FROM Follow f JOIN FETCH f.following WHERE f.follower.id = :userId")
    List<Follow> findFollowingsByFollowerId(@Param("userId") Long followerId);
}
