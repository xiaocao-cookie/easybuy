package com.web;

import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.Orderlistserviceimpl;
import com.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrderServlet",urlPatterns = "/admin/order")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        OrderService orderService = new OrderServiceImpl();
        Orderlistserviceimpl orderlistservice = new Orderlistserviceimpl();
        //对应leftBar的我的订单
        if ("index".equals(action)){
            request.setAttribute("menu",1);
            List<Order> orderList = null;
            //查出用户号为页面获取的id的三个表所有信息放到一个List<Order> orderList集合里
            String  uId=request.getParameter("userId");
//         Integer userId=((uId== null || uId.equals("null"))?null:Integer.parseInt(uId));
            Integer userId=Integer.parseInt(uId);
//通过页面传入的userId获得一条order表数据
            orderList = orderlistservice.findOrdersByUserId(userId);

            for (Order order : orderList) {
                //通过order id获得细节表信息放入集合中
                List<OrderDetail> orderDetailList = orderlistservice.findProductsByOrderId(order.getId());
                if(orderDetailList!=null){
                    order.setOrderDetailList(orderDetailList);
                }
                //已经存放了细节表和订单表信息，在遍历获取产品表信息
                for (OrderDetail detailOrder : orderDetailList) {
                    //通过细节表的productId获取一天产品表信息
                    Product product = orderlistservice.findProductById(
                            detailOrder.getProductId());
                    detailOrder.setProduct(product);
                }
            }
            request.getSession().setAttribute("orderList",orderList);
            request.getRequestDispatcher("../backend/order/orderList.jsp").forward(request,response);
        }
        //对应leftBar的订单列表
       /* else if ("queryAllOrder".equals(action)){
            String currentPageStr = request.getParameter("currentPage");
            if (currentPageStr == null) {
                currentPageStr = "1";
            }
            int currentPage = Integer.parseInt(currentPageStr);
            Page pager = orderService.queryPageOrders(currentPage);
            List<Order> orderList = pager.getOrderList();
            request.setAttribute("menu",9);
            request.setAttribute("orderList",orderList);
            request.setAttribute("pager",pager);
            request.getRequestDispatcher("../backend/order/orderList.jsp").forward(request,response);
        }*/

        else if("queryAllOrder".equals(action)){
            //List<Order> 的方法 findAllorder() 从easybuy_order表查询三个表所有信息封装到orderList
            List<Order> orderList = orderlistservice.findAllorder();
            //遍历order，给每个订单用户都封装上订单细节信息和产品信息
            for (Order order : orderList) {
                List<OrderDetail> orderDetailList = orderlistservice.findProductsByOrderId(
                        order.getId());
                if(orderDetailList!=null){
                    order.setOrderDetailList(orderDetailList);
                }
                for (OrderDetail detailOrder : orderDetailList) {
                    Product product =orderlistservice.findProductById(detailOrder.getProductId());
                    detailOrder.setProduct(product);
                }
            }
            request.getSession().setAttribute("orderList",orderList);
            request.getRequestDispatcher("../backend/order/orderList.jsp").forward(request,response);
        }else if("queryOrderDeatil".equals(action)){
            List<OrderDetail> orderDetailList = null;
            //通过页面传入的orderId获得一条order表数据
            orderDetailList = orderlistservice.findProductsByOrderId(
                    Integer.parseInt(request.getParameter("orderId")));
                //已经存放了细节表信息，在遍历获取产品表信息
                for (OrderDetail detailOrder : orderDetailList) {
                    //通过细节表的productId获取一天产品表信息
                    Product product = orderlistservice.findProductById(
                            detailOrder.getProductId());
                    detailOrder.setProduct(product);
                }
            request.setAttribute("menu",9);
            request.getSession().setAttribute("orderDetailList",orderDetailList);
            request.getRequestDispatcher("../backend/order/orderDetailList.jsp").forward(request,response);
        }
    }
}
