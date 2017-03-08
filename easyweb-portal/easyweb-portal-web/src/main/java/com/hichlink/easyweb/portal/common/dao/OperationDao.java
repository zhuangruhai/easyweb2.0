package com.aspire.webbas.portal.common.dao;

import java.util.List;

import com.aspire.webbas.portal.common.entity.Operation;

public interface OperationDao {

	void insertOperation(Operation o);
	
	void deleteOperation(Operation o);
	
	void updateOperation(Operation o);
	
	Operation findOperation(Operation o);
	
	List<Operation> listOperation(Operation o);
	
	List<Operation> listResourceOperationByStaffId(Long staffId);
	
	List<Operation> listResourceKeyAndOperationKeyStaffId(Long staffId);
	 
	List<Operation> listOperationByRoleId(Long roleId);
	
}
