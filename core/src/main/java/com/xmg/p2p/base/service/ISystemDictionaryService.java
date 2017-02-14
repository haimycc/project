package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * 数据字典相关服务
 * Created by Panda on 2016/11/1.
 */
public interface ISystemDictionaryService {

    /**
     * 分页查询 数据字典
     * @param qo
     * @return
     */
    PageResult queryDics(SystemDictionaryQueryObject qo);

    /**
     * 修改或保存分类
     * @param dic
     */
    void saveOrUpdate(SystemDictionary dic);

    /**
     * 分页查询 数据明细
     * @param qo
     * @return
     */
    PageResult queryItem(SystemDictionaryQueryObject qo);

    /**
     * 修改或保存明细
     * @param dic
     */
    void itemSaveOrUpdate(SystemDictionaryItem dic);

    List<SystemDictionary> listAll();

    /**
     * 查询一个数据明细对象
     * @param id
     */
    SystemDictionaryItem selectByPrimaryKey(Long id);

    /**
     * 根据分类查出下拉列表
     * @param sn
     * @return
     */
    List<SystemDictionaryItem> getListBySn(String sn);
}
