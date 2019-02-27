package com.blog.service;

import com.blog.po.User;

public interface UserService {

    User checkUser(String userName, String Password);
}
