package com.marcus.configs.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class XmlApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Student st1 = context.getBean("st1", Student.class);
        System.out.println(st1.toString());

        School school = context.getBean("school",School.class);
        System.out.println(school);
    }
}
