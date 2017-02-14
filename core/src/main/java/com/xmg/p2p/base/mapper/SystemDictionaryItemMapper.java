package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SystemDictionaryItem record);

    int queryForCount(SystemDictionaryQueryObject qo);

    List<SystemDictionary> query(SystemDictionaryQueryObject qo);


    /**
     * 根据sn查询所有的下拉列表
     * @param sn
     * @return
     */
    List<SystemDictionaryItem> getListBySn(String sn);
}