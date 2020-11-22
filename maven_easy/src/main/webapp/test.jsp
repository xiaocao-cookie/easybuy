<%@ page import="java.nio.file.Path" %>
<%@ page import="com.service.UserService" %>
<%@ page import="com.service.impl.UserServiceImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.entity.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/8/11
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>test</title>
</head>
<body>
<%
    UserService userService = new UserServiceImpl();
    List<User> userList = userService.queryAllUser();
    for (User user : userList) {
        System.out.println(user);
    }
%>
</body>
</html>
