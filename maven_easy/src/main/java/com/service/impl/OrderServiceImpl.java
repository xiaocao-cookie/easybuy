package com.service.impl;

import com.dao.OrderListMapper;
import com.dao.OrderMapper;
import com.entity.Order;
import com.entity.Product;
import com.service.OrderService;
import com.util.MybatisUtil;
import com.util.Page;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    SqlSession sqlSession = null;
    @Override
    public Page queryPageOrders(Integer currentPage) {
        sqlSession = MybatisUtil.openSqlSession();
        Page page = new Page();

        //查询总记录数
        int totalCounts = sqlSession.getMapper(OrderMapper.class).queryTotalCounts();
        page.setTotalCount(totalCounts);

        if (currentPage < 1){
            currentPage = 1;
        }else if (currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("/admin/order?action=queryAllOrder");
        page.setCurrentPage(currentPage);
        List<Order> orderList = sqlSession.getMapper(OrderMapper.class).queryPageOrders((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setOrderList(orderList);
        MybatisUtil.closeSqlSession(sqlSession);
        return page;
    }


}
