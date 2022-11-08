package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.LikeComment;
import com.example.trainogram.entity.LikePost;
import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.SponsorPost;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status433LikeAlreadyExistsException;
import com.example.trainogram.exception.Status436PostNotFoundException;
import com.example.trainogram.repositories.LikePostRepository;
import com.example.trainogram.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("likePost")
public class LikePostServiceImpl implements LikePostService {

    private final LikePostRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    private final SponsorPostService sponsorPostService;

    private final NotificationService notificationService;

    @Autowired
    public LikePostServiceImpl(LikePostRepository likeRepository, UserService userService, PostService postService, SponsorPostService sponsorPostService, NotificationService notificationService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
        this.sponsorPostService = sponsorPostService;
        this.notificationService = notificationService;
    }



    @Transactional
    @Override
    public void createLike(Long postId, String token) throws Status433LikeAlreadyExistsException, Status436PostNotFoundException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();
        Post post = postService.findById(postId).orElseThrow();
        SponsorPost sponsorPost = sponsorPostService.findSponsorPostByPostId(postId);
        if (likeRepository.findLikeByUserIdAndPostId(userId,postId)==null){

            likeRepository.save(LikePost.builder()
                    .user(userService.getAuthenticatedUser(token).get())
                    .createDate(new Date())
                    .post(post)
                    .build());
            if(!userId.equals(postService.findById(postId).get().getUser().getId())){
                notificationService.createNotification("User with id " + userId + " like your sponsor post", post.getUser(), token);
            }
            if(sponsorPost != null && !sponsorPost.getSponsorUser().getId().equals(postId)){
                notificationService.createNotification("User with id " + userId + " like your post", sponsorPost.getSponsorUser(),token);
            }
        }else {
            throw new Status433LikeAlreadyExistsException("Like already exists");
        }



    }

    @Override
    public void deleteLike(Long likeId, String token) throws Status427UserHasNotRootException {
        Long authorizationUserId = userService.getAuthenticatedUser(token).get().getId();
        LikePost like  = likeRepository.findById(likeId).orElseThrow();
        if(!isLikeRelatedToUser(like,authorizationUserId)){
            throw new Status427UserHasNotRootException("User with id "+authorizationUserId+ " has no root");
        }else {
            likeRepository.deleteById(likeId);

        }

    }

    @Override
    public boolean isLikeRelatedToUser(LikePost like, Long authorizationUserId) {
        return like.getUser().getId().equals(authorizationUserId);
    }


}
