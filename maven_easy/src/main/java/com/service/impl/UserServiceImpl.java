package com.service.impl;

import com.dao.UserMapper;
import com.entity.User;
import com.service.UserService;
import com.util.MybatisUtil;
import com.util.Page;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserServiceImpl implements UserService {
    SqlSession sqlSession = null;
    @Override
    public List<User> queryAllUser() {
        sqlSession = MybatisUtil.openSqlSession();
        List<User> userList = sqlSession.getMapper(UserMapper.class).queryAllUser();
        MybatisUtil.closeSqlSession(sqlSession);
        return userList;
    }

    @Override
    public User findUser(String loginName, String password) {
        sqlSession = MybatisUtil.openSqlSession();
        User user = sqlSession.getMapper(UserMapper.class).findUser(loginName, password);
        MybatisUtil.closeSqlSession(sqlSession);
        return user;
    }

    @Override
    public int addUser(User user) {
       sqlSession = MybatisUtil.openSqlSession();
       int i = 0;
       try {
           i = sqlSession.getMapper(UserMapper.class).addUser(user);
           if (i>0){
               sqlSession.commit();
           }
       }catch (Exception e){
           sqlSession.rollback();
           e.printStackTrace();
       }finally {
           MybatisUtil.closeSqlSession(sqlSession);
       }
       return i;
    }

    @Override
    public Page queryPageUser(Integer currentPage) {
        sqlSession = MybatisUtil.openSqlSession();
        Page page = new Page();

        //查询记录总数
        int totalCounts = sqlSession.getMapper(UserMapper.class).queryTotalCounts();
        page.setTotalCount(totalCounts);
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("/admin/user?action=queryUserList");
        page.setCurrentPage(currentPage);
        List<User> userList = sqlSession.getMapper(UserMapper.class).queryPageUser((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setUserList(userList);
        return page;
    }

}
