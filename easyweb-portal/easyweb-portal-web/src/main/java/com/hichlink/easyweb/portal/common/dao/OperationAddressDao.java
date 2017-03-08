package com.aspire.webbas.portal.common.dao;

import java.util.List;
import java.util.Map;

import com.aspire.webbas.portal.common.entity.OperationAddress;

public interface OperationAddressDao {

	/**
     * 插入操作地址.
     * @param address 操作地址对象
     * @return 操作地址ID
     */
    void insertOperationAddress(OperationAddress address);
    
    
    /**
     *  删除操作地址
     * @param operation
     */
    void deleteOperationAddress(OperationAddress address);
    
    
	OperationAddress findOperationAddress(String operationAddressUrl);
	
	/**
     * 获取操作地址列表.
     * @param address 操作地址查询条件
     * @return 操作地址列表
     */
    List<OperationAddress> listOperationAddress(OperationAddress oa);
    
    List<OperationAddress> listOperationAddressByLoginName(String loginName);
    
    Integer findOperationAddressByStaff(Map<String,Object> params);
    
    List<OperationAddress> listOperationAddressByAuthType(String authType);
}
