package com.example.trainogram.services;


import com.example.trainogram.websocket.ChatRoom;
import com.example.trainogram.exception.Status430UserNotFoundException;

import java.util.Set;

public interface ChatRoomService {

    ChatRoom createChatRoom(Set<Long> recipients, String token) throws Status430UserNotFoundException;
}
