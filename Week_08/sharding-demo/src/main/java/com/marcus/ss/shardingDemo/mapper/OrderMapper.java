package com.marcus.ss.shardingDemo.mapper;

import com.marcus.ss.shardingDemo.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select * from order_info")
    List<Order> findAll();

    @Select("select * from order_info where order_id = #{order_id}")
    Order selectByID(long order_id);

    @Insert("insert into order_info(order_time,user_id,commodity_id,price,create_time,update_time) values(#{order_time},#{user_id},#{commodity_id},#{price},#{create_time},#{update_time})")
    int insert(Order order);
}
