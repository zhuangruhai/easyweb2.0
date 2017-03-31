package com.hichlink.easyweb.portal.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.core.util.StringTools;
import com.hichlink.easyweb.portal.common.dao.SubSystemMapper;
import com.hichlink.easyweb.portal.common.entity.SubSystem;

/**
 * 
 * <b>Title：</b>SubSystemService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>oceanzhuang<br/>
 * <b>@date：</b>2017-03-28 23:12:52<br/>
 * <b>Copyright (c) 2017 HichLink Tech.</b>
 * 
 */
@Service("subSystemService")
public class SubSystemService {
	@Autowired
	private SubSystemMapper subSystemMapper;

	public Page<SubSystem> pageQuery(Page<SubSystem> page) {
		List<SubSystem> list = subSystemMapper.pageQuery(page);
		page.setDatas(list);
		return page;
	}

	public void insert(SubSystem data) {
		subSystemMapper.insert(data);
	}

	public SubSystem get(String subSystemId) {
		return subSystemMapper.selectByPrimaryKey(subSystemId);
	}

	public void saveAndUpdate(SubSystem data) {
		if (StringTools.isNotEmptyString(data.getUpdId())) {// 判断有没有传主键，如果传了为更新，否则为新增
			this.update(data);
		} else {
			this.insert(data);
		}
	}

	public void update(SubSystem data) {
		subSystemMapper.updateByPrimaryKey(data);
	}

	public int delete(String subSystemId) {
		return subSystemMapper.deleteByPrimaryKey(subSystemId);
	}
}