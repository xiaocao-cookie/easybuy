package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private Integer id;         //主键
    private String title;       //标题
    private String content;     //内容
    private Date createTime;  //创建时间

}
