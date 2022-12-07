package com.example.trainogram.services;

import com.example.trainogram.entity.ChatMessage;
import com.example.trainogram.websocket.Message;

public interface ChatMessageService {
    ChatMessage saveMessage(String incomingMessage, Long adminId, Long chatRoomId);

}
