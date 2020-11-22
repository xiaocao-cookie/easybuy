package com.dao;

import com.entity.News;

import java.util.List;

public interface NewsDao {
    //查询所有新闻，新闻资讯栏显示
    public List<News> queryAllNews();
    //根据id查询新闻的内容和标题
    public News queryNewsById(Integer id);
    //分页查询
    public List<News> queryPageNews(Integer currentPage, Integer pageSize);
    //查询资讯总数
    public int queryTotalCounts();
}
