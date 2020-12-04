package com.marcus.sbmybatis.cfg;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.marcus.sbmybatis.datasource.DynamicDataSource;
import com.marcus.sbmybatis.util.DatasourceConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties("spring.datasource.druid.secondary")
    public DataSource secondaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource primaryDataSource, DataSource secondaryDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put(DatasourceConst.primary.name(), primaryDataSource);
        targetDataSources.put(DatasourceConst.secondary.name(), secondaryDataSource);
        return new DynamicDataSource(primaryDataSource, targetDataSources);
    }

}
