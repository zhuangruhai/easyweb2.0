package com.aspire.webbas.portal.common.dao;

import java.util.List;

import com.aspire.webbas.portal.common.entity.ResourceCategory;

public interface ResourceCategoryDao {

	/**
	 * 新增资源分类
	 * @param category
	 */
	void insertResourceCategory(ResourceCategory category);
	
	/**
     * 删除资源分类
     * @param id
     */
    void deleteResourceCategoryById(Long id);
    
    /**
     * 通过元数据ID删除资源分类.
     * @param metadataId 元数据ID
     */
    void deleteResourceCategoryByMetadataId(String metadataId);
    
    /**
     * 更新资源分类
     * @param category
     */
    void updateResourceCategory(ResourceCategory category);
    
    
	ResourceCategory findResourceCategory(Long resourceCategoryId); 
	
	/**
     * 根据KEY查资源分类
     * @param resourceCategoryId
     * @return
     */
    ResourceCategory findResourceCategoryByKey(String resourceCategoryKey); 
	
	List<ResourceCategory> listResourceCategory(ResourceCategory category);
	
	List<ResourceCategory> listRootResourceCategory(ResourceCategory category);
	
	/**
     * 获取顶层的资源分类列表
     * @param parentId 资源分类的父ID，如果是顶出节点，则parentId为null
     * @return
     */
    List<ResourceCategory> listResourceCategoryByParentId(Long parentId);
}
