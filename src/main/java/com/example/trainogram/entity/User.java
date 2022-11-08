package com.example.trainogram.entity;

import com.example.trainogram.config.Role;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30,message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30,message = "Name should be between 2 and 30 characters")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30,message = "Name should be between 2 and 30 characters")
    @Column(name = "username")
    private String username;

    @Min(value = 0)
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role userRole;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Avatar> avatarList;

}
