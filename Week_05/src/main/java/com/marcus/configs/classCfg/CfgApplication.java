package com.marcus.configs.classCfg;

import com.marcus.configs.classCfg.entity.ValueStudent;
import com.marcus.configs.xml.Klass;
import com.marcus.configs.xml.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CfgApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ValueStudent student = context.getBean("studentCfg1", ValueStudent.class);
        System.out.println(student.toString());
        Student student1 = context.getBean("student1", Student.class);
        System.out.println(student1.toString());
        Klass cfgClass = context.getBean("cfgClass", Klass.class);
        System.out.println(cfgClass.toString());
    }
}
