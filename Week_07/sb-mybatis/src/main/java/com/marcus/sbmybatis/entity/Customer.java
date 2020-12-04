package com.marcus.sbmybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    private long customer_id;

    private String mobile;

    private String customer_name;

    private String password;

    private String email;

    private String address;

    private long create_time;

    private long update_time;
}
