package com.example.trainogram.services;

import com.example.trainogram.dto.PostDTO;
import com.example.trainogram.entity.Post;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status436PostNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface PostService {

    List<Post> findAll() throws Status436PostNotFoundException;

    List <Post> getByUsername(String token) throws Status436PostNotFoundException;

    void deletePost(Long id, String token) throws Status436PostNotFoundException, Status427UserHasNotRootException;

    Post createPost(PostDTO postDTO, String token) throws IOException;

    void updatePost(Long id, PostDTO postDTO, String token) throws Status436PostNotFoundException, Status427UserHasNotRootException, IOException;

    Optional<Post> findById(Long id) throws Status436PostNotFoundException;
    boolean isPostRelatedToUser(Long userId, Post post);

}
