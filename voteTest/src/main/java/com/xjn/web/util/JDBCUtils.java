package com.xjn.web.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
    private static ComboPooledDataSource ds;
    static{
        ds = new ComboPooledDataSource();
    }
    public static ComboPooledDataSource getDataSource(){
        return ds;
    }
    public static Connection getConnection() throws SQLException {
        Connection conn = ds.getConnection();
        return conn;
    }
    public static void close(Connection conn) throws SQLException{
        if(conn!=null)
            conn.close();
    }
    public static void close(ResultSet rs) throws SQLException{
        if(rs!=null)
            rs.close();
    }
    public static void close(PreparedStatement pstmt) throws SQLException{
        if(pstmt!=null)
            pstmt.close();
    }
}
