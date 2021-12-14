package com.maruftech.springblog.service;

import com.maruftech.springblog.entities.User;
import com.maruftech.springblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username).orElseGet(()-> null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
