package com.marcus.configs.classCfg;

import com.marcus.configs.classCfg.entity.ValueStudent;
import com.marcus.configs.xml.Klass;
import com.marcus.configs.xml.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Bean
    public Student student1() {
        return new Student(88, "zhangsan");
    }

    @Bean("cfgClass")
    public Klass klass(Student student1) {
        Klass klass = new Klass();
        ArrayList<Student> students = new ArrayList<>();
        students.add(student1);
        klass.setStudents(students);
        return klass;
    }
}
