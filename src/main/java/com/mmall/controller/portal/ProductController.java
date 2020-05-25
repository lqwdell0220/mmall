package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/28 22:27
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService iProductService;



    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("/{productId}")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRestful(@PathVariable Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false)String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping("/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}")
    @ResponseBody
    public ServerResponse<PageInfo> listRestful(@PathVariable(value = "keyword")String keyword,
                                         @PathVariable(value = "categoryId")Integer categoryId,
                                         @PathVariable(value = "pageNum") int pageNum,
                                         @PathVariable(value = "pageSize") int pageSize,
                                         @PathVariable(value = "orderBy") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping("keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}")
    @ResponseBody
    public ServerResponse<PageInfo> listRestfulwithoutcategoryId(@PathVariable(value = "keyword")String keyword,
                                                @PathVariable(value = "pageNum") int pageNum,
                                                @PathVariable(value = "pageSize") int pageSize,
                                                @PathVariable(value = "orderBy") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,null,pageNum,pageSize,orderBy);
    }

    @RequestMapping("cateogryId/{categoryId}/{pageNum}/{pageSize}/{orderBy}")
    @ResponseBody
    public ServerResponse<PageInfo> listRestfulwithoutkeyword(@PathVariable(value = "categoryId")Integer categoryId,
                                                                 @PathVariable(value = "pageNum") int pageNum,
                                                                 @PathVariable(value = "pageSize") int pageSize,
                                                                 @PathVariable(value = "orderBy") String orderBy){
        return iProductService.getProductByKeywordCategory("",null,pageNum,pageSize,orderBy);
    }

}
