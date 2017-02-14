package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;

import java.util.List;

/**
 * Created by Panda on 2016/11/3.
 */
public interface IUserFileService {

    PageResult query(UserFileQueryObject qo);

    List<UserFile> getSelectList(Long id , boolean select);

    void apply(String fileName);

    void saveFileType(Long[] id, Long[] fileType);

    void audit(Long id, String remark, int score, int state);

    List<UserFile> queryForList(UserFileQueryObject qo);
}
