package com.marcus.ss.shardingDemo.service;

import com.marcus.ss.shardingDemo.entity.Order;
import com.marcus.ss.shardingDemo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getAllOrder() {
        return orderMapper.findAll();
    }

    public Order findById(long id) {
        return orderMapper.selectByID(id);
    }

    public int insert(Order order) {
        return orderMapper.insert(order);
    }

}