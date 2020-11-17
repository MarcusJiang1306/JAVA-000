package com.marcus.configs.xml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
public class School implements ISchool {
    
    // Resource 
    @Autowired(required = true) //primary
    Klass class1;

    @Resource(name = "st2")
//    @Autowired
//    @Qualifier("st2")
    Student student100;

    @Override
    public void ding(){
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
    }
    
}
