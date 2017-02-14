package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.mapper.PlatformBankInfoMapper;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Panda on 2016/11/6.
 */
@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    public int insert(PlatformBankInfo record) {
        return platformBankInfoMapper.insert(record);
    }

    public PlatformBankInfo selectByPrimaryKey(Long id) {
        return platformBankInfoMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(PlatformBankInfo record) {
        return platformBankInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageResult query(PlatformBankInfoQueryObject qo) {
        int totalCount = platformBankInfoMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<PlatformBankInfo> listData = platformBankInfoMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(PlatformBankInfo pb) {
        if (pb.getId() != null) {
            //更新
            platformBankInfoMapper.updateByPrimaryKey(pb);
        } else {
            platformBankInfoMapper.insert(pb);
        }
    }

    public List<PlatformBankInfo> list() {
        return platformBankInfoMapper.listAll();
    }
}
