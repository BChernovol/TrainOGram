package com.example.trainogram.services;


import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status432UserCannotAddOneselfToFriend;
import com.example.trainogram.exception.Status435FriendshipAlreadyExistException;

public interface FriendService {
    void addUserToFriends(Long id, String token) throws Status432UserCannotAddOneselfToFriend, Status435FriendshipAlreadyExistException, Status430UserNotFoundException;

    void deleteUserFromFriends(Long id);
}
