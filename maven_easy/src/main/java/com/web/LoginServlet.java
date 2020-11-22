package com.web;

import com.entity.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet",urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        //登录界面
        if ("toLogin".equals(action)) {
            request.getRequestDispatcher("pre/login.jsp").forward(request, response);
        }
        //用户注销
        else if ("loginOut".equals(action)) {
            session.removeAttribute("loginUser");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        //准备登录
        else if ("readyLogin".equals(action)) {
            String loginName = request.getParameter("loginName");
            String password = request.getParameter("password");
            if (loginName.equals("")) {
                out.println("<script>alert('用户名为空，请重新输入！！'); location.href='pre/login.jsp';</script> ");
                return;
            }
            if (password.equals("")) {
                out.println("<script>alert('密码为空，请重新输入！！'); location.href='pre/login.jsp';</script> ");
                return;
            }
            UserService userService = new UserServiceImpl();
            User user = userService.findUser(loginName, SecurityUtils.md5Hex(password));
            if (user != null) {
                session.setAttribute("loginUser", user);
                request.getRequestDispatcher("index.jsp").forward(request,response);
            } else {
                request.getRequestDispatcher("pre/login.jsp").forward(request, response);
            }
        }
    }
}
