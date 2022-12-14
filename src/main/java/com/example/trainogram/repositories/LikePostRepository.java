package com.example.trainogram.repositories;

import com.example.trainogram.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost,Long> {
    LikePost findLikeByUserIdAndPostId(Long userId, Long postId);
}
