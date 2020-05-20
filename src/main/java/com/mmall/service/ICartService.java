package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/5/5 19:36
 */
public interface ICartService {

    ServerResponse<CartVo> list (Integer userId);

    ServerResponse<CartVo> add(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer id, String productIds);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
