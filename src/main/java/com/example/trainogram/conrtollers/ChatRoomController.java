package com.example.trainogram.conrtollers;


import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }


    @PostMapping("/create-chat")
    public ResponseEntity<?> createChat(@RequestParam Set<Long> participants, @RequestHeader("Authorization") String token) throws Status430UserNotFoundException {
        chatRoomService.createChatRoom(participants, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
