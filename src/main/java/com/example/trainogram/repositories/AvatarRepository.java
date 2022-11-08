package com.example.trainogram.repositories;

import com.example.trainogram.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findAvatarById(Long avatarId);
}
