package com.blog.service;

import com.blog.Dao.UserRepository;
import com.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String userName, String Password) {
        User user = userRepository.findByUsernameAndPassword(userName, Password);
        return user;
    }
}
