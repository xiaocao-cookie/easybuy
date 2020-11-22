package com.dao.impl;

import com.dao.BaseDao;
import com.dao.ProductCategoryDao;
import com.entity.ProductCategory;
import com.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoImpl extends BaseDao implements ProductCategoryDao {

    public ProductCategoryDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<ProductCategory> queryPageProCategory(Integer currentPage, Integer pageSize) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductCategory> proCategoryList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM easybuy_product_category limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,(currentPage-1)*pageSize);
            preparedStatement.setInt(2,pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductCategory proCategory = new ProductCategory();
                proCategory.setId(resultSet.getInt(1));
                proCategory.setName(resultSet.getString(2));
                proCategory.setParentId(resultSet.getInt(3));
                proCategory.setType(resultSet.getInt(4));
                proCategory.setIconClass(resultSet.getString(5));
                proCategoryList.add(proCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return proCategoryList;
    }

    @Override
    public int queryTotalCounts() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCounts = 0;
        try {
            String sql = "SELECT COUNT(1) FROM easybuy_product_category";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            totalCounts = resultSet.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return totalCounts;
    }

    //通过父级id查询父级名称
    @Override
    public String queryParentName(int parentId) {
        PreparedStatement preparedStatement = null;
        String parentName = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT `name` FROM easybuy_product_category WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,parentId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            parentName = resultSet.getString(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return parentName;
    }

    @Override
    public List<ProductCategory> queryNameByType(int type) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductCategory> proCategoryList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM easybuy_product_category WHERE `type` = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,type);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductCategory proCategory = new ProductCategory();
                proCategory.setId(resultSet.getInt(1));
                proCategory.setName(resultSet.getString(2));
                proCategory.setParentId(resultSet.getInt(3));
                proCategory.setType(resultSet.getInt(4));
                proCategoryList.add(proCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return proCategoryList;
    }

    @Override
    public List<ProductCategory> queryAllCategory() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //大的List
        List<ProductCategory> proCategoryList = new ArrayList<>();
        ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
        try{
            String sql = "SELECT * FROM easybuy_product_category";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductCategory proCategory = new ProductCategory();
                proCategory.setId(resultSet.getInt(1));
                proCategory.setName(resultSet.getString(2));
                proCategory.setParentId(resultSet.getInt(3));
                proCategory.setType(resultSet.getInt(4));
                //小的List
                List<ProductCategory> productCategoryList = productCategoryDao.queryNameByType(1);
                proCategoryList.add(proCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return proCategoryList;
    }

    //通过父级id查分类名
    @Override
    public List<ProductCategory> queryType(int parentId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductCategory> proCategoryList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `easybuy_product_category` WHERE parentId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,parentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductCategory proCategory = new ProductCategory();
                proCategory.setId(resultSet.getInt(1));
                proCategory.setName(resultSet.getString(2));
                proCategory.setParentId(resultSet.getInt(3));
                proCategory.setType(resultSet.getInt(4));
                proCategoryList.add(proCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return proCategoryList;
    }
}
