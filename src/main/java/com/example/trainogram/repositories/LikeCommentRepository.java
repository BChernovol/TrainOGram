package com.example.trainogram.repositories;

import com.example.trainogram.entity.LikeComment;
import com.example.trainogram.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    LikeComment findLikeByUserIdAndCommentId(Long userId, Long commentId);
}
