package com.marcus.ss.shardingspheredemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
@Lazy
public interface CustomerMapper {

    @Select("select * from customer")
    List<Customer> findAll();

}
