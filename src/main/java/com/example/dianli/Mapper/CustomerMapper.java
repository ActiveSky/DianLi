package com.example.dianli.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dianli.Entity.Customer;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author JiKuiXie
 * @Date 2023/05/29
 */

@Repository
@Mapper
public interface CustomerMapper extends MPJBaseMapper<Customer> {
}
