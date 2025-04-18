package com.prk.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcQueryTemplate<T> extends AbstractDao {
    public JdbcQueryTemplate() {}

    public List<T> queryForList(String sql) {
        List<T> items = new ArrayList<>();
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(sql))
        {
            while(rset.next()) {
                items.add(mapItem(rset));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public abstract T mapItem(ResultSet rset) throws SQLException;
}
