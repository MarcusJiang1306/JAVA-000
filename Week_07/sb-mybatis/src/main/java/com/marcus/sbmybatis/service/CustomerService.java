package com.marcus.sbmybatis.service;

import com.marcus.sbmybatis.datasource.CurDataSource;
import com.marcus.sbmybatis.entity.Customer;
import com.marcus.sbmybatis.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> getAllCustomerFromPrimary() {
        return customerMapper.findAll();
    }

    @CurDataSource
    public List<Customer> getAllCustomerFromSecondary() {
        return customerMapper.findAll();
    }
}
