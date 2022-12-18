package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.ChatMessage;
import com.example.trainogram.repositories.ChatMessageRepository;
import com.example.trainogram.repositories.ChatRoomRepository;
import com.example.trainogram.services.ChatMessageService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, UserService userService, ChatRoomRepository chatRoomRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
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

    public List<ChatMessage> getMessage(Long chatRoomId){
        return chatMessageRepository.findAllByChatroomId(chatRoomId);
    }
}
