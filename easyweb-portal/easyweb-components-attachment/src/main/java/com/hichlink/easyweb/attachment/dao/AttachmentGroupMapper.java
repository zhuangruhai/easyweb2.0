/*** Auto generator by codegenerator 2014-07-30 13:50:47*/
package com.hichlink.easyweb.attachment.dao;

import com.hichlink.easyweb.attachment.entity.AttachmentGroup;
import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import java.util.List;

public interface AttachmentGroupMapper {
    int deleteByPrimaryKey(String attachGroupId);

    int insert(AttachmentGroup record);

    int insertSelective(AttachmentGroup record);

    List<AttachmentGroup> pageQuery(Page<AttachmentGroup> page);

    AttachmentGroup selectByPrimaryKey(String attachGroupId);

    int updateByPrimaryKeySelective(AttachmentGroup record);

    int updateByPrimaryKey(AttachmentGroup record);
    
    void clearTempGroup(Integer days);
    
    List<AttachmentGroup> list(AttachmentGroup record);
}
