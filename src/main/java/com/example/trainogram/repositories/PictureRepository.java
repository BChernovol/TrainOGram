package com.example.trainogram.repositories;

import com.example.trainogram.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
    Optional<Picture> deletePictureByPostId(Long postId);

}
