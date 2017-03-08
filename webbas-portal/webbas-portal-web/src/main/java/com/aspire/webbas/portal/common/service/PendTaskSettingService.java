/**
* 该文件由自动生成代码器生成
*/
package com.aspire.webbas.portal.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.dao.PendTaskSettingMapper;
import com.aspire.webbas.portal.common.entity.PendTaskSetting;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.util.StaffUtil;


/**
 * 
 * <b>Title：</b>PendTaskSettingService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014-06-20 16:44:42<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * 
 */
@Service("pendTaskSettingService")
public class PendTaskSettingService{
    @Autowired
    private PendTaskSettingMapper pendTaskSettingMapper;
    
    public PendTaskSetting get(String staffid) {
        return pendTaskSettingMapper.selectByPrimaryKey(staffid);
    }
    @Transactional(rollbackFor=Exception.class) 
    public void insert(PendTaskSetting data) {
        pendTaskSettingMapper.insert(data);
    }
    @Transactional(rollbackFor=Exception.class) 
    public void saveAndUpdate(PendTaskSetting data) throws Exception{
    	Staff staff = StaffUtil.getLoginStaff();
    	data.setStaffid(staff.getStaffId().toString());
		PendTaskSetting pendTaskSetting = get(staff.getStaffId().toString());
		if (null != pendTaskSetting){
			this.update(data);
		}else{
			this.insert(data);
		}
    }
    public Page<PendTaskSetting> pageQuery(Page<PendTaskSetting> page) {
    	List<PendTaskSetting> list = pendTaskSettingMapper.pageQuery(page);
		page.setDatas(list);
        return page;
    }
    @Transactional(rollbackFor=Exception.class) 
    public void update(PendTaskSetting data) {
        pendTaskSettingMapper.updateByPrimaryKey(data);
    }
    @Transactional(rollbackFor=Exception.class) 
    public int delete(String staffid) {
        return pendTaskSettingMapper.deleteByPrimaryKey(staffid);
    }
}
