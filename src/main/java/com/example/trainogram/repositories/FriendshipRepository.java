package com.example.trainogram.repositories;

import com.example.trainogram.entity.Friendship;
import com.example.trainogram.entity.User;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsByFriendAndOwner(User friend, User owner);

    void deleteFriendshipByFriendId(Long id);
}
