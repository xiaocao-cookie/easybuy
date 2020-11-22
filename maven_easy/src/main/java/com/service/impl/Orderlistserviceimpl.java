package com.service.impl;


import com.dao.OrderListMapper;
import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;
import com.service.Orderlistservice;
import com.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;


public class Orderlistserviceimpl implements Orderlistservice {
    SqlSession sqlSession = null;
    //从easybuy_order表查询用户名 地址 价格 订单号所有信息封装到orderList
    @Override
    public List<Order> findAllorder() {
        sqlSession = MybatisUtil.openSqlSession();
        List<Order> orderList=null;
        try {
            orderList = sqlSession.getMapper(OrderListMapper.class).findAllorder();
        } finally {
            MybatisUtil.closeSqlSession(sqlSession);
        }
        return orderList;
    }
    //通过order表，通过传入userId 获取所有信息。包含细节表的集合 和 产品表的类
    @Override
    public List<Order> findOrdersByUserId(int userId) {
        sqlSession = MybatisUtil.openSqlSession();
        List<Order> detailOrderList = null;
        try {
            detailOrderList =sqlSession.getMapper(OrderListMapper.class).findOrdersByUserId(userId);
        } finally {
            MybatisUtil.closeSqlSession(sqlSession);
        }
        return detailOrderList;
    }

    /**
     * 根据orderId查询products,查询的表示easybuy_order_detail
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderDetail> findProductsByOrderId(int orderId)  {
        sqlSession = MybatisUtil.openSqlSession();
        List<OrderDetail> orderList = null;
        try {
            orderList =sqlSession.getMapper(OrderListMapper.class).findProductsByOrderId(orderId);
        } finally {
            MybatisUtil.closeSqlSession(sqlSession);
        }
        return orderList;
    }

    /**
     * 通过订单详情表（数量价格）的产品号，找到产品表格里的产品信息
     *
     * @param productId
     * @return
     * @throws
     */
    @Override
    public Product findProductById(int productId) {
        sqlSession = MybatisUtil.openSqlSession();
        Product product = null;

        product = sqlSession.getMapper(OrderListMapper.class).findProductById(productId);

        MybatisUtil.closeSqlSession(sqlSession);

        return product;
    }

}
