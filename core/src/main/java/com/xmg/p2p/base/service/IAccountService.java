package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Account;

/**
 * 账户相关服务
 * Created by Panda on 2016/10/28.
 */
public interface IAccountService {

    /**
     * 处理乐观锁的问题
     * @param account
     */
    void update(Account account);

    /**
     * 保存
     * @param account
     */
    void save(Account account);

    Account get(Long id);

    Account getCurrent();

    void doTest();
}
