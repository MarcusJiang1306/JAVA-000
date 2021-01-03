package com.marcus.redis.demo.controller;

import com.marcus.redis.demo.service.DemoService;
import com.marcus.redis.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: MarcusJiang1306
 */
@RestController
public class HelloController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("hello")
    public String hello() {
        return demoService.doSomething();
    }

    @GetMapping("count")
    public String count() {
        String result;
        if (goodsService.decrGoods("pid_1")) {
            result = "购买成功";
        } else {
            result = "商品数量不足，购买失败";
        }
        return result;
    }
}
