package com.example.trainogram.conrtollers;

import com.example.trainogram.dto.PostDTO;
import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.SponsorPost;
import com.example.trainogram.exception.*;
import com.example.trainogram.services.LikePostService;
import com.example.trainogram.services.PostService;
import com.example.trainogram.services.SponsorPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")

public class PostController {

    private final PostService postService;

    private final LikePostService likeService;

    private final SponsorPostService sponsorPostService;

    @Autowired
    public PostController(PostService postService, LikePostService likeService, SponsorPostService sponsorPostService) {
        this.postService = postService;
        this.likeService = likeService;
        this.sponsorPostService = sponsorPostService;
    }


    @PostMapping("/create")
    public Post createPost(@ModelAttribute PostDTO postDTO, @RequestHeader("Authorization") String token) throws IOException {
        return postService.createPost(postDTO, token);
    }
    @PostMapping("/create/sponsor-post/{sponsorId}")
    public SponsorPost createSponsorPost(@ModelAttribute PostDTO postDTO,@PathVariable Long sponsorId, @RequestHeader("Authorization") String token) throws Status430UserNotFoundException, IOException {
        return sponsorPostService.createSponsorPost(postDTO,sponsorId,token);
    }
    @DeleteMapping("/delete/sponsor-post/{sponsorPostId}")
    public ResponseEntity<?> deleteSponsorPost(@PathVariable Long sponsorPostId, @RequestHeader("Authorization") String token) throws Status427UserHasNotRootException, Status436PostNotFoundException {
         sponsorPostService.deleteSponsorPost(sponsorPostId, token);
         return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get")
    public List<Post> getPostByUsername(@RequestHeader("Authorization") String token) throws Status436PostNotFoundException {
        return postService.getByUsername(token);
    }

    @GetMapping("/get/{id}")
    public Optional<Post> getPostById(@PathVariable Long id) throws Status436PostNotFoundException {
      return  postService.findById(id);
    }

    @GetMapping("/get-all")
    public List<Post> getAllPost() throws Status436PostNotFoundException {
        return postService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status436PostNotFoundException, Status427UserHasNotRootException {
        postService.deletePost(id, token);
    }

    @PostMapping("/update/{id}")
    public void updatePost(@PathVariable Long id,@ModelAttribute PostDTO postDTO, @RequestHeader("Authorization") String token) throws Status436PostNotFoundException, Status427UserHasNotRootException, IOException {
        postService.updatePost(id,postDTO, token);
    }
    
    @PostMapping("/set-like/{id}")
    public void setLike(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status420CommentPostIdNotFoundException, Status433LikeAlreadyExistsException, Status436PostNotFoundException {
        likeService.createLike(id, token);
    }

    @DeleteMapping ("/delete-like/{id}")
    public void deleteLike(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status427UserHasNotRootException, Status430UserNotFoundException {
        likeService.deleteLike(id, token);
    }

}
