package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.Friendship;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status432UserCannotAddOneselfToFriend;
import com.example.trainogram.exception.Status435FriendshipAlreadyExistException;
import com.example.trainogram.repositories.FriendshipRepository;
import com.example.trainogram.services.FriendService;
import com.example.trainogram.services.NotificationService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendServiceImpl implements FriendService {

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;

    private final NotificationService notificationService;

    @Autowired
    public FriendServiceImpl(FriendshipRepository friendshipRepository, UserService userService, NotificationService notificationService) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }


    @Override
    public void addUserToFriends(Long id, String token) throws Status432UserCannotAddOneselfToFriend, Status435FriendshipAlreadyExistException, Status430UserNotFoundException {
        User user = userService.getAuthenticatedUser(token).get();
        User userFriend = userService.findById(id);

        if(user.getId().equals(userFriend.getId())){
            throw new Status432UserCannotAddOneselfToFriend("User with id " + id + " cannot add yourself to friend");
        }

        if(friendshipRepository.existsByFriendAndOwner(userFriend, user)){
            throw new Status435FriendshipAlreadyExistException("User " + userFriend.getSurname() + " and user " + user.getSurname() + " already exist in friends");
        }

        friendshipRepository.save(Friendship.builder()
                .owner(user)
                .friend(userFriend)
                .build());
        if(!user.getId().equals(userFriend.getId())){
            notificationService.createNotification("User with id " + user.getId() + " follow you", userFriend, token);
        }
    }
    @Transactional
    @Override
    public void deleteUserFromFriends(Long id) {
        friendshipRepository.deleteFriendshipByFriendId(id);
    }


}
