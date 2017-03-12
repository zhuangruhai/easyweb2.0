/*** Auto generator by codegenerator 2014-06-20 16:55:03*/
package com.hichlink.easyweb.portal.common.dao;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.portal.common.entity.PendTaskSetting;
import java.util.List;

public interface PendTaskSettingMapper {
    int deleteByPrimaryKey(String staffid);

    int insert(PendTaskSetting record);

    int insertSelective(PendTaskSetting record);

    List<PendTaskSetting> pageQuery(Page<PendTaskSetting> page);

    PendTaskSetting selectByPrimaryKey(String staffid);

    int updateByPrimaryKeySelective(PendTaskSetting record);

    int updateByPrimaryKey(PendTaskSetting record);
}
