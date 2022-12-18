package com.example.trainogram.conrtollers;


import com.example.trainogram.entity.ChatMessage;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.services.ChatMessageService;
import com.example.trainogram.services.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Component
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }


    @PostMapping("/create-chat")
    public ResponseEntity<?> createChat(@RequestParam Set<Long> participants, @RequestHeader("Authorization") String token) throws Status430UserNotFoundException {
        chatRoomService.createChatRoom(participants, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/get-message/{chatRoomId}")
    public List<ChatMessage> getMessage(@PathVariable Long chatRoomId){
      return chatMessageService.getMessage(chatRoomId);
    }


}
