package com.hichlink.easyweb.portal.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 组织域对象.
 */
public class Department extends BaseEntity {


    public static final String ROOT_DEPARTMENT_NAME = "根组织";

    public static final String PATH = "path";

    public static final String PATH_SEPARATOR = "/";
    
    public static final String ZW_SYS_ADMIN = "管理员";
    
    public static final String ZW_SP = "SP";
    
    public static final String SYS_ADMIN = "SYS_ADMIN";
    
    public static final String SP = "SP";
    
    
    public static final long ROOT_DEPARTMENT_ID = -999;

    /**
	 * 
	 */
	private static final long serialVersionUID = 3829319798451942102L;

	/**
	 * 组织ID.
	 */
	private Long departmentId;

	/**
	 * 组织名称.
	 */
	private String departmentName;

	/**
	 * 组织描述.
	 */
	private String departmentDesc;

	/**
	 * 父级组织.
	 */
	private Long parentId;

	/**
	 * 组织的邮件.
	 */
	private String email;

	/**
	 * 组织地址.
	 */
	private String address;

	/**
	 * 是否可以创建下级组织.
	 */
	private String addSub;

	/**
	 * 组织创建者.
	 */
	@JsonIgnore
	private String createUser;

	/**
	 * 组织创建时间.
	 */
	@JsonIgnore
	private Date createDate;

	/**
	 * 组织最后修改时间.
	 */
	@JsonIgnore
	private Date lastUpdateDate;

	private String domain;

	@JsonIgnore
    private List<Staff> staffs;

	@JsonIgnore
    private List<Role> roles;

	@JsonIgnore
    private List<Department> subDepartments;
    
	@JsonIgnore
    private Department parent;
    
    @JsonIgnore
    private List<Department> childs = new ArrayList<Department>();
    

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
//    @Override
//	public boolean equals(Object obj) {
//		if (obj instanceof Department) {
//			Department department = (Department) obj;
//			return department.getDepartmentId().equals(this.departmentId);
//		}
//		return false;
//	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
//    @Override
//	public int hashCode() {
//		if (this.departmentId == null) {
//			return 0;
//		}
//		return this.departmentId.hashCode();
//	}

	/**
	 * 
	 */
	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId The departmentId to set.
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return Returns the departmentName.
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName The departmentName to set.
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return Returns the departmentDesc.
	 */
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**
	 * @param departmentDesc The departmentDesc to set.
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	/**
	 * @return Returns the parentId.
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId The parentId to set.
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the addSub.
	 */
	public String getAddSub() {
		return addSub;
	}

	/**
	 * @param addSub The addSub to set.
	 */
	public void setAddSub(String addSub) {
		this.addSub = addSub;
	}

	/**
	 * @return Returns the createUser.
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser The createUser to set.
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return Returns the createDate.
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return Returns the lastUpdateDate.
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate The lastUpdateDate to set.
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return Returns the domain.
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain The domain to set.
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if(this.roles == null){
            this.roles = new ArrayList<Role>();
        }
        this.roles.add(role);
    }

    public List<Department> getSubDepartments() {
        return subDepartments;
    }

    public void setSubDepartments(List<Department> subDepartments) {
        this.subDepartments = subDepartments;
    }


    public void addSubDepartment(Department department) {
        if(this.subDepartments == null){
            this.subDepartments = new ArrayList<Department>();
        }
        this.subDepartments.add(department);
    }

    public boolean isRootDepartment(){
        return this.parentId==null;
    }

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public List<Department> getChilds() {
		return childs;
	}

	public void setChilds(List<Department> childs) {
		this.childs = childs;
	}
	
	public void addChild(Department dep){
		childs.add(dep);
	}
}
