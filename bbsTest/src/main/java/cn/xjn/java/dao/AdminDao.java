package cn.xjn.java.dao;

import cn.xjn.java.domain.Admin;
import cn.xjn.java.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AdminDao {
    //根据版块名称查询版主
    public Admin findAdminByTitle(String title) throws SQLException {
        Admin admin = null;
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select * from admin where title = ?";
        admin = (Admin) runner.query(sql, new BeanHandler(Admin.class), title);
        return admin;
    }
}
