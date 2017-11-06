package cn.xjn.java.dao;

import cn.xjn.java.domain.Flow;
import cn.xjn.java.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.Date;

public class FlowDao {
    //根据日期查询流量
    public Flow findFlowByDate(Date date) throws SQLException {
        Flow flow = null;
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select * from flow where historydate = ?";
        flow = (Flow) runner.query(sql, new BeanHandler(Flow.class), new java.sql.Date(date.getTime()));
        return flow;
    }
}
