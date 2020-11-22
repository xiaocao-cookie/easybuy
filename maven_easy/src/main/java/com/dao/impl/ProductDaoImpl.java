package com.dao.impl;

import com.dao.BaseDao;
import com.dao.ProductDao;
import com.entity.Product;
import com.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends BaseDao implements ProductDao {
    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public List<Product> queryAllProduct() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM easybuy_product";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setFileName(resultSet.getString("fileName"));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                productList.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return productList;
    }

    @Override
    public List<Product> queryPageProduct(Integer currentPage, Integer pageSize) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM easybuy_product limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,(currentPage-1)*pageSize);
            preparedStatement.setInt(2,pageSize);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setFileName(resultSet.getString("fileName"));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                productList.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return productList;
    }

    @Override
    public int queryTotalCounts() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int totalCounts = 0;
        try {
            String sql = "SELECT count(1) FROM easybuy_product";
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

    @Override
    public int addProduct(Product product) {
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            String sql = "INSERT INTO easybuy_product(`name`,fileName,price,stock,description,categoryLevel1Id, categoryLevel2Id, categoryLevel3Id) VALUES(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getFileName());
            preparedStatement.setFloat(3,product.getPrice());
            preparedStatement.setInt(4,product.getStock());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getCategoryLevel1Id());
            preparedStatement.setInt(7,product.getCategoryLevel2Id());
            preparedStatement.setInt(8,product.getCategoryLevel3Id());
            i =preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,null);
        }
        return i;
    }

    @Override
    public int updateProductById(Product product) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int i = 0;
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE `easybuy_product`" +
                    "SET `name` = ?, `description` = ?, `price` = ?, `stock` = ?, " +
                    "`categoryLevel1Id` = ?, `categoryLevel2Id` = ?, `categoryLevel3Id` = ?," +
                    "`fileName` = ?" +
                    "WHERE `id` = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setFloat(3,product.getPrice());
            preparedStatement.setInt(4,product.getStock());
            preparedStatement.setInt(5,product.getCategoryLevel1Id());
            preparedStatement.setInt(6,product.getCategoryLevel2Id());
            preparedStatement.setInt(7,product.getCategoryLevel3Id());
            preparedStatement.setString(8,product.getFileName());
            preparedStatement.setInt(9,product.getId());
            i = preparedStatement.executeUpdate();
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return i;
    }

    @Override
    public int deleteProductById(int id) {
        PreparedStatement preparedStatement = null;
        int i = 0;
        try {
            connection.setAutoCommit(false);
            String sql = "DELETE FROM easybuy_product WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            i = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JdbcUtil.closed(null,preparedStatement,null);
        }
        return i;
    }

    @Override
    public Product queryProductById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = new Product();
        try {
            String sql = "SELECT * FROM `easybuy_product` WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                product.setCategoryLevel1Id(resultSet.getInt(6));
                product.setCategoryLevel2Id(resultSet.getInt(7));
                product.setCategoryLevel3Id(resultSet.getInt(8));
                product.setFileName(resultSet.getString(9));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return product;
    }

    //通过第1分类来查询商品信息
    //此方法在ProductCategoryServiceImpl中调用，用来查询首页的商品信息
    @Override
    public List<Product> queryProductsByCate1Id(int categoryLevel1Id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `easybuy_product` WHERE categoryLevel1Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,categoryLevel1Id);
            resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setFileName(resultSet.getString("fileName"));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                productList.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return productList;
    }

    @Override
    public List<Product> queryProductsByCate2Id(int categoryLevel2Id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `easybuy_product` WHERE categoryLevel2Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,categoryLevel2Id);
            resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setFileName(resultSet.getString("fileName"));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                productList.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return productList;
    }

    @Override
    public List<Product> queryProductsByCate3Id(int categoryLevel3Id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `easybuy_product` WHERE categoryLevel3Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,categoryLevel3Id);
            resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setFileName(resultSet.getString("fileName"));
                product.setPrice(resultSet.getFloat(4));
                product.setStock(resultSet.getInt(5));
                productList.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closed(null,preparedStatement,resultSet);
        }
        return productList;
    }
}