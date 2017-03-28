package com.hichlink.easyweb.portal.modules.authmgt.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.core.util.StringTools;
import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.entity.SubSystem;
import com.hichlink.easyweb.portal.common.service.impl.SubSystemService;

/**
 * 
 * <b>Title：</b>SubSystemController.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2017-03-28 23:12:52<br/>
 * <b>Copyright (c) 2017 HichLink Tech.</b>
 * 
 */
@Controller
@RequestMapping("/subSystem")
public class SubSystemController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(SubSystemController.class);
	@Autowired
	@Qualifier("subSystemService")
	private SubSystemService subSystemService;

	@RequestMapping(value = "/query.ajax")
	@ResponseBody
	public Map<String, Object> pageQuery(Page<SubSystem> page) {
		Page<SubSystem> list = subSystemService.pageQuery(page);
		return super.page(list);
	}

	@RequestMapping(value = "/add.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@ModelAttribute("subSystem") SubSystem data) {
		subSystemService.saveAndUpdate(data);
		return super.success("新增成功");
	}

	@RequestMapping(value = "/delete.ajax")
	@ResponseBody
	public Map<String, Object> delete(String subSystemId) {
		subSystemService.delete(subSystemId);
		return super.success("删除成功");
	}

	@RequestMapping(value = "/get.ajax")
	@ResponseBody
	public Map<String, Object> get(String subSystemId) {
		SubSystem data = subSystemService.get(subSystemId);
		return super.success(data);
	}

	/**
	 * 更新的时候需额外传递updId,值跟主键值一样,被@ModelAttribute注释的方法会在此controller每个方法执行前被执行，要谨慎使用
	 */
	@ModelAttribute("subSystem")
	public void getForUpdate(@RequestParam(value = "updId", required = false) String updId, Model model) {
		if (StringTools.isNotEmptyString(updId)) {
			model.addAttribute("subSystem", subSystemService.get(updId));
		} else {
			model.addAttribute("subSystem", new SubSystem());
		}
	}
}