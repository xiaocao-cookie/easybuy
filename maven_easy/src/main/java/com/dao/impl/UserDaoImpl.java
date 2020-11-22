package com.dao.impl;

import com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.User;
import com.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao{
    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> queryAllUser() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        try {
            String sql = "select * from easybuy_user";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                 User user = new User();
                 user.setLoginName(resultSet.getString(2));
                 user.setUserName(resultSet.getString(3));
                 user.setSex(resultSet.getInt("sex"));
                 user.setType(resultSet.getInt("type"));
                 userList.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }

        return userList;
    }

    @Override
    public User findUser(String loginName, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            String sql = "select * from easybuy_user where loginName = ? and password =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, loginName);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLoginName(resultSet.getNString("loginName"));
                user.setPassword(resultSet.getNString("password"));
                user.setUserName(resultSet.getString("userName"));
                user.setType(resultSet.getInt("type"));
                user.setEmail(resultSet.getNString("email"));
                user.setMobile(resultSet.getString("mobile"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return user;
    }

    @Override
    public int addUser(User user) {
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            String sql = "insert into easybuy_user(loginName, userName, password, sex, identityCode,  email,  mobile, type ) values(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getLoginName());
            preparedStatement.setString(2,user.getUserName());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setInt(4,user.getSex());
            preparedStatement.setString(5,user.getIdentityCode());
            preparedStatement.setString(6,user.getEmail());
            preparedStatement.setString(7,user.getMobile());
            preparedStatement.setInt(8,user.getType());
            i = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,null);
        }
        return i;
    }
}
