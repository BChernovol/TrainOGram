package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.LikeAvatar;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status433LikeAlreadyExistsException;
import com.example.trainogram.exception.Status438AvatarNotFoundException;
import com.example.trainogram.repositories.LikeAvatarRepository;
import com.example.trainogram.services.AvatarPictureService;
import com.example.trainogram.services.LikeAvatarService;
import com.example.trainogram.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class LikeAvatarServiceImpl implements LikeAvatarService {

    private final LikeAvatarRepository likeAvatarRepository;
    private final UserService userService;

    private final AvatarPictureService avatarPictureService;

    public LikeAvatarServiceImpl(LikeAvatarRepository likeAvatarRepository, UserService userService, AvatarPictureService avatarPictureService1) {
        this.likeAvatarRepository = likeAvatarRepository;
        this.userService = userService;
        this.avatarPictureService = avatarPictureService1;
    }

    public void setLike(Long avatarId, String token) throws Status433LikeAlreadyExistsException, Status438AvatarNotFoundException {
        User user = userService.getAuthenticatedUser(token).get();
        if(!existsLikeAvatarsByUserId(user.getId(),avatarId)){
            likeAvatarRepository.save(LikeAvatar.builder()
                    .avatar(avatarPictureService.findAvatarById(avatarId).orElseThrow(() -> new Status438AvatarNotFoundException("Avatar with id "+ avatarId + "not found")))
                    .user(user)
                    .build());
        }else {
            throw new Status433LikeAlreadyExistsException("Like already exist");
        }

    }

    @Override
    public boolean existsLikeAvatarsByUserId(Long likeId, Long userId) {
        return likeAvatarRepository.existsByUser_IdAndAvatar_Id(likeId,userId);
    }
}
