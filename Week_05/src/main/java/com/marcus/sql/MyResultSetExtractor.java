package com.marcus.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MyResultSetExtractor<T> {

    T extractData(ResultSet rs) throws SQLException;

}
