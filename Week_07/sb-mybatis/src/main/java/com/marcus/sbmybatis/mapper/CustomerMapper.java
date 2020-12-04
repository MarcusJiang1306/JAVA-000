package com.marcus.sbmybatis.mapper;

import com.marcus.sbmybatis.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("select * from customer")
    List<Customer> findAll();

}
