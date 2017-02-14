package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Panda on 2016/10/28.
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    public void update(Account account) {
        int ret = accountMapper.updateByPrimaryKey(account);
        if (ret <= 0) {
            throw new RuntimeException("乐观锁失败,Account" + account.getId());
        }
    }

    public void save(Account account) {
        accountMapper.insert(account);
    }

    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public Account getCurrent() {
        return get(UserContext.getCurrent().getId());
    }

    public void doTest() {
        System.out.println("定时器ok");
    }
}
