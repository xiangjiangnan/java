package cn.xjn.java.dao;

import cn.xjn.java.domain.Type;
import cn.xjn.java.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang
 */
public class TypeDao {
    public List<Type> findAllType() throws SQLException {
        List<Type> typeList = new ArrayList<Type>();
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select * from type";
        typeList = (List<Type>) runner.query(sql, new BeanListHandler(Type.class));
        return typeList;
    }

    //根据版块更新点击数
    public void updateClickByType(int typeId) throws SQLException{
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "update type set click = click + 1 where id = ?";
        runner.update(sql,typeId);
    }
}
