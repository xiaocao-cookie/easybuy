package com.service;

import com.entity.ProductCategory;
import com.entity.ProductCategoryVo;
import com.util.Page;

import java.util.List;

public interface ProductCategoryService {
    //分页查询
    public Page queryPageProCategory(Integer currentPage);
    //通过type（1,2,3）的值查询相关分类名
    public List<ProductCategory> queryNameByType(int type);
    //查询商品所有分类集合
    //也就是首页的左侧边栏的商品分类一级各个一级分类下的产品信息
    public List<ProductCategoryVo> queryAllCategories();
}
