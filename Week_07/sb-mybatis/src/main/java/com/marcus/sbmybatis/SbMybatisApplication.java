package com.marcus.sbmybatis;

import com.marcus.sbmybatis.cfg.DynamicDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({DynamicDataSourceConfig.class})
public class SbMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbMybatisApplication.class, args);

    }

}
