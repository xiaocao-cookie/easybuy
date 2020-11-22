package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;           //主键
    private Integer userId;       //用户主键
    private String loginName;     //登录名
    private String userAddress;   //用户地址
    private Date createTime;      //创建时间
    private Float cost;           //总消费
    private String serialNumber;  //订单号

    private List<OrderDetail> orderDetailList;
}
