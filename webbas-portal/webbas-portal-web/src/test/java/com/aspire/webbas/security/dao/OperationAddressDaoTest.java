package com.aspire.webbas.security.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.dao.OperationAddressDao;
import com.aspire.webbas.portal.common.entity.OperationAddress;
import com.aspire.webbas.test.BaseTest;

public class OperationAddressDaoTest extends BaseTest{

	OperationAddressDao operationAddressDao;
	
	public void test(){
		operationAddressDao = (OperationAddressDao)getBean("operationAddressDao");
	
		assertNotNull("OperationAddress Dao is null", operationAddressDao);
		
//		insertOperationAddress();
		
//		deleteOperationAddress();
		
//		findOperationAddress("/auth/listStaff.ajax");
		
//		findOperationAddressByStaff("-999","10058","QUERY");
		
//		authURLByStaff("-999", "/auth/listStaff.ajax");
		
//		listOperationAddress();
		
//		listOperationAddressByLoginName();
		
		listOperationAddressByAuthType();
		
	}
	
	public void findOperationAddress(String url){
		assertNotNull("OperationAddress Dao is null", operationAddressDao);
		
		OperationAddress oa = operationAddressDao.findOperationAddress(url);
		
		if(oa != null){
			System.out.println("找到相应资源resourceId=["+oa.getResourceId()+"],operationKey=["+oa.getOperationKey()+"]");
		}else{
			System.out.println("没有找到资源["+url+"].");
		}
	}
	
	public void insertOperationAddress(){
		assertNotNull("OperationAddress Dao is null", operationAddressDao);
		
		OperationAddress oa = new OperationAddress();
		
//		oa.setAddressId("1");
		oa.setResourceId(new Long(10050));
		oa.setOperationKey("TEST");
		oa.setOperationAddressName("测试链接");
		oa.setOperationAddressUrl("test.html");
		oa.setMetadataId("exclude_portal_admin");
		oa.setDomain("admin");
		
		operationAddressDao.insertOperationAddress(oa);
		
		System.out.println("insert operation address[id="+oa.getAddressId()+"] ok!");
	}
	
	public void deleteOperationAddress(){
		assertNotNull("OperationAddress Dao is null", operationAddressDao);
		
		OperationAddress oa = new OperationAddress();

		oa.setResourceId(new Long(10050));
		oa.setOperationKey("TEST");
		
		operationAddressDao.deleteOperationAddress(oa);
		
		System.out.println("delete operation address[resourceId="+oa.getResourceId()+",operationKey="+oa.getOperationKey()+"] ok!");
	}
	
	public void findOperationAddressByStaff(String staffId,String resourceId,String operationKey){
		assertNotNull("OperationAddress Dao is null", operationAddressDao);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("staffId", staffId);
		params.put("resourceId", resourceId);
		params.put("operationKey", operationKey);
		
		Integer result = operationAddressDao.findOperationAddressByStaff(params);
		
		System.out.println("result = " + result);
	}
	
	public void authURLByStaff(String staffId, String url){
		
		assertNotNull("OperationAddress Dao is null", operationAddressDao);
		Integer result = 0;
		long start = System.currentTimeMillis();
//		for(int i = 0; i < 600; i++){
		OperationAddress oa = operationAddressDao.findOperationAddress(url);
		
		if(oa != null){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("staffId", staffId);
			params.put("resourceId", oa.getResourceId());
			params.put("operationKey", oa.getOperationKey());
			
			result = operationAddressDao.findOperationAddressByStaff(params);
		}
//		}
		long end = System.currentTimeMillis();
		
		System.out.println("auth [staffId="+staffId+",url="+url+"] is " + result);
		System.out.println("auth 总耗时：" + (end - start));
		
	}
	
	public void listOperationAddress(){
		
		
//		OperationAddress oa = new OperationAddress();
//		oa.setResourceId("10050");
//		oa.setOperationKey("EXCLUDE");
//		oa.setOperationAddressName("测试");
		
		OperationAddress oa = new OperationAddress();
//		Page<OperationAddress> page = new Page<OperationAddress>();
		
		List<OperationAddress> list = operationAddressDao.listOperationAddress(oa);
		
		System.out.println("list OperationAddress ok!");
//		System.out.println("pageSize:"+ page.getPageSize());
//		System.out.println("totalSize:"+ page.getTotal());
//		System.out.println("totalPage:" + page.getTotalPage());
		System.out.println("[list.size:"+list.size()+"]");
	}
	
	public void listOperationAddressByLoginName(){
		String loginName = "sims_admin";
		String url = "/auth/findDepartmentWithPath.ajax?p=v";
		
		List<OperationAddress> list = operationAddressDao.listOperationAddressByLoginName(loginName);
		
		System.out.println("list OperationAddress ok!");
		System.out.println("[list.size:"+list.size()+"]");
		
		Map<String,String> urlMap = new HashMap<String,String>();
		
		for(OperationAddress address : list){
			StringBuffer paramStr = new StringBuffer(); 
			
			if(!StringUtils.isEmpty(address.getParameterName1())){
				paramStr.append(address.getParameterName1())
				        .append("=")
				        .append(address.getParameterValue1())
				        .append("&");
			}
			
			if(!StringUtils.isEmpty(address.getParameterName2())){
				paramStr.append(address.getParameterName2())
				        .append("=")
				        .append(address.getParameterValue2())
				        .append("&");
			}
			
			if(!StringUtils.isEmpty(address.getParameterName3())){
				paramStr.append(address.getParameterName3())
				        .append("=")
				        .append(address.getParameterValue3())
				        .append("&");
			}
			
			urlMap.put(address.getOperationAddressUrl(), paramStr.toString());
		}
		
		String strs[] = url.split("\\?");
		String paramsStr = urlMap.get(strs[0]);
		
		System.out.println("url=" + strs[0]);
		System.out.println("param:" + paramsStr);
		
		if(strs.length >= 2){
			String[] params = splitParam(strs[1]); 
			
			for(String p : params){
				System.out.println("param:" + p);
				if(paramsStr.indexOf(p) == -1){
					System.out.println("authorize  params false");
				}
			}
		}		
	}
	
	public void listOperationAddressByAuthType(){
		String authType = "UNAUTH";
		
		List<OperationAddress> list = operationAddressDao.listOperationAddressByAuthType(authType);
		
		System.out.println("size:" + list.size());
		
		for(OperationAddress oa : list){
			System.out.println("" + oa.getAddressId() + ":" + oa.getOperationAddressUrl());
		}
	}
	
	private String[] splitParam(String params){
    	// 拆分URL和参数（根据?号拆分）
//        String strArray[] = url.split("\\?");
//        String paramStr = null;
//        if (strArray.length >= 2) {
//            paramStr = strArray[1];
//            String paramArray[] = paramStr.split("\\&");
            
            
//            String paramArray[] = params.split("\\&");
//        }
		
		if(params == null){
			return new String[0];
		}
        
        return params.split("\\&");
    }
}
