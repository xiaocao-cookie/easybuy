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
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet",urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        //跳转到注册页面
        if ("toRegister".equals(action)){
            request.getRequestDispatcher("pre/register.jsp").forward(request,response);
        }
        //准备注册
        else if ("readyRegister".equals(action)){
            String loginName=request.getParameter("loginName");
            String password=request.getParameter("password");
            String confirmPassword=request.getParameter("confirmPassword");
            String userName=request.getParameter("userName");
            int sex=Integer.parseInt(request.getParameter("sex"));
            String identityCode=request.getParameter("identityCode");
            String email=request.getParameter("email");
            String mobile=request.getParameter("mobile");
            User user1=new User(loginName, userName, SecurityUtils.md5Hex(password), sex, identityCode,  email,  mobile, 0);
            UserService userService = new UserServiceImpl();
            int i = userService.addUser(user1);
            if (i > 0){
                out.println("<script>alert('注册成功'); location.href='pre/login.jsp';</script> ");
            }else{
                out.println("<script>alert('注册失败，请重新注册！！'); location.href='pre/register.jsp';</script> ");
            }
        }
    }
}
