package com.example.springbootdemo.service;

import com.example.springbootdemo.model.User;
import com.github.pagehelper.PageInfo;

public interface UserService {

    int addUser(User user);

    PageInfo<User> findAllUser(int pageNum, int pageSize);
}
