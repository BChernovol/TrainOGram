package com.example.trainogram.services;

import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status433LikeAlreadyExistsException;
import com.example.trainogram.exception.Status438AvatarNotFoundException;


public interface LikeAvatarService {
     void setLike(Long avatarId, String token) throws Status433LikeAlreadyExistsException, Status438AvatarNotFoundException;
     void deleteLike(Long avatarId, String token) throws Status438AvatarNotFoundException, Status427UserHasNotRootException;
     boolean existsLikeAvatarsByUserId(Long likeId, Long userId);
}
