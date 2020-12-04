package com.marcus.sql.dao.impl;

import com.marcus.sql.MyJdbcTemplate;
import com.marcus.sql.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Component
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private MyJdbcTemplate myJdbcTemplate;

    public void moneyOperation(double money, String id) {
        try {
            myJdbcTemplate.update("update user set money = ? where id =? ", money, id);
            int i = 1 / 0;
            myJdbcTemplate.update("update user set money = ? where id =? ", money + 300, "440111198905013225");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("sql error");
        }
    }
}
