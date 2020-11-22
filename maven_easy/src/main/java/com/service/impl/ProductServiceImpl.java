package com.service.impl;

import com.dao.ProductMapper;
import com.entity.Product;
import com.service.ProductService;
import com.util.MybatisUtil;
import com.util.Page;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    SqlSession sqlSession = null;
    @Override
    public List<Product> queryAllProduct() {
        sqlSession = MybatisUtil.openSqlSession();
        List<Product> productList = sqlSession.getMapper(ProductMapper.class).queryAllProduct();
        MybatisUtil.closeSqlSession(sqlSession);
        return productList;
    }

    @Override
    public Page queryPageProduct(Integer currentPage) {
        sqlSession = MybatisUtil.openSqlSession();
        Page page = new Page();

        //查记录总数
        int totalCounts = sqlSession.getMapper(ProductMapper.class).queryTotalCounts();
        page.setTotalCount(totalCounts);

        //合理范围
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("/admin/product?action=index");
        page.setCurrentPage(currentPage);
        List<Product> productList = new ArrayList<>();
        productList =  sqlSession.getMapper(ProductMapper.class).queryPageProduct((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setProductList(productList);
        MybatisUtil.closeSqlSession(sqlSession);
        return page;
    }

    @Override
    public int addProduct(Product product) {
        sqlSession = MybatisUtil.openSqlSession();
        int i = 0;
        try {
            i = sqlSession.getMapper(ProductMapper.class).addProduct(product);
            if (i>0){
                sqlSession.commit();
            }
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            MybatisUtil.closeSqlSession(sqlSession);
        }
        return i;
    }

    @Override
    public int updateProductById(Product product) {
        sqlSession = MybatisUtil.openSqlSession();
        int i =0;
        try {
            i = sqlSession.getMapper(ProductMapper.class).updateProductById(product);
            if (i>0){
                sqlSession.commit();
            }
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            MybatisUtil.closeSqlSession(sqlSession);
        }
        return i;
    }

    @Override
    public int deleteProductById(int id) {
       sqlSession = MybatisUtil.openSqlSession();
       int i = 0;
       try {
           i = sqlSession.getMapper(ProductMapper.class).deleteProductById(id);
           if (i>0){
               sqlSession.commit();
           }
       }catch (Exception e){
           sqlSession.rollback();
           e.printStackTrace();
       }finally {
           MybatisUtil.closeSqlSession(sqlSession);
       }
       return i;
    }

    @Override
    public Product queryProductById(int id) {
       sqlSession = MybatisUtil.openSqlSession();
       Product product = sqlSession.getMapper(ProductMapper.class).queryProductById(id);
       MybatisUtil.closeSqlSession(sqlSession);
       return product;
    }

    @Override
    public List<Product> queryProductsByCate1Id(int categoryLevel1Id) {
        sqlSession = MybatisUtil.openSqlSession();
        List<Product> productList = sqlSession.getMapper(ProductMapper.class).queryProductsByCate1Id(categoryLevel1Id);
        MybatisUtil.closeSqlSession(sqlSession);
        return productList;
    }

    @Override
    public List<Product> queryProductsByCate2Id(int categoryLevel2Id) {
        sqlSession = MybatisUtil.openSqlSession();
        List<Product> productList = sqlSession.getMapper(ProductMapper.class).queryProductsByCate2Id(categoryLevel2Id);
        MybatisUtil.closeSqlSession(sqlSession);
        return productList;
    }

    @Override
    public List<Product> queryProductsByCate3Id(int categoryLevel3Id) {
        sqlSession = MybatisUtil.openSqlSession();
        List<Product> productList = sqlSession.getMapper(ProductMapper.class).queryProductsByCate3Id(categoryLevel3Id);
        MybatisUtil.closeSqlSession(sqlSession);
        return productList;
    }
}
