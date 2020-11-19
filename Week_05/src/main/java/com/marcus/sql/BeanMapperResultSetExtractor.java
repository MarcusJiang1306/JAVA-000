package com.marcus.sql;


import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanMapperResultSetExtractor<T> implements MyResultSetExtractor<List<T>> {
    private final BeanMapper<T> rowMapper;

    private final int rowsExpected;

    public BeanMapperResultSetExtractor(BeanMapper<T> rowMapper) {
        this(rowMapper, 0);
    }

    public BeanMapperResultSetExtractor(BeanMapper<T> rowMapper, int rowsExpected) {
        Assert.notNull(rowMapper, "RowMapper is required");
        this.rowMapper = rowMapper;
        this.rowsExpected = rowsExpected;
    }


    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = (this.rowsExpected > 0 ? new ArrayList<T>(this.rowsExpected) : new ArrayList<T>());
        int rowNum = 0;
        while (rs.next()) {
            results.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }
}
