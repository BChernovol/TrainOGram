package com.example.trainogram.repositories;

import com.example.trainogram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> deleteUserById(Long id);
    boolean existsById(Long id);
}
