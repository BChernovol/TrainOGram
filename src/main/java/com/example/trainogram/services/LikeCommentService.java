package com.example.trainogram.services;

import com.example.trainogram.entity.LikeComment;
import com.example.trainogram.entity.LikePost;
import com.example.trainogram.exception.*;

public interface LikeCommentService {

    void createLike(Long id, String token) throws Status420CommentPostIdNotFoundException, Status433LikeAlreadyExistsException, Status436PostNotFoundException;
    void deleteLike(Long id, String token) throws Status427UserHasNotRootException, Status430UserNotFoundException;
    boolean isLikeRelatedToUser(LikeComment likeComment, Long authorizationUserId);
}
