package com.util;


import com.entity.*;

import java.util.List;
import java.util.Objects;

public class Page {
    private int currentPage;                        // 当前页码
    private int pageSize = 8;                      // 页面大小，即每页显示记录数
    private int totalCount;                         // 记录总数
    private int pageCount;                          // 总页数
    private String url;                             //路径
    private List<News> newsList;                    //新闻资讯
    private List<Product> productList;              //商品列表
    private List<User> userList;                    //用户信息
    private List<Order> orderList;                  //订单信息
    private List<ProductCategory> proCategoryList;  //产品详情

    public Page() {
    }

    public Page(int currentPage, int pageSize, int totalCount, int pageCount, String url, List<News> newsList, List<Product> productList, List<User> userList, List<Order> orderList, List<ProductCategory> proCategoryList) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.url = url;
        this.newsList = newsList;
        this.productList = productList;
        this.userList = userList;
        this.orderList = orderList;
        this.proCategoryList = proCategoryList;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", pageCount=" + pageCount +
                ", url='" + url + '\'' +
                ", newsList=" + newsList +
                ", productList=" + productList +
                ", userList=" + userList +
                ", orderList=" + orderList +
                ", proCategoryList=" + proCategoryList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return currentPage == page.currentPage &&
                pageSize == page.pageSize &&
                totalCount == page.totalCount &&
                pageCount == page.pageCount &&
                Objects.equals(url, page.url) &&
                Objects.equals(newsList, page.newsList) &&
                Objects.equals(productList, page.productList) &&
                Objects.equals(userList, page.userList) &&
                Objects.equals(orderList, page.orderList) &&
                Objects.equals(proCategoryList, page.proCategoryList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, pageSize, totalCount, pageCount, url, newsList, productList, userList, orderList, proCategoryList);
    }

    public List<ProductCategory> getProCategoryList() {
        return proCategoryList;
    }

    public void setProCategoryList(List<ProductCategory> proCategoryList) {
        this.proCategoryList = proCategoryList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if(totalCount > 0){
            this.totalCount = totalCount;
            pageCount = (totalCount % pageSize == 0)?(totalCount / pageSize):(totalCount / pageSize + 1);
        }
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

}

