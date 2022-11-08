package com.example.trainogram.entity;


import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "recipient_id",referencedColumnName = "id")
    private User recipient;

    @Column(name = "text")
    private String text;

    @Column(name = "create_date")
    private Date createDate;

}
