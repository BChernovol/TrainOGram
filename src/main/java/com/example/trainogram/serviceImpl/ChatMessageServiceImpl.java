package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.ChatMessage;
import com.example.trainogram.entity.User;
import com.example.trainogram.repositories.ChatMessageRepository;
import com.example.trainogram.services.ChatMessageService;
import com.example.trainogram.services.UserService;
import com.example.trainogram.websocket.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveMessage(String incomingMessage, Long adminId, Long chatRoomId){

        ChatMessage chatMessage = ChatMessage.builder()
                .authorId(adminId)
                .content(incomingMessage)
                .chatroomId(chatRoomId)
                .dateCreate(new Date().toInstant())
                .build();

        return chatMessageRepository.save(chatMessage);


    }
}
