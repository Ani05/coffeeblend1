package com.example.coffeeblend.security;

import com.example.coffeeblend.model.User;
import com.example.coffeeblend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(s);
        if (!byEmail.isPresent()) {
            throw new UsernameNotFoundException("user with " + s + "dose not exist");
        }
        return new SpringUser(byEmail.get());
    }
}

