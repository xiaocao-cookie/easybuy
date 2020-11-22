package com.dao;

import com.entity.User;

import java.util.List;

public interface UserDao {
    //查询所有用户信息
    public List<User> queryAllUser();
    //根据id和password查询用户
    public User findUser(String loginName, String password);
    //注册用户，也就是说添加用户
    public int addUser(User user);
}
