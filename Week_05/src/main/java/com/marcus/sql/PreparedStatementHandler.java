package com.marcus.sql;

import org.springframework.util.Assert;

import java.sql.*;

public class PreparedStatementHandler {

    private final String sql;
    private final Object[] params;

    public PreparedStatementHandler(String sql, Object... params) {
        Assert.notNull(sql, "SQL must not be null");
        this.sql = sql;
        this.params = params;
    }

    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(this.sql);
        this.fillStatement(ps);
        return ps;
    }

    public String getSql() {
        return this.sql;
    }

    public void fillStatement(PreparedStatement stmt) throws SQLException {
        ParameterMetaData pmd = stmt.getParameterMetaData();
        int stmtCount = pmd.getParameterCount();
        int paramsCount = params == null ? 0 : params.length;

        if (stmtCount != paramsCount) {
            throw new SQLException("Wrong number of parameters: expected "
                    + stmtCount + ", was given " + paramsCount);
        }

        // nothing to do here
        if (params == null) {
            return;
        }

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                stmt.setObject(i + 1, params[i]);
            } else {
                int sqlType = Types.VARCHAR;
                stmt.setNull(i + 1, sqlType);
            }
        }
    }
}
