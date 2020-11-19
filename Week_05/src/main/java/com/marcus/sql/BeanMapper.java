package com.marcus.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BeanMapper<T> {

    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
