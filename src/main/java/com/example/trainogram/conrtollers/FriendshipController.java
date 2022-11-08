package com.example.trainogram.conrtollers;

import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status432UserCannotAddOneselfToFriend;
import com.example.trainogram.exception.Status435FriendshipAlreadyExistException;
import com.example.trainogram.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
public class    FriendshipController {

    private final FriendService friendService;

    @Autowired
    public FriendshipController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add-friend/{id}")
    public void addToFriends(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status432UserCannotAddOneselfToFriend, Status435FriendshipAlreadyExistException, Status430UserNotFoundException {
       friendService.addUserToFriends(id,token);
    }

    @DeleteMapping("/delete-friend/{id}")
    public void deleteUserFromFriends(@PathVariable Long id){
        friendService.deleteUserFromFriends(id);
    }

}
