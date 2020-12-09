package com.marcus.ss.shardingDemo.controller;

import com.marcus.ss.shardingDemo.entity.Order;
import com.marcus.ss.shardingDemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrder();
    }

    @GetMapping("getOrder")
    public Order getOrdersById(@RequestParam long id) {
        return orderService.findById(id);
    }

    @GetMapping("order")
    public int createOrder() {
        int orderId = (int) (Math.random() * 1024);
        int userId = (int) (Math.random() * 32);

        long timeMillis = System.currentTimeMillis();
        return orderService.insert(new Order(orderId, timeMillis, userId, 1, 39, timeMillis, timeMillis));
    }
}
