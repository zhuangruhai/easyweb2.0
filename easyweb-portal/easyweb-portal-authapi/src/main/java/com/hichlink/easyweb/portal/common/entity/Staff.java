package com.hichlink.easyweb.portal.common.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 用户域对象.
 */
public class Staff extends BaseEntity implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 6309997035381648458L;

    /**
     * 成员ID.
     */
    private Long staffId;

    /**
     * 登录名.
     */
    private String loginName;

    /**
     * 组织ID.
     */
    private Long departmentId;

    /**
     * 成员姓名.
     */
    private String realName;

    /**
     * 密码.
     */
    @JsonIgnore
    private String password;

    /**
     * 成员状态.
     */
    private Status status;

    /**
     * 性别：MALE-男性；FEMALE-女性；UNKNOWN-未知.
     */
    private Sex sex;

    /**
     * 电话.
     */
    private String telephone;

    /**
     * 手机号码.
     */
    private String mobile;

    /**
     * 邮件地址.
     */
    private String email;

    /**
     * 成员创建者.
     */
    @JsonIgnore
    private String createUser;

    /**
     * 成员创建时间.
     */
    @JsonIgnore
    private Date createDate;

    /**
     * 帐号过期时间.
     */
    @JsonIgnore
    private Date expireDate;
    
    @JsonIgnore
    private String expireDateStr;

	/**
     * 锁定时间.
     */
    private Date lockDate;
    
    /**
     * 密码过期时间.
     */
    @JsonIgnore
    private Date passwordExpireDate;

    /**
     * 最后修改时间.
     */
    @JsonIgnore
    private Date lastUpdateDate;

    /**
     * 成员组织对象.
     */
    private Department department;


	/**
     * 成员扩展属性列表.
     */
    private Map<String, String> extendProperties = new HashMap<String, String>();


	@JsonIgnore
    private List<Role> roles;
    
    /**
     * 选中选项，为前台页面使用
     */
    @JsonIgnore
    private boolean choose = false;

    public boolean isChoose() {
		return choose;
	}

	public void setChoose(boolean choose) {
		this.choose = choose;
	}



    /**
     * 地市号.
     */
    private Integer cityId;

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Staff) {
//            Staff staff = (Staff) obj;
//            return staff.getStaffId().equals(this.staffId);
//        }
//        return false;
//    }

    /*
     * @see java.lang.Object#hashCode()
     */
//    @Override
//    public int hashCode() {
//        if (this.staffId == null) {
//            return 0;
//        }
//        return this.staffId.hashCode();
//    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordExpireDate() {
        return passwordExpireDate;
    }

    public void setPasswordExpireDate(Date passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Sex getSex() {
        return sex;
    }
    
    public String getSexName(){
    	return sex.toCnString();    
    }
 
    /*public void setSex(String sexString) {
        this.sex = Sex.valueOf(sexString);
    }*/
    
    public void setSex(Sex sex) {
        this.sex =sex;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Status getStatus() {
        return status;
    }

    /*public void setStatus(String statusString) {
        this.status = Status.valueOf(statusString);
    }*/
    public void setStatus(Status status) {
        this.status =status;
    }
    
    public String getStatusName(){
    	return status.toCnString();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    
    public String getExpireDateStr() {
    	SimpleDateFormat  df = new SimpleDateFormat ("yyyy-MM-dd");
    	if(expireDate != null)
    		return  df.format(expireDate);
    	else
    		return "";
	}
    
    public Map<String, String> getExtendProperties() {
		return extendProperties;
	}

	public void setExtendProperties(Map<String, String> extendProperties) {
		this.extendProperties = extendProperties;
	}
	
	public String getExtendProperty(String name){
		return extendProperties.get(name);
	}
	
	public void addExtendProperty(String name, String value){
		extendProperties.put(name, value);
	}
    
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
    public enum Status {
    	/**
    	 * 成员状态类型.
    	 */
    	INITIAL, NORMAL, AUTO_LOCKED, LOCKED, PASSWORD_EXPIRED, EXPIRED, DELETED;
    	
    	/**
    	 * 获取枚举值的描述，主要用于iBatis的SQLMap.
    	 * @return 枚举值的描述
    	 */
    	public String getValue() {
    		return this.toString();
    	}

        public String toCnString() {
            switch (this){
                case INITIAL:
                    return "初创";
                case NORMAL:
                    return "正常";
                case AUTO_LOCKED:
                    return "自动锁定";
                case LOCKED:
                    return "锁定";
                case PASSWORD_EXPIRED:
                    return "密码过期";
                case EXPIRED:
                    return "帐号过期";
                case DELETED:
                    return "删除";
                default:
                    return "未知";
            }
    	}
    }
    
    public enum Sex {
    	/**
    	 * 性别类型.
    	 */
    	MALE, FEMALE, UNKNOWN;

    	/**
    	 * 获取枚举值的描述，主要用于iBatis的SQLMap.
    	 * @return 枚举值的描述
    	 */
    	public String getValue() {
    		return this.toString();
    	}
    	
    	public String toCnString() {
            switch (this){
                case MALE:
                    return "男";
                case FEMALE:
                    return "女";
                default:
                    return "未知";
            }
    	}
    }
}