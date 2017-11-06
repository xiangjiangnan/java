package cn.xjn.java.dao;

import cn.xjn.java.domain.User;
import cn.xjn.java.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    //注册用户
    public void register(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "insert into user(username,password,gender,email) values(?,?,?,?)";
        runner.update(sql,new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getGender(),
                user.getEmail()
        });
    }

    //用户登录
    public User login(User user) throws SQLException{
        User u = null;
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select * from user where username = ? and password = ?";
        u = (User) runner.query(sql, new BeanHandler(User.class), user.getUsername(), user.getPassword());
        return u;
    }
}
