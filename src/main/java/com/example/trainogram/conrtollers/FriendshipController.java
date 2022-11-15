package com.example.trainogram.conrtollers;

import com.example.trainogram.exception.*;
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
    public void deleteUserFromFriends(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Status427UserHasNotRootException, Status430UserNotFoundException, Status440UserCannotDeleteOneselfToFriend {
        friendService.deleteUserFromFriends(id, token);
    }

}
