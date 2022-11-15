package com.example.trainogram.serviceImpl;

import com.example.trainogram.dto.PostDTO;
import com.example.trainogram.entity.Picture;
import com.example.trainogram.entity.Post;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status436PostNotFoundException;
import com.example.trainogram.repositories.PostRepository;
import com.example.trainogram.services.PostPictureService;
import com.example.trainogram.services.PostService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    private final PostPictureService postPictureService;


    @Autowired
    public PostServiceImp(PostRepository postRepository, UserService userService, PostPictureService postPictureService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postPictureService = postPictureService;
    }

    @Override
    public List<Post> findAll() throws Status436PostNotFoundException {
        if(postRepository.findAll().isEmpty()){
            throw new Status436PostNotFoundException("Posts not exist");
        }
        return postRepository.findAll();
    }

    @Override
    public List<Post> getByUsername(String token) throws Status436PostNotFoundException {
        String username = userService.getAuthenticatedUser(token).get().getUsername();
        if(postRepository.findAllByUserUsername(username).isEmpty()){
            throw new Status436PostNotFoundException("Posts by username " + username + " not found");
        }
        return  postRepository.findAllByUserUsername(username);
    }

    public Optional<Post> findById(Long id) throws Status436PostNotFoundException { //TODO
        if(postRepository.findById(id).isEmpty()){
            throw new Status436PostNotFoundException("Post with id " + id + " not found");
        }
        return postRepository.findById(id);
    }



    @Transactional
    @Override
    public void deletePost(Long postId, String token) throws Status436PostNotFoundException, Status427UserHasNotRootException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();
        Post post = postRepository.findById(postId).orElseThrow(() -> new Status436PostNotFoundException("Post not found"));
        if(post == null){
            throw new Status436PostNotFoundException("Post with id " + postId + " not found");
        }
        if(isPostRelatedToUser(userId, post)){
            postRepository.deleteById(postId);
        }else {
            throw new Status427UserHasNotRootException("User is not the owner of the postUser");
        }

    }


    @Override
    public Post createPost(PostDTO postDTO, String token) throws IOException {
        Post post = Post.builder()
                .text(postDTO.getText())
                .pictureList(new ArrayList<>())
                .createDate(new Date())
                .user(userService.getAuthenticatedUser(token).get())
                .build();
        postRepository.save(post);
        post.setPictureList(postPictureService.uploadImageToFileSystem(post, postDTO.getMultipartFile(),token));
        return post;
    }


    @Transactional
    @Override
    public void updatePost(Long id, PostDTO postDTO, String token) throws Status436PostNotFoundException, Status427UserHasNotRootException, IOException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();
        Post post = postRepository.findById(id).orElseThrow(() -> new Status436PostNotFoundException("Post with id " + id + " not found"));

        if(!isPostRelatedToUser(userId, post)){
            throw new Status427UserHasNotRootException("User with id " + userId + " no root");
        }else {
            post.setText(postDTO.getText());
            post.setCreateDate(new Date());
            for (Picture picture:post.getPictureList()) {
                File file = new File(picture.getPath());
                file.delete();
            }
            postPictureService.deletePicture(id);
            for( Picture picture : postPictureService.uploadImageToFileSystem(post,postDTO.getMultipartFile(),token)){
                postPictureService.savePicture(picture);
            }
        }
    }


    public boolean isPostRelatedToUser(Long userId, Post post){
        return post.getUser().getId().equals(userId);
    }

}
