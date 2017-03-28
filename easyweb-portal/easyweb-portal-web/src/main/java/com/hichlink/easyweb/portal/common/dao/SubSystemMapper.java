/*** Auto generator by codegenerator 2017-03-28 23:12:52*/
package com.hichlink.easyweb.portal.common.dao;

import java.util.List;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.portal.common.entity.SubSystem;

public interface SubSystemMapper {
    int deleteByPrimaryKey(String subSystemId);

    int insert(SubSystem record);

    int insertSelective(SubSystem record);

    List<SubSystem> pageQuery(Page<SubSystem> page);

    SubSystem selectByPrimaryKey(String subSystemId);

    int updateByPrimaryKeySelective(SubSystem record);

    int updateByPrimaryKey(SubSystem record);
}
