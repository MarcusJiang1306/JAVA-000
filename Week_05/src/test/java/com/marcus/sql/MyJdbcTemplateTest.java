package com.marcus.sql;

import com.marcus.sql.dao.impl.UserDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context.xml"})
public class MyJdbcTemplateTest {

    @Autowired
    private MyJdbcTemplate myJdbcTemplate;

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Test
    public void queryTest() throws SQLException {
        List<User> users = myJdbcTemplate.query("select * from user where name =?", (rs, rowNum) -> {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setId(rs.getString("id"));
            user.setCity(rs.getString("city"));
            user.setAge(rs.getInt("age"));
            user.setMoney(rs.getDouble("money"));
            return user;
        }, "zhangsan");
        users.forEach(System.out::println);
    }

    @Test
    public void transferTest() {
        userDaoImpl.moneyOperation(500, "440114199909090009");

    }
}
