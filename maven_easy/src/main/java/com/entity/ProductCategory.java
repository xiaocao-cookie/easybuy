package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    private Integer id;         //主键
    private String name;        //名称
    private Integer parentId;   //父级id
    private Integer type;       //级别(1:一级 2：二级 3：三级)
    private String iconClass;   //图标
    private String parentName;  //父级名称

}
