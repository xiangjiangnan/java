package com.xjn.web.dao;
import com.xjn.web.domain.Vote;
import com.xjn.web.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
public class VoteDao {
    //查询所有候选人信息
    public List<Vote> findAllVotes() throws SQLException {
        List<Vote> votelist = new ArrayList<Vote>();
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql ="select * from vote";
        votelist = (List<Vote>) runner.query(sql,new BeanListHandler(Vote.class));
        return votelist;
    }
    //根据id更新票数
    public void updateVoteById(int id) throws SQLException{
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "update vote set ticket = ticket+1 where id=?";
        runner.update(sql,id);
    }
    //根据id查询候选人信息
    public Vote findVoteById(int id) throws SQLException{
        Vote vote = null;
        QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from vote where id=?";
        //use variable params
        vote = (Vote)runner.query(sql,new BeanHandler(Vote.class),id);
        return vote;
    }
}
