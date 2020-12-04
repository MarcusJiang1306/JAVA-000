package com.marcus.sbmybatis.service;

import com.marcus.sbmybatis.datasource.CurDataSource;
import com.marcus.sbmybatis.entity.Customer;
import com.marcus.sbmybatis.mapper.CustomerMapper;
import com.marcus.sbmybatis.util.DatasourceConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
