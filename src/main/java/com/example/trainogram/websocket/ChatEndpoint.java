package com.example.trainogram.websocket;

import com.example.trainogram.entity.User;
import com.example.trainogram.services.ChatMessageService;
import com.example.trainogram.services.ChatRoomService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/chat/{chatRoomId}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class ChatEndpoint {
    private Session session;
    private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    private final UserService userService;

    @Autowired
    public ChatEndpoint() {
        this.chatRoomService = SpringContext.getApplicationContext().getBean(ChatRoomService.class);
        this.chatMessageService = SpringContext.getApplicationContext().getBean(ChatMessageService.class);
        this.userService = SpringContext.getApplicationContext().getBean(UserService.class);
    }

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {         // Get session and WebSocket connection
        this.session = session;
        chatEndpoints.add(this);
    }

    @OnMessage
    public void onMessage(Session session, String incomingMessage, @PathParam("chatRoomId") Long chatRoomId) throws EncodeException, IOException {  //Handle new message
        Message message = new Message();
        message.setFrom(users.get(session.getId()));

        String adminUsername = session.getUserPrincipal().getName();
        Optional<User> adminId = userService.findByUsername(adminUsername);
        ChatRoom chatRoom = chatRoomService.findChatRoomById(chatRoomId);

        if (adminId.get().getId().equals(chatRoom.getAdminId())) {
            chatMessageService.saveMessage(incomingMessage, adminId.get().getId(), chatRoom.getId());
            for (User participant : chatRoom.getParticipants()) {
                message.setTo(participant.getName());
                message.setContent(incomingMessage);
                broadcast(message);
            }
        } else {
            throw new RuntimeException("Error");
        }

    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

        chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);

    }

    @OnError
    public void onError(Session session, Throwable throwable) { //Do error handing here
        System.out.println(throwable.getMessage());
    }

    private static void broadcast(Message message)
            throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendText(message.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
