package com.example.trainogram.serviceImpl;

import com.example.trainogram.config.Role;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.repositories.UserRepository;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    public List<User> getAllUsers() throws Status430UserNotFoundException {
        if(userRepository.findAll().isEmpty()){
            throw new Status430UserNotFoundException("User not found exception");
        }
        return userRepository.findAll();
    }

    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(Role.USER);
        userRepository.save(user);
    }


    @Transactional
    public Optional<User> deleteUsesById(Long id) {
        return userRepository.deleteUserById(id);
    }


    @Override
    public Optional<User> getAuthenticatedUser(String token) {
        return userRepository.findByUsername(com.auth0.jwt.JWT.decode(token).getSubject());
    }

    @Override
    public User findById(Long id) throws Status430UserNotFoundException {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        } else throw new Status430UserNotFoundException("User doesn't exist");
    }
}
