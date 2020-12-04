package com.marcus.sbmybatis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class DatasourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void getDs() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());

    }
}
