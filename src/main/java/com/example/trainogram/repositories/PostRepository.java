package com.example.trainogram.repositories;

import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List <Post> findAllByUserUsername(String user);
}
