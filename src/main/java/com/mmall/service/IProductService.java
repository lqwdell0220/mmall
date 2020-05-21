package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 7:00
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
