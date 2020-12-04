package com.marcus.sbmybatis.controller;

import com.marcus.sbmybatis.entity.Customer;
import com.marcus.sbmybatis.mapper.CustomerMapper;
import com.marcus.sbmybatis.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("all1")
    public List<Customer> getAllCustomer1() {
        return customerService.getAllCustomerFromPrimary();
    }

    @GetMapping("all2")
    public List<Customer> getAllCustomer2() {
        return customerService.getAllCustomerFromSecondary();
    }
}
