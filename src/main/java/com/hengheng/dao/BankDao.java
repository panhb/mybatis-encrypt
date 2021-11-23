package com.hengheng.dao;

import com.hengheng.entity.Bank;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Bank)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-23 10:47:46
 */
public interface BankDao {

    /**
     * 通过ID查询单条数据
     *
     * @param pkid 主键
     * @return 实例对象
     */
    Bank queryById(Long pkid);

    /**
     * 查询指定行数据
     *
     * @param bank 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Bank> queryAllByLimit(Bank bank, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param bank 查询条件
     * @return 总行数
     */
    long count(Bank bank);

    /**
     * 新增数据
     *
     * @param bank 实例对象
     * @return 影响行数
     */
    int insert(Bank bank);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Bank> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Bank> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Bank> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Bank> entities);

    /**
     * 修改数据
     *
     * @param bank 实例对象
     * @return 影响行数
     */
    int update(Bank bank);

    /**
     * 通过主键删除数据
     *
     * @param pkid 主键
     * @return 影响行数
     */
    int deleteById(Long pkid);

}

