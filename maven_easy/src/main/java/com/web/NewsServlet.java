package com.web;

import com.entity.News;
import com.service.NewsService;
import com.service.impl.NewsServiceImpl;
import com.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewsServlet",urlPatterns = "/admin/news")
public class NewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        NewsService newsService = new NewsServiceImpl();
        //leftBar的资讯列表
        if ("queryNewsList".equals(action)){
            String currentPageStr = request.getParameter("currentPage");
            if (currentPageStr == null) {
                currentPageStr = "1";
            }
            int currentPage = Integer.parseInt(currentPageStr);
            Page pager = newsService.queryPageNews(currentPage);
            List<News> newsList = pager.getNewsList();
            request.setAttribute("menu",7);
            request.setAttribute("newsList",newsList);
            request.setAttribute("pager",pager);
            request.getRequestDispatcher("../backend/news/newsList.jsp").forward(request,response);
        }
        //点击资讯列表后的新闻详情页面
        else if ("newsDeatil".equals(action)){
            Integer id = Integer.parseInt(request.getParameter("id"));
            News news = newsService.queryNewsById(id);
            request.setAttribute("news",news);
            request.getRequestDispatcher("../backend/news/newsDetail.jsp").forward(request,response);
        }
    }
}
