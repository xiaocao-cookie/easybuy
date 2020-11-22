package com.web;

import com.entity.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet",urlPatterns = "/admin/user")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        UserService userService = new UserServiceImpl();
        //leftBar中的用户信息
        if ("index".equals(action)){
            request.setAttribute("menu",2);
            request.getRequestDispatcher("../backend/user/userInfo.jsp").forward(request,response);
        }
        //leftBar中的用户列表
        else if ("queryUserList".equals(action)){
            String currentPageStr = request.getParameter("currentPage");
            if (currentPageStr == null) {
                currentPageStr = "1";
            }
            int currentPage = Integer.parseInt(currentPageStr);
            Page pager = userService.queryPageUser(currentPage);
            List<User> userList = pager.getUserList();
            request.setAttribute("menu",8);
            request.setAttribute("userList",userList);
            request.setAttribute("pager",pager);
            request.getRequestDispatcher("../backend/user/userList.jsp").forward(request,response);
        }
    }
}
