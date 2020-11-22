package com.service;

import com.entity.User;
import com.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    //查询所有
    public List<User> queryAllUser();
    //根据id和password查询用户
    public User findUser(String loginName, String password);
    //注册用户，也就是说添加用户
    public int addUser(User user);
    //分页查询
    public Page queryPageUser(Integer currentPage);
}
