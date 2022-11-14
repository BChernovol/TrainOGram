package com.example.trainogram.conrtollers;

import com.example.trainogram.dto.CommentDTO;
import com.example.trainogram.exception.*;
import com.example.trainogram.services.CommentService;
import com.example.trainogram.services.LikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final LikeCommentService likeCommentService;


    @Autowired
    public CommentController(CommentService commentService, LikeCommentService likeCommentService) {
        this.commentService = commentService;
        this.likeCommentService = likeCommentService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<HttpStatus> create(@PathVariable Long id,
                                             @RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String token) throws Status420CommentPostIdNotFoundException, Status439TextIsEmptyException {
        commentService.createComment(id, commentDTO, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<HttpStatus> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status420CommentPostIdNotFoundException, Status427UserHasNotRootException {
        commentService.deleteComment(id, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody CommentDTO commentDTO, @RequestHeader("Authorization") String token) throws Status427UserHasNotRootException {
        commentService.updateComment(id,commentDTO, token);
    }

    @PostMapping("/set-like/{id}")
    public void setLike( @RequestHeader("Authorization") String token, @PathVariable Long id) throws Status420CommentPostIdNotFoundException, Status433LikeAlreadyExistsException, Status436PostNotFoundException {
       likeCommentService.createLike(id, token);
    }

    @DeleteMapping("/delete-like/{id}")
    public void deleteCommentLike(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status427UserHasNotRootException, Status430UserNotFoundException {
        likeCommentService.deleteLike(id, token);
    }


}
