package com.service;

import com.entity.Product;
import com.util.Page;

import java.util.List;

public interface ProductService {
    //查询所有商品
    public List<Product> queryAllProduct();
    //分页查询
    public Page queryPageProduct(Integer currentPage);
    //商品上架
    public int addProduct(Product product);
    //修改
    public int updateProductById(Product product);
    //删除
    public int deleteProductById(int id);
    //通过id查询商品信息
    public Product queryProductById(int id);
    //通过第1分类来查询商品信息
    public List<Product> queryProductsByCate1Id(int categoryLevel1Id);
    //通过第2分类来查询商品信息
    public List<Product> queryProductsByCate2Id(int categoryLevel2Id);
    //通过第3分类来查询商品信息
    public List<Product> queryProductsByCate3Id(int categoryLevel3Id);
}
