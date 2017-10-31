package com.xjn.web.dao;

import com.xjn.web.domain.Info;
import com.xjn.web.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfoDao {
    //查询所有投票人信息
    public List<Info> findAllInfo() throws SQLException{
        List<Info> listInfo = new ArrayList<Info>();
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select ip,max(votetime) as votetime from info group by ip";
        listInfo = (List<Info>)runner.query(sql,new BeanListHandler(Info.class));
        return listInfo;
    }
    //根据id找到最后投票时间
    public Info findInfoByIp(String ip) throws SQLException {
        Info info = null;
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select ip,max(votetime) as votetime from info where ip=? group by ip";
        info = (Info)runner.query(sql,new BeanHandler(Info.class),ip);
        return info;
    }
    //添加信息
    public void addInfo(Info info) throws SQLException{
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "insert into info(ip) values(?)";
        runner.update(sql,info.getIp());
    }
}
