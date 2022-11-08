package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.Notification;
import com.example.trainogram.entity.User;
import com.example.trainogram.repositories.NotificationRepository;
import com.example.trainogram.services.NotificationService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }



    @Override
    public void createNotification(String message, User recipient, String token) {
        notificationRepository.save(Notification.builder()
                .createDate(new Date())
                .owner(userService.getAuthenticatedUser(token).get())
                .recipient(recipient)
                .text(message)
                .build());
    }

}
