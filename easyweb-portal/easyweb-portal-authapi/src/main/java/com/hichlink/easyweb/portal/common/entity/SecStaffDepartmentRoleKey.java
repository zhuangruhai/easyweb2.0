/*** Auto generator by codegenerator 2014-07-18 11:11:09*/
package com.hichlink.easyweb.portal.common.entity;

public class SecStaffDepartmentRoleKey {
    /**
    成员ID
     */
    private String staffId;

    /**
    组织ID
     */
    private String departmentId;

    /**
    角色ID
     */
    private Long roleId;

    /**
    成员ID
     * @return the value of SEC_STAFF_DEPARTMENT_ROLE.STAFF_ID
     */
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId == null ? null : staffId.trim();
    }

    /**
    组织ID
     * @return the value of SEC_STAFF_DEPARTMENT_ROLE.DEPARTMENT_ID
     */
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
    角色ID
     * @return the value of SEC_STAFF_DEPARTMENT_ROLE.ROLE_ID
     */
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}