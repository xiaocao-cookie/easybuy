package com.service.impl;

import com.dao.NewsMapper;
import com.entity.News;
import com.service.NewsService;
import com.util.MybatisUtil;
import com.util.Page;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class NewsServiceImpl  implements NewsService {
    SqlSession sqlSession = null;
    @Override
    public List<News> queryAllNews() {
        sqlSession = MybatisUtil.openSqlSession();
        List<News> newsList = sqlSession.getMapper(NewsMapper.class).queryAllNews();
        MybatisUtil.closeSqlSession(sqlSession);
        return newsList;
    }

    @Override
    public News queryNewsById(Integer id) {
        sqlSession = MybatisUtil.openSqlSession();
        News news = sqlSession.getMapper(NewsMapper.class).queryNewsById(id);
        MybatisUtil.closeSqlSession(sqlSession);
        return news;
    }

    @Override
    public Page queryPageNews(Integer currentPage) {
        sqlSession = MybatisUtil.openSqlSession();
        Page page = new Page();

        //查记录总数
        int totalCounts = sqlSession.getMapper(NewsMapper.class).queryTotalCounts();
        page.setTotalCount(totalCounts);

        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("admin/news?action=queryNewsList");
        page.setCurrentPage(currentPage);
        List<News> newsList = sqlSession.getMapper(NewsMapper.class).queryPageNews((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setNewsList(newsList);
        MybatisUtil.closeSqlSession(sqlSession);
        return page;
    }
}
