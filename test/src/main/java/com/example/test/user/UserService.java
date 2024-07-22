package com.example.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String loginCheck(String userId, String pwd){
        return userRepository.loginCheck(userId,pwd);
    }
}
