package com.web;

import com.entity.ProductCategory;
import com.service.ProductCategoryService;
import com.service.impl.ProductCategoryServiceImpl;
import com.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductCategoryServlet",urlPatterns = "/admin/productCategory")
public class ProductCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        ProductCategoryService proCategoryService = new ProductCategoryServiceImpl();
        //分类管理
        if ("index".equals(action)){
            String currentPageStr = request.getParameter("currentPage");
            if (currentPageStr == null) {
                currentPageStr = "1";
            }
            int currentPage = Integer.parseInt(currentPageStr);
            Page pager = proCategoryService.queryPageProCategory(currentPage);
            List<ProductCategory> proCategoryList = pager.getProCategoryList();
            request.setAttribute("menu",4);
            request.setAttribute("productCategoryList",proCategoryList);
            request.setAttribute("pager",pager);
            request.getRequestDispatcher("../backend/productCategory/productCategoryList.jsp").forward(request,response);
        }
    }
}
