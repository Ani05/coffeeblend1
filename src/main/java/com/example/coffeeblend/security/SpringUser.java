package com.example.coffeeblend.security;

import com.example.coffeeblend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;


import java.security.PublicKey;
import java.util.Collection;

public class SpringUser extends org.springframework.security.core.userdetails.User {
    private  User user;
    public SpringUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
