package com.example.scoreboard.service;

import com.example.scoreboard.entites.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User save(User user);
}
