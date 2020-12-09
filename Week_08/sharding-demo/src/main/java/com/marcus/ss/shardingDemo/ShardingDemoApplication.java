package com.marcus.ss.shardingDemo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;


@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ShardingDemoApplication {



    public static void main(String[] args) {
        SpringApplication.run(ShardingDemoApplication.class, args);
    }

}
