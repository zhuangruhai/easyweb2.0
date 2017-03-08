package com.aspire.webbas.security.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.webbas.portal.common.dao.OperationDao;
import com.aspire.webbas.portal.common.entity.Operation;
import com.aspire.webbas.test.BaseTest;

public class OperationDaoTest extends BaseTest{

	private OperationDao operationDao;
	
	public void test(){
		
		operationDao = (OperationDao)getBean("operationDao");
		
		assertNotNull("operationDao is null ", operationDao);
		
//		insertOperation();
		
//		deleteOperation();
		
//		updateOperation();
		
//		findOperation();
		
//		listOperation();
		
		listResourceOperationByStaffId();
		
//		listOperationByRoleId();
	}
	
	public void insertOperation(){
		Operation o = new Operation();
		
		o.setResourceId(new Long(10050));
		o.setOperationKey("TEST");
		o.setOperationName("测试");
		
		operationDao.insertOperation(o);
		
		System.out.println("insert operation[id="+o.getOperationId()+"] ok!");
	}
	
	public void deleteOperation(){
		Operation o = new Operation();
		
		o.setResourceId(new Long(10050));
		o.setOperationKey("TEST");
		
		operationDao.deleteOperation(o);
		
		System.out.println("delete operation ok!");
	}
	
	public void updateOperation(){
		Operation o = new Operation();
		
		o.setResourceId(new Long(10050));
		o.setOperationKey("TEST");
		o.setOperationName("测试");
		o.setOperationDesc("测试操作。");
		
		operationDao.updateOperation(o);
		
		System.out.println("update operation ok!");
	}
	
	public void findOperation(){
		
		Operation o = new Operation();
		
		o.setResourceId(new Long(10050));
		o.setOperationKey("EXCLUDE");
		
		Operation newOne = operationDao.findOperation(o);
		
		assertNotNull("find operation is null", newOne);
		
		System.out.println("" + newOne.getOperationName());
		
	}
	
	public void listOperation(){
		Operation o = new Operation();
//		o.setOperationName("测试");
		o.setOperationDesc("测试");
		
		List<Operation> list = operationDao.listOperation(o);
		
		assertNotNull("list operation is null", list);
		
		
		System.out.println("list.szie:" + list.size());
	}
	
	public void listResourceOperationByStaffId(){
		Long staffId = new Long(-999);
		
		List<Operation> list = operationDao.listResourceOperationByStaffId(staffId);
		
		assertNotNull("list operation is null", list);
		
		
		Set<String> set = getResourceAndOperationKey(list);
		
		System.out.println("set.szie:" + set.size());
		
		for(String s : set){
			System.out.println("set:" + s);
		}
	}
	
	public void listOperationByRoleId(){
		Long roleId = new Long(100011);
		
		List<Operation> list = operationDao.listOperationByRoleId(roleId);
		
		assertNotNull("list operation is null", list);
		
		
		System.out.println("list.szie:" + list.size());
	}
	
	private Set<String> getResourceAndOperationKey(List<Operation> list){
//		 = operationDao.listResourceOperationByStaffId(staffId);
		
		Set<String> set = new HashSet<String>();
		
		for(Operation record : list){
			set.add(record.getResourceId() + "-" + record.getOperationKey());
		}
		
		return set;
	}
}
