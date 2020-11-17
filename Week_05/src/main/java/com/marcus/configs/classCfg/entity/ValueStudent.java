package com.marcus.configs.classCfg.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Data
@NoArgsConstructor
@ToString
@Component("studentCfg1")
public class ValueStudent implements Serializable {

    @Value("1")
    private int id;

    @Value("Value name")
    private String name;

    public ValueStudent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void init() {
        System.out.println("hello...........");
    }

    public ValueStudent create() {
        return new ValueStudent(101, "KK101");
    }
}
