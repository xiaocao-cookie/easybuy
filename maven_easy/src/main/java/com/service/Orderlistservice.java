package com.service;

import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface Orderlistservice {

    List<Order> findAllorder();
    List<Order> findOrdersByUserId(int userId);
    /**
     * 根据orderId查询products,查询的表示easybuy_order_detail
     * @param orderId
     * @return
     */
    List<OrderDetail> findProductsByOrderId(int orderId);
    /**
     * 通过订单详情表（数量价格）的产品号，找到产品表格里的产品信息
     * @param productId
     * @return
     * @throws SQLException
     */
    Product findProductById(int productId);

}
