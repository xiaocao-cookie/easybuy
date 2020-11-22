package com.web;

import com.entity.Product;
import com.entity.ProductCategory;
import com.service.ProductCategoryService;
import com.service.ProductService;
import com.service.impl.ProductCategoryServiceImpl;
import com.service.impl.ProductServiceImpl;
import com.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ProductServlet",urlPatterns = "/admin/product")
public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        ProductService productService = new ProductServiceImpl();
        ProductCategoryService proCategoryService = new ProductCategoryServiceImpl();
        //leftBar中的商品管理
        if ("index".equals(action)){
            //商品管理之分页显示
            String currentPageStr = request.getParameter("currentPage");
            if (currentPageStr == null) {
                currentPageStr = "1";
            }
            int currentPage = Integer.parseInt(currentPageStr);
            Page pager = productService.queryPageProduct(currentPage);
            List<Product> productList = pager.getProductList();
            request.setAttribute("menu",5);
            request.setAttribute("pager",pager);
            request.setAttribute("productList",productList);
            request.getRequestDispatcher("../backend/product/productList.jsp").forward(request,response);
        }
        //leftBar中的商品准备上架
        else if ("toAddProduct".equals(action)){
            List<ProductCategory> proCategoryList1 = proCategoryService.queryNameByType(1);
            List<ProductCategory> proCategoryList2 = proCategoryService.queryNameByType(2);
            List<ProductCategory> proCategoryList3 = proCategoryService.queryNameByType(3);
            request.setAttribute("productCategoryList1",proCategoryList1);
            request.setAttribute("productCategoryList2",proCategoryList2);
            request.setAttribute("productCategoryList3",proCategoryList3);
            request.setAttribute("menu",6);
            request.getRequestDispatcher("../backend/product/toAddProduct.jsp").forward(request,response);
        }
        //商品上架
        else if ("addProduct".equals(action)){
            //获取表单提交的参数
            String name = request.getParameter("name");
            String photo = request.getParameter("photo");
            float price = Float.parseFloat(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String description = request.getParameter("description");
            int categoryLevel1Id = Integer.parseInt(request.getParameter("categoryLevel1Id"));
            int categoryLevel2Id = Integer.parseInt(request.getParameter("categoryLevel2Id"));
            int categoryLevel3Id = Integer.parseInt(request.getParameter("categoryLevel3Id"));
            //获取隐藏域id的值，隐藏域在toAddProduct.jsp的第75行
            String idStr = request.getParameter("pid");
            if ("".equals(idStr)){
                idStr = "1";
            }
            int id = Integer.parseInt(idStr);
            //封装对象
            Product product = new Product();
            product.setName(name);
            product.setFileName(photo);
            product.setPrice(price);
            product.setStock(stock);
            product.setDescription(description);
            product.setCategoryLevel1Id(categoryLevel1Id);
            product.setCategoryLevel2Id(categoryLevel2Id);
            product.setCategoryLevel3Id(categoryLevel3Id);

            //如果id的值为空字符串，则添加商品,否则则修改商品
            if (id == 1){
                //调用添加方法
                int i = productService.addProduct(product);
                if (i > 0){
                    out.println("<script>alert('添加成功');location.href='../admin/product?action=index';</script>");
                }else{
                    out.println("<script>alert('添加失败');location.href='../backend/product/toAddProduct.jsp';</script>");
                }
            } else{
                product.setId(id);
                //调用修改方法
                int i = productService.updateProductById(product);
                if (i > 0){
                    out.println("<script>alert('修改成功');location.href='../admin/product?action=index';</script>");
                }else{
                    out.println("<script>alert('修改失败');location.href='../admin/product?action=index';</script>");
                }
            }
        }
        //商品管理之准备修改
        else if ("toUpdateProduct".equals(action)){
            List<ProductCategory> proCategoryList1 = proCategoryService.queryNameByType(1);
            List<ProductCategory> proCategoryList2 = proCategoryService.queryNameByType(2);
            List<ProductCategory> proCategoryList3 = proCategoryService.queryNameByType(3);
            request.setAttribute("productCategoryList1",proCategoryList1);
            request.setAttribute("productCategoryList2",proCategoryList2);
            request.setAttribute("productCategoryList3",proCategoryList3);
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productService.queryProductById(id);
            request.setAttribute("product",product);
            request.getRequestDispatcher("../backend/product/toAddProduct.jsp").forward(request,response);
        }
        //商品管理之删除
        else if ("toDeleteProduct".equals(action)){
            int id = Integer.parseInt(request.getParameter("id"));
            int i = productService.deleteProductById(id);
            if (i>0){
                out.println("<script>alert('删除成功');location.href='../admin/product?action=index';</script>");
            }else{
                out.println("<script>alert('删除失败');location.href='../admin/product?action=index';</script>");
            }
        }
    }
}
