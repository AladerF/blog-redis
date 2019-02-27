package com.blog.Dao;

import com.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 使用springboot自带的jpa
 */

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}
