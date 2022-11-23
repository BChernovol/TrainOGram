package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.Avatar;
import com.example.trainogram.entity.LikeAvatar;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status433LikeAlreadyExistsException;
import com.example.trainogram.exception.Status438AvatarNotFoundException;
import com.example.trainogram.repositories.AvatarRepository;
import com.example.trainogram.repositories.LikeAvatarRepository;
import com.example.trainogram.services.AvatarPictureService;
import com.example.trainogram.services.LikeAvatarService;
import com.example.trainogram.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class LikeAvatarServiceImpl implements LikeAvatarService {

    private final LikeAvatarRepository likeAvatarRepository;
    private final UserService userService;
    private final AvatarRepository avatarRepository;
    private final AvatarPictureService avatarPictureService;

    public LikeAvatarServiceImpl(LikeAvatarRepository likeAvatarRepository, UserService userService, AvatarRepository avatarRepository, AvatarPictureService avatarPictureService1) {
        this.likeAvatarRepository = likeAvatarRepository;
        this.userService = userService;
        this.avatarRepository = avatarRepository;
        this.avatarPictureService = avatarPictureService1;
    }

    public void setLike(Long avatarId, String token) throws Status433LikeAlreadyExistsException, Status438AvatarNotFoundException {
        User user = userService.getAuthenticatedUser(token).get();
        if(!existsLikeAvatarsByUserId(avatarId, user.getId())){
            likeAvatarRepository.save(LikeAvatar.builder()
                    .avatar(avatarPictureService.findAvatarById(avatarId).orElseThrow(() -> new Status438AvatarNotFoundException("Avatar with id "+ avatarId + "not found")))
                    .user(user)
                    .build());
        }else {
            throw new Status433LikeAlreadyExistsException("Like already exist");
        }

    }

    @Override
    public void deleteLike(Long avatarId, String token) throws Status438AvatarNotFoundException, Status427UserHasNotRootException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();//TODO
        Avatar avatar = avatarRepository.findById(avatarId).orElseThrow(() -> new Status438AvatarNotFoundException("Avatar not found"));
        if(userId.equals(avatar.getUser().getId())){
            likeAvatarRepository.deleteByAvatarId(avatarId);
        }else {
            throw new Status427UserHasNotRootException("User has not root");
        }

    }

    @Override
    public boolean existsLikeAvatarsByUserId(Long likeId, Long userId) {
        return likeAvatarRepository.existsLikeAvatarsByUserId(userId);
    }
}
