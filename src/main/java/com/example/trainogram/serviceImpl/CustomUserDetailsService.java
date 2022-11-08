package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Optional<User> findByUsername(String username) throws Status430UserNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new Status430UserNotFoundException("User with username " + username + "not found")));
    }


    @SneakyThrows
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(findByUsername(username).orElseThrow(() -> new Status430UserNotFoundException("Username " + username + "not found")));

        if (user.isEmpty()){
            throw new Status430UserNotFoundException("User with username " + username + " not found");
        }
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                    user.get().getPassword(), List.of(user.get().getUserRole()));
    }

}
