/**
 * 该文件由自动生成代码器生成
 */
package com.hichlink.easyweb.portal.modules.authmgt.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hichlink.easyweb.core.util.StringTools;
import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.entity.PendTaskSetting;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.PendTaskSettingService;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

/**
 * 
 * <b>Title：</b>PendTaskSettingController.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014-06-20 16:44:42<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * 
 */
@Controller
@RequestMapping("/pendTaskSetting")
public class PendTaskSettingController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(PendTaskSettingController.class);
	@Autowired
	@Qualifier("pendTaskSettingService")
	private PendTaskSettingService pendTaskSettingService;

	@RequestMapping(value = "/update.ajax")
	@ResponseBody
	public Map<String, Object> update(
			@ModelAttribute("pendTaskSetting") PendTaskSetting data) {
		try {
			pendTaskSettingService.saveAndUpdate(data);
		} catch (Exception e) {
			logger.error("更新记录出错", e);
			return super.fail("更新记录发生系统错误");
		}
		return super.success("更新成功");
	}

	@RequestMapping(value = "/get.ajax")
	@ResponseBody
	public Map<String, Object> get() {
		try {
			Staff staff = StaffUtil.getLoginStaff();
			PendTaskSetting data = pendTaskSettingService.get(staff
					.getStaffId().toString());
			Map<String, Object> result = new HashMap<String, Object>();
			if (null != data) {
				if (isEmpty(data.getSendemail())) {
					data.setSendemail(PendTaskSetting.NO);
				}
				if (isEmpty(data.getSendsms())) {
					data.setSendsms(PendTaskSetting.NO);
				}
			}
			if (null != staff){
				staff.setEmail(StringTools.null2Str(staff.getEmail()));
				staff.setMobile(StringTools.null2Str(staff.getMobile()));
			}
			result.put("staff", staff);
			result.put("pendTaskSetting", data);
			return super.success(result);
		} catch (Exception e) {
			logger.error("获取记录出错", e);
			return super.fail("获取记录发生系统错误");
		}
	}
}
