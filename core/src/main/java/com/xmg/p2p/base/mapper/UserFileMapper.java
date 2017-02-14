package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {

    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserFile record);

    int queryForCount(UserFileQueryObject qo);

    List<UserFile> query(UserFileQueryObject qo);

    List<UserFile> getSelectList(@Param("id") Long id,@Param("select")  boolean select);
}