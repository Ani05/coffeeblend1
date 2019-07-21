package com.example.coffeeblend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private boolean isActive;
    @Column
    private String token;
    @Column
    private String position;
    @Column (name = "pic_url")
    private String picUrl;
    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.ADMIN;

}
