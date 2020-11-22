package com.dao;

import com.entity.ProductCategory;

import java.util.List;


public interface ProductCategoryDao {
    //查询商品分类，pre/index.jsp的左侧边栏，分页查询
    public List<ProductCategory> queryPageProCategory(Integer currentPage, Integer pageSize);
    //查询总页数
    public int queryTotalCounts();
    //通过父级id查询父级名称
    public String queryParentName(int parentId);
    //通过type（1,2,3）的值查询相关分类名
    public List<ProductCategory> queryNameByType(int type);
    //查询所有
    public List<ProductCategory> queryAllCategory();
    //通过父级id查分类名
    public List<ProductCategory> queryType(int parentId);
}
