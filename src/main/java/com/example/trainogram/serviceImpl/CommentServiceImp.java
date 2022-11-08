package com.example.trainogram.serviceImpl;

import com.example.trainogram.dto.CommentDTO;
import com.example.trainogram.entity.Comment;
import com.example.trainogram.entity.Post;
import com.example.trainogram.exception.Status420CommentPostIdNotFoundException;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.repositories.CommentRepository;
import com.example.trainogram.repositories.PostRepository;
import com.example.trainogram.services.CommentService;
import com.example.trainogram.services.NotificationService;
import com.example.trainogram.services.UserService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {


    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final NotificationService notificationService;


    @Autowired
    public CommentServiceImp(UserService userService, CommentRepository commentRepository, PostRepository postRepository, NotificationService notificationService) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.notificationService = notificationService;
    }


    @Override
    public Comment createComment(Long postId, CommentDTO commentDTO, String token) throws Status420CommentPostIdNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Status420CommentPostIdNotFoundException("Post with id " + postId));
        Comment comment = Comment.builder()
                .createDate(new Date())
                .text(commentDTO.getText())
                .post(post)
                .author(userService.getAuthenticatedUser(token).get())
                .build();
        commentRepository.save(comment);
        if(!userService.getAuthenticatedUser(token).get().getId().equals(post.getUser().getId())){
            notificationService.createNotification("new comment to post ", post.getUser(),token);
        }

        return comment;
    }

    @Override
    public void deleteComment(Long commentId, String token) throws Status420CommentPostIdNotFoundException, Status427UserHasNotRootException {

        Long userId = userService.getAuthenticatedUser(token).get().getId(); // TODO: 01.11.2022  Check owner user
        Optional<Comment> comment  = commentRepository.findById(commentId);

            if(commentRepository.existsById(commentId)){

                if(userId.equals(comment.get().getAuthor().getId())){
                    commentRepository.deleteById(commentId);
                }else {
                    throw new Status427UserHasNotRootException("User have's root with id " + userId);
                }

        } else {
            throw new Status420CommentPostIdNotFoundException("Comment not found with id " + commentId);
        }
    }

    @Override
    public Comment findById(Long id) throws Status420CommentPostIdNotFoundException {
       return commentRepository.findById(id).orElseThrow(() -> new Status420CommentPostIdNotFoundException("Comment with id " + id));
    }

    @Transactional
    @Override
    public void updateComment(Long id, CommentDTO commentDTO, String token) throws Status427UserHasNotRootException {
            Long userId = userService.getAuthenticatedUser(token).get().getId();

            Comment comment = commentRepository.findById(id).orElseThrow();
            if (!isCommentRelatedToUser(comment, userId)){
                throw new Status427UserHasNotRootException("User with id " + id + " hasn't root");
            }
                comment.setText(commentDTO.getText());
                notificationService.createNotification("update comment with id " + id, comment.getAuthor(), token);
            }
     public boolean isCommentRelatedToUser(Comment comment, Long userId){
        return comment.getAuthor().getId().equals(userId);
     }

}

