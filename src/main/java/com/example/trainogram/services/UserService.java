package com.example.trainogram.services;

import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {


     List<User> getAllUsers() throws Status430UserNotFoundException;

     void save(User user);
     Optional<User> deleteUsesById(Long id);

     Optional<User> getAuthenticatedUser(String token);

     User findById(Long id) throws Status430UserNotFoundException;


}
