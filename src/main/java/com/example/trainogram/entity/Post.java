package com.example.trainogram.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
     private Long id;

    @Column(name = "create_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Picture> pictureList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"post"})
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties({"post"})
    private List<LikePost> likeList;

}

