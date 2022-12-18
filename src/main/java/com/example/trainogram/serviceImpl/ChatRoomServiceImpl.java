package com.example.trainogram.serviceImpl;

import com.example.trainogram.services.ChatRoomService;
import com.example.trainogram.websocket.ChatRoom;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.repositories.ChatRoomRepository;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, UserService userService) {
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
    }


    public ChatRoom createChatRoom(Set<Long> participants, String token) throws Status430UserNotFoundException {
        User chatRoomAdminId = userService.getAuthenticatedUser(token).get();
        Set<User> listParticipant = new HashSet<>();
        listParticipant.add(chatRoomAdminId);
        for (Long participantId : participants) {
            listParticipant.add(userService.findById(participantId));
        }

        return chatRoomRepository.save(ChatRoom.builder()
                .adminId(chatRoomAdminId.getId())
                .participants(listParticipant)
                .createTime(Instant.now())
                .build());
    }

    @Override
    public ChatRoom findChatRoomById(Long id) {
        return chatRoomRepository.findChatRoomById(id);
    }

}
