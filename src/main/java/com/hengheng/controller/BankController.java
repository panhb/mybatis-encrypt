package com.hengheng.controller;

import com.hengheng.entity.Bank;
import com.hengheng.service.BankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Bank)表控制层
 *
 * @author makejava
 * @since 2021-11-23 10:47:43
 */
@RestController
@RequestMapping("bank")
public class BankController {
    /**
     * 服务对象
     */
    @Resource
    private BankService bankService;

    /**
     * 分页查询
     *
     * @param bank 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Bank>> queryByPage(Bank bank, PageRequest pageRequest) {
        return ResponseEntity.ok(this.bankService.queryByPage(bank, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Bank> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.bankService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param bank 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Bank> add(@RequestBody Bank bank) {
        return ResponseEntity.ok(this.bankService.insert(bank));
    }

    /**
     * 编辑数据
     *
     * @param bank 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Bank> edit(@RequestBody Bank bank) {
        return ResponseEntity.ok(this.bankService.update(bank));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.bankService.deleteById(id));
    }

}

