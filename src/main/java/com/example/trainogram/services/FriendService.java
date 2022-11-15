package com.example.trainogram.services;


import com.example.trainogram.exception.*;

public interface FriendService {
    void addUserToFriends(Long id, String token) throws Status432UserCannotAddOneselfToFriend, Status435FriendshipAlreadyExistException, Status430UserNotFoundException;

    void deleteUserFromFriends(Long id, String token) throws Status430UserNotFoundException, Status427UserHasNotRootException, Status440UserCannotDeleteOneselfToFriend;
}
