package com.hengheng.service.impl;

import com.hengheng.entity.Bank;
import com.hengheng.dao.BankDao;
import com.hengheng.service.BankService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Bank)表服务实现类
 *
 * @author makejava
 * @since 2021-11-23 10:47:50
 */
@Service("bankService")
public class BankServiceImpl implements BankService {
    @Resource
    private BankDao bankDao;

    /**
     * 通过ID查询单条数据
     *
     * @param pkid 主键
     * @return 实例对象
     */
    @Override
    public Bank queryById(Long pkid) {
        return this.bankDao.queryById(pkid);
    }

    /**
     * 分页查询
     *
     * @param bank 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Bank> queryByPage(Bank bank, PageRequest pageRequest) {
        long total = this.bankDao.count(bank);
        return new PageImpl<>(this.bankDao.queryAllByLimit(bank, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param bank 实例对象
     * @return 实例对象
     */
    @Override
    public Bank insert(Bank bank) {
        this.bankDao.insert(bank);
        return bank;
    }

    /**
     * 修改数据
     *
     * @param bank 实例对象
     * @return 实例对象
     */
    @Override
    public Bank update(Bank bank) {
        this.bankDao.update(bank);
        return this.queryById(bank.getPkid());
    }

    /**
     * 通过主键删除数据
     *
     * @param pkid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long pkid) {
        return this.bankDao.deleteById(pkid) > 0;
    }
}
