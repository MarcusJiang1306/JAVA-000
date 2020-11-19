package com.marcus.sql;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MyJdbcTemplate {

    @Autowired
    private DataSource dataSource;

    public MyJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public <T> List<T> query(String sql, BeanMapper<T> beanMapper, Object... params) throws SQLException {
        return query(sql, new BeanMapperResultSetExtractor<>(beanMapper), params);
    }

    public <T> T query(final String sql, final MyResultSetExtractor<T> rse, Object... params) throws SQLException {
        Assert.notNull(sql, "SQL must not be null");
        Assert.notNull(rse, "ResultSetExtractor must not be null");

        return execute(new PreparedStatementHandler(sql, params), ps -> {
            ResultSet resultSet = null;
            try {
                resultSet = ps.executeQuery();
                return rse.extractData(resultSet);
            } finally {
                JdbcUtils.closeResultSet(resultSet);
            }
        });
    }

    public int update(String sql, Object... params) throws SQLException {
        Assert.notNull(sql, "SQL must not be null");
        return execute(new PreparedStatementHandler(sql, params), PreparedStatement::executeUpdate);
    }

    protected <T> T execute(PreparedStatementHandler psh, PreparedStatementCallback<T> action) throws SQLException {
        Assert.notNull(psh, "PreparedStatementHandler object must not be null");
        Assert.notNull(action, "Callback object must not be null");

        Connection con = DataSourceUtils.getConnection(getDataSource());
        PreparedStatement stmt = null;
        try {
            Connection conToUse = con;

            stmt = psh.createPreparedStatement(con);

            T result = action.doInPreparedStatement(stmt);

            return result;
        } catch (SQLException ex) {
            JdbcUtils.closeStatement(stmt);
            stmt = null;
            DataSourceUtils.releaseConnection(con, getDataSource());
            con = null;
            throw ex;
        } finally {
            JdbcUtils.closeStatement(stmt);
            DataSourceUtils.releaseConnection(con, getDataSource());
        }
    }
}
