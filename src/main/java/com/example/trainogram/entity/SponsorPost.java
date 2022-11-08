package com.example.trainogram.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sponsor_post")
public class SponsorPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name= "sponsor_id", referencedColumnName = "id")
    private User sponsorUser;

    @OneToOne
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;
}
