package com.hichlink.easyweb.portal.common.dao;

import java.util.List;
import java.util.Map;

import com.hichlink.easyweb.portal.common.entity.Resource;

public interface ResourceDao {

	/**
     * 插入资源.
     * @param resource 资源对象
     * @return 资源ID
     */
    void insertResource(Resource resource);
    
    /**
     * 删除资源（通过元数据ID）.
     * @param metadataId 元数据ID
     */
    void deleteResourceByMetadataId(String metadataId);
    
    /**
     * 更新资源
     * @param resource
     */
    void updateResource(Resource resource);
    
	/**
     * 获取资源信息.
     * @param resourceId 资源ID
     * @return 资源对象
     */
    Resource findResource(Long resourceId);
    
    /**
     * 获取资源信息（通过资源外码）.
     * @param resourceKey 资源外码
     * @return 资源对象
     */
    Resource findResourceByKey(String resourceKey);
    /**
     * 获取资源列表.
     * @param resource 资源查询条件
     * @return 资源列表
     */
    List<Resource> listResource(Resource resource); 
    
    /**
     * 通过成员登录名获取资源列表.
     * @param loginName 成员登录名
     * @return 资源列表
     */
    List<Resource> listResourceByLoginName(String loginName);
    
    
    /**
     * 获取资源列表
     * @param roleId 角色ID
     * @return
     */
    List<Resource> listResourceByRoleId(Long roleId);
    
    /**
     * 获取资源表和操作表所有的关联关系.
     * @return 
     */
    List<Map<String,String>> listAllResourceAndOperation();
    
    /**
     * 根据staffId查找staff已分配所有的resource
     * @param staffId
     * @return
     */
    List<Map<String,String>> listResourceOfStaff(Long staffId);
}
