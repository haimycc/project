package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Panda on 2016/11/3.
 */
@Service
public class UserFileServiceImpl implements IUserFileService {
    @Autowired
    private UserFileMapper userFileMapper;
    @Autowired
    private IUserInfoService userInfoService;

    public List<UserFile> getSelectList(Long id, boolean select) {
        return userFileMapper.getSelectList(id, select);
    }

    public void apply(String fileName) {
        UserFile userFile = new UserFile();
        userFile.setApplier(UserContext.getCurrent());
        userFile.setApplyTime(new Date());
        userFile.setImage(fileName);
        userFile.setState(UserFile.STATE_NORMAL);
        userFileMapper.insert(userFile);
    }

    public void saveFileType(Long[] id, Long[] fileType) {
        //保存图片的分类,判断id是否为空
        if (id.length > 0 && id.length == fileType.length) {
            for (int i = 0; i < id.length; i++) {
                UserFile userFile = userFileMapper.selectByPrimaryKey(id[i]);
                SystemDictionaryItem si = new SystemDictionaryItem();
                si.setId(fileType[i]);
                userFile.setFileType(si);
                userFileMapper.updateByPrimaryKey(userFile);
            }
        }
    }

    public PageResult query(UserFileQueryObject qo) {
        int totalCount = userFileMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        List<UserFile> listData = userFileMapper.query(qo);
        return new PageResult(listData, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id, String remark, int score, int state) {
        //得到要审核的资料对象
        UserFile userFile = userFileMapper.selectByPrimaryKey(id);
        //如果资料对象不为空,并且未审核,就执行审核操作
        if (userFile != null) {
            userFile.setRemark(remark);
            userFile.setAuditTime(new Date());
            userFile.setAuditor(UserContext.getCurrent());
            if (state == UserFile.STATE_REJECT) {
                userFile.setScore(UserFile.ZERO);
            } else {
                userFile.setScore(userFile.getScore() + score);
                UserInfo applier = userInfoService.get(userFile.getApplier().getId());
                applier.setAuthScore(applier.getAuthScore() + score);
                userInfoService.update(applier);
            }
            userFile.setState(state);
            userFileMapper.updateByPrimaryKey(userFile);
        }
    }

    @Override
    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return this.userFileMapper.query(qo);
    }
}
