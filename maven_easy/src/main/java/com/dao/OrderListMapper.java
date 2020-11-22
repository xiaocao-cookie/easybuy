package com.dao;

import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;

import java.util.List;

public interface OrderListMapper {
    List<Order> findOrdersByUserId(int userId) ;
    List<Order> findAllorder() ;
    /**
     * 根据orderId查询products,查询的表easybuy_order_detail
     * @param orderId
     * @return
     */
    List<OrderDetail> findProductsByOrderId(int orderId) ;

    /**
     * 通过订单详情表（数量价格）的产品号，找到产品表格里的产品信息
     * @param productId
     * @return
     * @throws
     */
    Product findProductById(int productId) ;
}
