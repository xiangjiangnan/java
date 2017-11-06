package cn.xjn.java.dao;

import cn.xjn.java.domain.Reply;
import cn.xjn.java.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang
 */
public class ReplyDao {
    //根据主题查询所有回复
    public List<Reply> findReplyByTopic(int topicId) throws SQLException {
        List<Reply> replyList = new ArrayList<Reply>();
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select * from reply where topic_id = ?";
        replyList = (List<Reply>) runner.query(sql, new BeanListHandler(Reply.class), topicId);
        return replyList;
    }
    //根据主题取得回复数
    public int getReplyNumByTopic(int topicId) throws SQLException{
        int replyNum = 0;
        QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "select count(*) from reply where topic_id = ?";
        Object[] array = (Object[]) runner.query(sql, new ArrayHandler(), topicId);
        Long temp = (Long) array[0];
        replyNum = temp.intValue();
        return replyNum;
    }
}





