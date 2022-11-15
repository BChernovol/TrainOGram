package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.Comment;
import com.example.trainogram.entity.LikeComment;
import com.example.trainogram.entity.SponsorPost;
import com.example.trainogram.exception.Status420CommentPostIdNotFoundException;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status433LikeAlreadyExistsException;
import com.example.trainogram.repositories.LikeCommentRepository;
import com.example.trainogram.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("likeComment")
public class LikeCommentServiceImp implements LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;

    private final CommentService commentService;

    private final UserService userService;

    private final NotificationService notificationService;

    private final SponsorPostService sponsorPostService;

    @Autowired
    public LikeCommentServiceImp(LikeCommentRepository likeCommentRepository, CommentService commentService, UserService userService, NotificationService notificationService, SponsorPostService sponsorPostService) {
        this.likeCommentRepository = likeCommentRepository;
        this.commentService = commentService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.sponsorPostService = sponsorPostService;
    }

    @Override
    public void createLike(Long id, String token) throws Status420CommentPostIdNotFoundException, Status433LikeAlreadyExistsException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();
        Comment comment = commentService.findById(id);
        SponsorPost sponsorPost = sponsorPostService.findSponsorPostByIdLike(id);
        if(likeCommentRepository.findLikeByUserIdAndCommentId(userId, comment.getId())==null ) {
            likeCommentRepository.save(LikeComment.builder()
                    .user(userService.getAuthenticatedUser(token).get())
                    .comment(comment)
                    .createDate(new Date())
                    .build());
            if(!userId.equals(commentService.findById(id).getAuthor().getId())){
                notificationService.createNotification("user with id " + userId + " liked your comment", commentService.findById(id).getAuthor(),token);
            }
            if(sponsorPost != null ){
                notificationService.createNotification("user with id " + userId + " liked you comment", sponsorPost.getSponsorUser(),token);
            }


        }else {
            throw new Status433LikeAlreadyExistsException("Like already exist");
        }

    }

    @Override
    public void deleteLike(Long id, String token) throws Status427UserHasNotRootException, Status430UserNotFoundException {
        Long authorizationUserId = userService.getAuthenticatedUser(token).get().getId();
        LikeComment likeComment = likeCommentRepository.findById(id).orElseThrow(() -> new Status430UserNotFoundException("LikeComment with id"+ id + " not found"));
        if (!isLikeRelatedToUser(likeComment, authorizationUserId)) {
            throw new Status427UserHasNotRootException("User " + authorizationUserId + " can not delete like");
        }
        else {
            likeCommentRepository.deleteById(id);
        }
    }

    @Override
    public boolean isLikeRelatedToUser(LikeComment likeComment, Long authorizationUserId) {
        return likeComment.getUser().getId().equals(authorizationUserId);
    }
}
