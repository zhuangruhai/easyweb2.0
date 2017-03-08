package com.aspire.webbas.security.dao;

import java.util.List;
import java.util.Map;

import com.aspire.webbas.portal.common.dao.ResourceDao;
import com.aspire.webbas.portal.common.entity.Resource;
import com.aspire.webbas.test.BaseTest;

public class ResourceDaoTest extends BaseTest{

	private ResourceDao dao;
	
	public void test(){
		dao = (ResourceDao)getBean("resourceDao");
		
		assertNotNull("ResourceDao is null ", dao);
		
//		insertResource();
		
//		deleteResourceByMetadataId();
		
//		findResource();
		
//		findResourceByKey();
		
//		listResourceOfStaff();
		
//		listResource();
		
//		listResourceByLoginName();
		
//		listResourceByRoleId();
		
//		listAllResourceAndOperation();
		
		
	}
	
	public void insertResource(){
		Resource r = new Resource();
		
		r.setResourceKey("auth_admin_sys_test");
		r.setResourceName("测试");
		r.setResourceDesc("测试");
		r.setCategoryId(new Long(10044));
		r.setAuthType("auth");
		r.setMetadataId("test");
		r.setDomain("admin");
		r.setOrderKey(100);
		
		dao.insertResource(r);
		
		System.out.println("insert resource[id="+r.getResourceId()+"] ok");
	}
	
	public void deleteResourceByMetadataId(){
		String metadataId = "test";
		
		dao.deleteResourceByMetadataId(metadataId);
		
		System.out.println("delete resource[metadataId="+metadataId+"] ok");
	}
	
	public void findResource(){
		
		Long resourceId = new Long(10061);
		
		Resource r = dao.findResource(resourceId);
		
		assertNotNull("find resource[id="+resourceId+"] is null", r);
		
		System.out.println("find resource[id="+resourceId+"] ok");
		
	}
	
	
	
	public void findResourceByKey(){
		String resourceKey = "auth_admin_sys_test";
		
		Resource r = dao.findResourceByKey(resourceKey);
		
		assertNotNull("find resource[key="+resourceKey+"] is null", r);
		
		System.out.println("find resource[key="+resourceKey+"] ok");
	}
	
	public void listResource(){
		Resource r = new Resource();
		
//		r.setResourceKey("auth_admin_sys_test");
		r.setResourceName("测试");
//		r.setResourceDesc("测试");
//		r.setCategoryId("10044");
//		r.setAuthType("auth");
//		r.setMetadataId("test");
//		r.setDomain("admin");
		
		List<Resource> list = dao.listResource(r);
		
		System.out.println("list resource[size="+list.size()+"]");
	}
	
	public void listResourceByLoginName(){
		String loginName = "sims_admin";
		
		List<Resource> list = dao.listResourceByLoginName(loginName);
		
		System.out.println("list resource by loginName["+loginName+"][size="+list.size()+"]");
	}
	
	public void listResourceByRoleId(){
		Long roleId = new Long(100000);
		
		List<Resource> list = dao.listResourceByRoleId(roleId);
		
		System.out.println("list resource by roleId["+roleId+"][size="+list.size()+"]");
	}
	
	public void listAllResourceAndOperation(){
		
		List<Map<String,String>> list = dao.listAllResourceAndOperation();
		
		System.out.println("list listAllResourceAndOperation[size="+list.size()+"]");
	}
	
	public void listResourceOfStaff(){
		Long staffId = new Long(-999);
		
		List<Map<String,String>> list = dao.listResourceOfStaff(staffId);
		
		System.out.println("list listResourceOfStaff[size="+list.size()+"]");
	}
}
