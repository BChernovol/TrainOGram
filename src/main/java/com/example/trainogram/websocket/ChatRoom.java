package com.example.trainogram.websocket;


import com.example.trainogram.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "chatroom")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @JoinTable(name = "chatroom_participants")
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set <User> participants;

    @Column(name = "time_create")
    private Instant createTime;

}
