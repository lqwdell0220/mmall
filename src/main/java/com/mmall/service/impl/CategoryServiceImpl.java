package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Set;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/20 15:21
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {

        if(parentId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数不合法");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int count = categoryMapper.insertSelective(category);

        if(count>=1){
            return ServerResponse.createBySuccessMessage("商品类别新添加成功");
        }

        return ServerResponse.createByErrorMessage("商品类别添加失败");
    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {

        if(categoryId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新商品参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int count = categoryMapper.updateByPrimaryKeySelective(category);

        if(count >=0){
            return ServerResponse.createBySuccessMessage("商品类别名称修改成功");
        }

        return ServerResponse.createByErrorMessage("商品类别修改失败");
    }

    @Override
    public ServerResponse getChildrenParallelCategory(Integer categoryId) {

        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类下的子节点");
        }

        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse selectCategoryAndChildrenById(Integer categoryId) {

        Set<Category> categorySet = Sets.newHashSet();

        findChildCategory(categorySet,categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId!=null){
            for(Category categoryItem:categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }

        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     *@description 递归：1声明一个容器储存起始节点——>2对起始节点执行查询操作得到其下所有子节点->3循环遍历得到的子节点，对每个子节点都调用当前方法自身->4返回之前声明的集合
     *@param categorySet
     *@param categoryId
     *@author liqiwen
     *@return java.util.Set<com.mmall.pojo.Category>
     *@exception
     *@date 2020/4/21 7:42
     */
    private Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId){

        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
            categorySet.add(category);
        }

        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem:categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }

        return categorySet;

    }
}
