package com.aspire.webbas.security.dao;

import java.util.List;

import com.aspire.webbas.portal.common.dao.ResourceCategoryDao;
import com.aspire.webbas.portal.common.entity.ResourceCategory;
import com.aspire.webbas.test.BaseTest;

public class ResourceCategoryDaoTest extends BaseTest{

	private ResourceCategoryDao dao;
	
	public void test(){
		dao = (ResourceCategoryDao)getBean("resourceCategoryDao");
		
		assertNotNull("ResourceCategoryDao is null ", dao);
		
//		insertResourceCategory();
		
//		deleteResourceCategory();
		
//		deleteResourceCategoryByMetadataId();
		
//		updateResourceCategory();
		
//		findResourceCategory();
		
//		findResourceCategoryByKey();
		
//		listResourceCategory();
		
//		listResourceCategoryByParentId();
	}
	
	public void insertResourceCategory(){
		ResourceCategory rc = new ResourceCategory();
		
		rc.setParentId(null);
		rc.setCategoryName("测试分类");
		rc.setCategoryDesc("测试分类");
		rc.setMetadataId("test");
		rc.setCategoryKey("auth_admin_sys_test");
		rc.setDomain("admin");
		rc.setOrderKey(100);
		
		dao.insertResourceCategory(rc);
		
		System.out.println("insert ResourceCategory[id="+rc.getCategoryId()+"] ok!");
	}
	
	public void updateResourceCategory(){
		ResourceCategory rc = new ResourceCategory();
		
		rc.setCategoryId(new Long(10041));
		rc.setParentId(null);
		rc.setCategoryName("测试分类2");
		rc.setCategoryDesc("测试分类2");
		rc.setMetadataId("auth");
		rc.setCategoryKey("auth_admin_sys_test");
		rc.setDomain("admin");
		rc.setOrderKey(100);
		
		dao.updateResourceCategory(rc);
		
		System.out.println("update ResourceCategory[id="+rc.getCategoryId()+"] ok!");
	}
	
	public void deleteResourceCategory(){
	
		Long id = new Long(10041);
		
		dao.deleteResourceCategoryById(id);
		
		System.out.println("delete ResourceCategory[id="+id+"] ok!");
	}
	
	public void deleteResourceCategoryByMetadataId(){
		
		String metadataId = "test";
		
		dao.deleteResourceCategoryByMetadataId(metadataId);
		
		System.out.println("delete ResourceCategory[metadataId="+metadataId+"] ok!");
	}
	
	public void findResourceCategory(){
		
		Long resourceCategoryId = new Long(10032);
		
		ResourceCategory rc = dao.findResourceCategory(resourceCategoryId);
		
		assertNotNull("find  ResourceCategory[id="+resourceCategoryId+"]", rc);
		
		System.out.println("find ResourceCategory[id="+resourceCategoryId+"] ok");
		
	}
	
	public void findResourceCategoryByKey(){
		String resourceCategoryKey = "auth_admin_sys_test";
		
		ResourceCategory rc = dao.findResourceCategoryByKey(resourceCategoryKey);
		
		assertNotNull("find  ResourceCategory[key="+resourceCategoryKey+"]", rc);
		
		System.out.println("find ResourceCategory[key="+resourceCategoryKey+"] ok");
	}
	
	
	public void listResourceCategory(){
		
		ResourceCategory rc = new ResourceCategory();
		
		rc.setCategoryName("测试");
//		rc.setMetadataId("auth");
		
		List<ResourceCategory> list = dao.listResourceCategory(rc);
		
		System.out.println("list.size:" + list.size());
	}
	
	
	public void listResourceCategoryByParentId(){
		
		Long parentId = new Long(10032);
		
		List<ResourceCategory> list = dao.listResourceCategoryByParentId(parentId);
		
		System.out.println("list.size:" + list.size());
	}
}
