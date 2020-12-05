package com.marcus.ss.shardingspheredemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Lazy
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("all1")
    public List<Customer> getAllCustomer1() {
        return customerMapper.findAll();
    }

    @GetMapping("all2")
    public List<Customer> getAllCustomer2() {
        return customerMapper.findAll();
    }
}
