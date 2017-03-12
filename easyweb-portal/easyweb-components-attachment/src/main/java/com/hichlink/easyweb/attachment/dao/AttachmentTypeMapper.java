/*** Auto generator by codegenerator 2014-07-30 13:50:56*/
package com.hichlink.easyweb.attachment.dao;

import com.hichlink.easyweb.attachment.entity.AttachmentType;
import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import java.util.List;

public interface AttachmentTypeMapper {
    int deleteByPrimaryKey(String attachTypeId);

    int insert(AttachmentType record);

    int insertSelective(AttachmentType record);

    List<AttachmentType> pageQuery(Page<AttachmentType> page);

    AttachmentType selectByPrimaryKey(String attachTypeId);

    int updateByPrimaryKeySelective(AttachmentType record);

    int updateByPrimaryKey(AttachmentType record);
    
    List<AttachmentType> list(AttachmentType record);
}
