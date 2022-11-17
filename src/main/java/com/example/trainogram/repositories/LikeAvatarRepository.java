package com.example.trainogram.repositories;

import com.example.trainogram.entity.LikeAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeAvatarRepository extends JpaRepository<LikeAvatar,Long> {
    
    boolean existsLikeAvatarsByUserId(Long likeId, Long userId);

    void delete(Long avatarId);
}
