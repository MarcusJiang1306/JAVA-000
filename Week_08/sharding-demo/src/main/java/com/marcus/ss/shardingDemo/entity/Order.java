package com.marcus.ss.shardingDemo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private long order_id;

    private long order_time;

    private long user_id;

    private long commodity_id;

    private long price;

    private long create_time;

    private long update_time;
}
