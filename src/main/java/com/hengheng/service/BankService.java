package com.hengheng.service;

import com.hengheng.entity.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Bank)表服务接口
 *
 * @author makejava
 * @since 2021-11-23 10:47:49
 */
public interface BankService {

    /**
     * 通过ID查询单条数据
     *
     * @param pkid 主键
     * @return 实例对象
     */
    Bank queryById(Long pkid);

    /**
     * 分页查询
     *
     * @param bank 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Bank> queryByPage(Bank bank, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param bank 实例对象
     * @return 实例对象
     */
    Bank insert(Bank bank);

    /**
     * 修改数据
     *
     * @param bank 实例对象
     * @return 实例对象
     */
    Bank update(Bank bank);

    /**
     * 通过主键删除数据
     *
     * @param pkid 主键
     * @return 是否成功
     */
    boolean deleteById(Long pkid);

}
