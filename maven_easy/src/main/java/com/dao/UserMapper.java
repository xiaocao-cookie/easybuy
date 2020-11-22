package com.dao;

import com.entity.News;
import com.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //查询所有用户信息
    public List<User> queryAllUser();
    //根据id和password查询用户
    public User findUser(
            @Param("loginName") String loginName,
            @Param("password") String password);
    //注册用户，也就是说添加用户
    public int addUser(User user);
    //分页查询
    public List<User> queryPageUser(
            @Param("from") Integer from,
            @Param("pageSize") Integer pageSize);
    //查询用户总数
    public int queryTotalCounts();
}
