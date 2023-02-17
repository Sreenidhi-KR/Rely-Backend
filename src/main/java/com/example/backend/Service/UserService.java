package com.example.backend.Service;

import com.example.backend.Bean.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }
}
