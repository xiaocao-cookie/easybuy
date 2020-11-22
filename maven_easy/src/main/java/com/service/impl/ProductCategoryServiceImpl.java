package com.service.impl;

import com.dao.ProductCategoryMapper;
import com.dao.ProductMapper;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.ProductCategoryVo;
import com.service.ProductCategoryService;
import com.util.MybatisUtil;
import com.util.Page;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    SqlSession sqlSession = null;
    @Override
    public Page queryPageProCategory(Integer currentPage) {
        sqlSession = MybatisUtil.openSqlSession();
        Page page = new Page();
        //查询记录总数
        int totalCounts = sqlSession.getMapper(ProductCategoryMapper.class).queryTotalCounts();
        page.setTotalCount(totalCounts);

        //合理范围
        if(currentPage < 1){
            currentPage = 1;
        }else if(currentPage > page.getPageCount()){
            currentPage = page.getPageCount();
        }
        page.setUrl("admin/productCategory?action=index");
        page.setCurrentPage(currentPage);
        List<ProductCategory> proCategoryList = sqlSession.getMapper(ProductCategoryMapper.class).queryPageProCategory((currentPage-1)*page.getPageSize(),page.getPageSize());
        page.setProCategoryList(proCategoryList);
        for (ProductCategory proCategory : proCategoryList) {
            if (proCategory.getParentId() == 0){
                proCategory.setParentName(null);
            }else {
                String parentName = sqlSession.getMapper(ProductCategoryMapper.class).queryParentName(proCategory.getParentId());
                proCategory.setParentName(parentName);
            }
        }
        MybatisUtil.closeSqlSession(sqlSession);
        return page;
    }

    @Override
    public List<ProductCategory> queryNameByType(int type) {
        sqlSession = MybatisUtil.openSqlSession();
        List<ProductCategory> productCategoryList = sqlSession.getMapper(ProductCategoryMapper.class).queryNameByType(type);
        MybatisUtil.closeSqlSession(sqlSession);
        return productCategoryList;
    }

    //查询商品所有分类集合
    //也就是首页的左侧边栏的商品分类一级各个一级分类下的产品信息
    @Override
    public List<ProductCategoryVo> queryAllCategories() {
        sqlSession = MybatisUtil.openSqlSession();
        //查询一级分类的列表
        List<ProductCategoryVo> proCategory1VoList = new ArrayList<>();
        //查询一级分类
        List<ProductCategory> proCategory1List = sqlSession.getMapper(ProductCategoryMapper.class).queryType(0);
        //查询二级分类
        for (ProductCategory productCategory1 : proCategory1List) {
            //封装一级分类
            ProductCategoryVo productCategoryVo1 = new ProductCategoryVo();
            productCategoryVo1.setProductCategory(productCategory1);
            //根据一级分类查询一级下的商品
            List<Product> productList = sqlSession.getMapper(ProductMapper.class).queryProductsByCate1Id(productCategory1.getId());
            productCategoryVo1.setProductList(productList);
            List<ProductCategoryVo> proCategory1ChildVoList = new ArrayList<>();
            //根据一级分类查询二级分类
            List<ProductCategory> proCategory2List = sqlSession.getMapper(ProductCategoryMapper.class).queryType(productCategory1.getId());
            for (ProductCategory productCategory2 : proCategory2List) {
                //封装二级分类
                ProductCategoryVo productCategoryVo2 = new ProductCategoryVo();
                proCategory1ChildVoList.add(productCategoryVo2);
                productCategoryVo2.setProductCategory(productCategory2);
                List<ProductCategoryVo> proCategory2ChildVoList = new ArrayList<>();
                productCategoryVo2.setProductCategoryVoList(proCategory2ChildVoList);
                //根据二级分类查询三级分类
                List<ProductCategory> proCategory3List = sqlSession.getMapper(ProductCategoryMapper.class).queryType(productCategory2.getId());
                for (ProductCategory productCategory3 : proCategory3List) {
                    //封装三级分类
                    ProductCategoryVo productCategoryVo3 = new ProductCategoryVo();
                    productCategoryVo3.setProductCategory(productCategory3);
                    proCategory2ChildVoList.add(productCategoryVo3);
                }
            }
            productCategoryVo1.setProductCategoryVoList(proCategory1ChildVoList);
            proCategory1VoList.add(productCategoryVo1);
        }
        MybatisUtil.closeSqlSession(sqlSession);
        return proCategory1VoList;
    }
}
