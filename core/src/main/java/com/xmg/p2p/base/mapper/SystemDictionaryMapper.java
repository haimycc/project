package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SystemDictionary record);

    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionary> query(SystemDictionaryQueryObject qo);

    List<SystemDictionary> listAll();
}