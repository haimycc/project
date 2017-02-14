package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryItemMapper;
import com.xmg.p2p.base.mapper.SystemDictionaryMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Panda on 2016/11/1.
 */
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;


    public PageResult queryDics(SystemDictionaryQueryObject qo) {
        int totalCount = systemDictionaryMapper.queryForCount(qo);
        if (totalCount <= 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<SystemDictionary> listData = systemDictionaryMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void saveOrUpdate(SystemDictionary dic) {
        if (dic.getId() != null) {
            systemDictionaryMapper.updateByPrimaryKey(dic);
        } else {
            systemDictionaryMapper.insert(dic);
        }
    }

    public PageResult queryItem(SystemDictionaryQueryObject qo) {
        int totalCount = systemDictionaryItemMapper.queryForCount(qo);
        if (totalCount <= 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<SystemDictionary> listData = systemDictionaryItemMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void itemSaveOrUpdate(SystemDictionaryItem item) {
        if (item.getId() != null) {
            systemDictionaryItemMapper.updateByPrimaryKey(item);
        } else {
            systemDictionaryItemMapper.insert(item);
        }
    }

    public List<SystemDictionary> listAll() {
        return systemDictionaryMapper.listAll();
    }

    @Override
    public SystemDictionaryItem selectByPrimaryKey(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    public List<SystemDictionaryItem> getListBySn(String sn) {
        return systemDictionaryItemMapper.getListBySn(sn);
    }

}
