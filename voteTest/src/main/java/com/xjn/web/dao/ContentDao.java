package com.xjn.web.dao;

import com.xjn.web.domain.Content;
import com.xjn.web.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

//候选人详细信息
public class ContentDao {
    public Content findContentById(int id) throws SQLException {
        Content content = null;
        QueryRunner runner =new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select *from content where id =?";
        //BeanHandler中没有封装有关vote的相关信息，即关联关系DBUtils框架不能自动装配
        content = (Content)runner.query(sql,new BeanHandler(Content.class),id);
        return content;
    }
}
