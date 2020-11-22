package com.web;

import com.entity.Product;
import com.entity.ProductCategoryVo;
import com.service.ProductCategoryService;
import com.service.ProductService;
import com.service.impl.ProductCategoryServiceImpl;
import com.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeProductServlet",urlPatterns = "/Product")
public class HomeProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        //页面跳转时首页的分页列表
        ProductCategoryService proCategoryService = new ProductCategoryServiceImpl();
        List<ProductCategoryVo> productCategoryVoList = proCategoryService.queryAllCategories();
        request.setAttribute("productCategoryVoList",productCategoryVoList);

        ProductService productService = new ProductServiceImpl();
        ProductCategoryService  productCategoryService = new ProductCategoryServiceImpl();
        //点击分类标签查询分类下的商品
        if ("queryProductList".equals(action)){

            int level = Integer.parseInt(request.getParameter("level"));
            int category = Integer.parseInt(request.getParameter("category"));
            List<Product> productList = new ArrayList<>();
            //第1类商品查询
            if (level == 1){
                productList = productService.queryProductsByCate1Id(category);
                request.setAttribute("productList",productList);
                request.getRequestDispatcher("pre/product/queryProductList.jsp").forward(request,response);
            }
            //第2类商品查询
            if (level == 2){
                productList = productService.queryProductsByCate2Id(category);
                request.setAttribute("productList",productList);
                request.getRequestDispatcher("pre/product/queryProductList.jsp").forward(request,response);
            }
            //第3类商品查询
            if (level == 3){
                productList = productService.queryProductsByCate3Id(category);
                request.setAttribute("productList",productList);
                request.getRequestDispatcher("pre/product/queryProductList.jsp").forward(request,response);
            }
        }
        //点击主页图片时显示商品详情
        else if ("queryProductDetail".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productService.queryProductById(id);
            request.setAttribute("product",product);
            request.getRequestDispatcher("pre/product/productDetail.jsp").forward(request,response);
        }
    }
}
