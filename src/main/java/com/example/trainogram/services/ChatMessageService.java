package com.example.trainogram.services;

import com.example.trainogram.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage saveMessage(String incomingMessage, Long adminId, Long chatRoomId);
    List<ChatMessage> getMessage(Long chatRoomId);
}
