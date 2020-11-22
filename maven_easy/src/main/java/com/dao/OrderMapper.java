package com.dao;

import com.entity.Order;
import org.apache.ibatis.annotations.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrderMapper {
    public List<Order> queryPageOrders(
            @Param("from") Integer from,
            @Param("pageSize") Integer pageSize
    );

    public int queryTotalCounts();
}
