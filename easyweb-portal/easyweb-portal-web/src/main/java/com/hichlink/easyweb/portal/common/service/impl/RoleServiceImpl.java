package com.aspire.webbas.portal.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.dao.DepartmentDao;
import com.aspire.webbas.portal.common.dao.OperationDao;
import com.aspire.webbas.portal.common.dao.ResourceCategoryDao;
import com.aspire.webbas.portal.common.dao.ResourceDao;
import com.aspire.webbas.portal.common.dao.RoleDao;
import com.aspire.webbas.portal.common.dao.StaffDao;
import com.aspire.webbas.portal.common.entity.Operation;
import com.aspire.webbas.portal.common.entity.ResourceCategory;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.RoleResourceOperation;
import com.aspire.webbas.portal.common.service.RoleService;
import com.aspire.webbas.portal.common.tree.CheckTreeNode;
import com.aspire.webbas.portal.common.tree.TreeBuilder;
import com.aspire.webbas.portal.common.tree.TreeNode;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private StaffDao staffDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private OperationDao operationDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private ResourceCategoryDao resourceCategoryDao;
	
	

	
	@Transactional(rollbackFor=Exception.class) 
	public void createRole(Role role)
			throws Exception
	{
		
		if(null != this.roleDao.findRoleByKey(role.getRoleKey())){
            throw new Exception("角色助记码不能重复！");
        }
        if (null != this.roleDao.findRoleByName(role.getRoleName())) {
            throw new Exception("角色名称不能重复！");
        }
        
        roleDao.insertRole(role);
        
        //insertRoleResourceOperation(role.getRoleId(), roleResourceOperations);
        
        // 
	 }
	
	/**
     * 更新角色.  重新绑定角色和资源的关联，资源的操作权限
     * @param role                      角色基本信息
     * @param roleResourceOperations    角色资源操作关联
     * @return
     */
	@Transactional(rollbackFor=Exception.class) 
    public void updateRole(Role role)
    	throws Exception
    {
    	
    	
    	Role roleTemp = this.roleDao.findRoleByKey(role.getRoleKey());
    	
        // 判断角色助记码是否重复
        if(null != roleTemp 
        		&& !role.getRoleId().equals( roleTemp.getRoleId())){
            throw new Exception("角色助记码不能重复！");
        }
        
        // 判断角色名称是否重复
        roleTemp = this.roleDao.findRoleByName(role.getRoleName());
        if (null != roleTemp && !role.getRoleId().equals(roleTemp.getRoleId()) ) {
            throw new Exception("角色名称不能重复！");
        }

        Role oldRole = roleDao.findRole(role.getRoleId());
        
        if (null == oldRole){
            throw new Exception("角色不存在！");
        }else if(Role.CAN_NOT_MODIFY.equals(oldRole.getCanModify())){
            throw new Exception("此角色不能修改！");
        }

        role.setCanModify(oldRole.getCanModify());
        role.setAutoAssign(oldRole.getAutoAssign());

        //roleDao.updateRole(role, roleResourceOperations);
        
        roleDao.updateRole(role);
        
//        // 先删除
//        roleDao.deleteRoleResourceOperationById(role.getRoleId());
//        // 后插入
//        insertRoleResourceOperation(role.getRoleId(), roleResourceOperations);
        
    }
	
	@Transactional(rollbackFor=Exception.class) 
	public  void deleteRole(Long roleId)
			throws Exception
	{
		if(roleId == null){
            throw new Exception("没有指定要删除的角色[roleId="+roleId+"]");
        }

        Role role = this.roleDao.findRole(roleId);
        if(role == null){
        	throw new Exception("没有指定要删除的角色[roleId="+roleId+"]");
        }

        // 删除角色资源操作关系
        roleDao.deleteRoleResourceOperationById(roleId);
        
        // 删除角色成员之间的关
        staffDao.deleteStaffRolesByRoleId(roleId);
        
        // 删除角色部门之间的关系
        departmentDao.deleteDepartmentRolesByRoleId(roleId);
        
        // 删除角色
        roleDao.deleteRole(roleId);
		
	}
	
	@Transactional(rollbackFor=Exception.class) 
	public Role findRole(Long roleId)
			throws Exception
	{
		
		return roleDao.findRole(roleId);
		
	}
	
	/**
     * 获取角色列表.
     * @param role 角色查询条件
     * @return 角色列表
     */
	@Transactional(rollbackFor=Exception.class) 
    public Page<Role> listRole(Page<Role> page) throws Exception{
		
		// 打开分页参数
//		role.openPagination();
		
//		Page<Role> page = new Page<Role>();
//		page.setRows();
		page.setDatas(roleDao.listRole(page));
		
//		page.setTotal(role.getTotal());
		
		return page;
	}
	
	/**
     * 获取成员角色列表.
     *
     * @param staffId      成员ID
     * @param departmentId 组织ID
     * @return 成员角色列表
     */
	@Transactional(rollbackFor=Exception.class) 
    public List<Role> listStaffRoles(Long staffId) 
    		throws Exception
	{
		return roleDao.listStaffRoles(staffId);
	}
	
	/**
     * 获取组织角色列表.
     *
     * @param departmentId 组织ID
     * @return 组织角色列表
     */
	@Transactional(rollbackFor=Exception.class) 
    public List<Role> listDepartmentRoles(Long departmentId) 
    		throws Exception{
		return roleDao.listDepartmentRoles(departmentId);
	}
	
	/**
     * 
     */
    public List<TreeNode> buildRoleResourceTree(){
    	
    	TreeBuilder tree = new TreeBuilder();
    	
    	addResourceCategory(tree);
    	
    	addResource(tree);
    	
    	addOperation(tree);
    	
    	return tree.buildTree();
    }
    
    /**
     * 按roleId已经分配的资源权限构造资源树
     * @param roleId
     * @return
     */
    public List<TreeNode> buildRoleResourceTree(Long roleId){
	
		TreeBuilder tree = new TreeBuilder();
    	
    	addResourceCategory(tree);
    	
    	addResource(tree);
    	
    	addOperation(tree);
    	
    	// 查找roleId已经分配的资源
		List<Operation> oList = operationDao.listOperationByRoleId(roleId);
		
		for(Operation op : oList){
			String key = op.getResourceId() + "-" + op.getOperationKey();
			tree.markChecked(key);
		}
    	
    	return tree.buildTree();
    }
    
	
	public void updateRoleResource(Long roleId, List<RoleResourceOperation> list){
		
		if(list!=null){
			// 先删除
			roleDao.deleteRoleResourceOperationById(roleId);
			
			// 后增加
	        for(RoleResourceOperation roleResourceOperation : list){
	            roleResourceOperation.setRoleId(roleId);
	            
	            //插入角色资源操作关联.
	            roleDao.insertRoleResourceOperation(roleResourceOperation);
	        }
		}
    }
	
	/**
     * 构建ResourceCategory树
     * @param tree
     */
    private void addResourceCategory(TreeBuilder tree){
    	ResourceCategory category = new ResourceCategory();
		//category.setMetadataId("auth");
		
		List<ResourceCategory> rcs = resourceCategoryDao.listRootResourceCategory(category);
		
//		logger.debug("共找到ResourceCategory["+rcs.size()+"]条记录。");
		
		for(ResourceCategory ca : rcs){
			TreeNode node = new TreeNode(longToString(ca.getCategoryId()),
					                     longToString(ca.getParentId()),
					                     ca.getCategoryName());
			tree.addNode(node);
		}
    }
    
    /**
     * 
     * @param tree
     */
    private void addResource(TreeBuilder tree){
    	com.aspire.webbas.portal.common.entity.Resource param = new com.aspire.webbas.portal.common.entity.Resource();
		//param.setAuthType("AUTH");
		
		List<com.aspire.webbas.portal.common.entity.Resource> rList = resourceDao.listResource(param);
		
//		logger.debug("共找到Resource["+rList.size()+"]条记录。");
		
		for(com.aspire.webbas.portal.common.entity.Resource res : rList){
				TreeNode node = 
						new TreeNode("res-" + longToString(res.getResourceId()), 
								     longToString(res.getCategoryId()),
									 res.getResourceName());
				tree.addNode(node);
		}
	}
    
    /**
     * 
     * @param tree
     */
    private void addOperation(TreeBuilder tree){
		// 构造operation节点
		Operation operParam = new Operation();
		//operParam.setMetadataId("auth");
		
		List<Operation> oList = operationDao.listOperation(operParam);
		
//		logger.debug("共找到Operation["+oList.size()+"]条记录。");
		
		for(Operation op : oList){
			
			CheckTreeNode node = 
					new CheckTreeNode(op.getResourceId()+ "-" + op.getOperationKey(), 
									  "res-" + op.getResourceId(),
									  op.getOperationName());
			tree.addNode(node);	
		}

	}

	public Role findRoleByKey(String roleKey) {
		return roleDao.findRoleByKey(roleKey);
	}
	
	private String longToString(Long value){
		if(value == null){
			return null;
		}
		
		return value.toString();
	}
}
