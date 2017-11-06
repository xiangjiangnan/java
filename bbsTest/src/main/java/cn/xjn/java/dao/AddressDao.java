package cn.xjn.java.dao;

import cn.xjn.java.domain.Address;
import cn.xjn.java.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AddressDao {
    //根据IP查询归属地
    public Address findAddressByIP(String ip) throws SQLException {
        Address address = null;
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select * from address where ip = ?";
        address = (Address) runner.query(sql, new BeanHandler(Address.class), ip);
        return address;
    }
}
