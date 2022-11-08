package com.example.trainogram.services;

import com.example.trainogram.dto.NotificationDTO;
import com.example.trainogram.entity.User;

public interface NotificationService {

    void createNotification(String message, User recipient, String token);



}

