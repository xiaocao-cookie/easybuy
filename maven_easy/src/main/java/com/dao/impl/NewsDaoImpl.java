package com.dao.impl;

import com.dao.BaseDao;
import com.dao.NewsDao;
import com.entity.News;
import com.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl extends BaseDao implements NewsDao {
    public NewsDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<News> queryAllNews() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> newsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM easybuy_news";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                News news = new News();
                news.setId(resultSet.getInt(1));
                news.setTitle(resultSet.getString(2));
                news.setContent(resultSet.getString(3));
                news.setCreateTime(resultSet.getDate(4));
                newsList.add(news);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return newsList;
    }

    @Override
    public News queryNewsById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        News news = new News();
        try {
            String sql = "SELECT * FROM easybuy_news where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                news.setId(resultSet.getInt(1));
                news.setTitle(resultSet.getString(2));
                news.setContent(resultSet.getString(3));
                news.setCreateTime(resultSet.getDate(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return news;
    }

    @Override
    public List<News> queryPageNews(Integer currentPage,Integer pageSize) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> newsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM easybuy_news limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,(currentPage-1)*pageSize);
            preparedStatement.setInt(2,pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                News news = new News();
                news.setId(resultSet.getInt(1));
                news.setTitle(resultSet.getString(2));
                news.setContent(resultSet.getString(3));
                news.setCreateTime(resultSet.getDate(4));
                newsList.add(news);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return newsList;
    }

    @Override
    public int queryTotalCounts() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCounts = 0;
        try {
            String sql = "SELECT count(1) FROM easybuy_news";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            totalCounts = resultSet.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return totalCounts;
    }
}
