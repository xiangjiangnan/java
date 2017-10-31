package com.xjn.web.service;

import com.xjn.web.dao.ContentDao;
import com.xjn.web.dao.InfoDao;
import com.xjn.web.dao.VoteDao;
import com.xjn.web.domain.Content;
import com.xjn.web.domain.Info;
import com.xjn.web.domain.Vote;
import com.xjn.web.exception.CannotVoteException;
import com.xjn.web.exception.LimitVoteException;

import java.sql.SQLException;
import java.util.List;

public class VoteService {
    private VoteDao votedao = new VoteDao();
    private InfoDao infoDao = new InfoDao();
    private ContentDao contentdao = new ContentDao();
    //查询所有候选人
    public List<Vote> findAllVotes() throws Exception{
        try {
            return votedao.findAllVotes();
        } catch (Exception e) {
            throw new Exception();
        }
    }
    public Content findContentById(int id) throws Exception{
        try {
            Content content = contentdao.findContentById(id);
            //手动封装vote的信息
            Vote vote = votedao.findVoteById(id);
            content.setVote(vote);
            return content;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
    //根据id更新票数
    public void updateVoteById(int id,String ip) throws Exception{
        try {
            //根据ip查找投票信息
            Info info = infoDao.findInfoByIp(ip);
            //第1次投票时
            if(info==null){
                votedao.updateVoteById(id);
                info = new Info();
                info.setIp(ip);
                infoDao.addInfo(info);
            }
            //获取最后投票时间
            else{
                long end = info.getVotetime().getTime();
                long middle = (System.currentTimeMillis()-end)/1000;
                //如果两次投票时间间隔大于1分钟
                if(middle>60){
                    Vote vote = votedao.findVoteById(id);
                    //总票数小于100时
                    if(vote.getTicket()<100){
                        votedao.updateVoteById(id);
                        info.setIp(ip);
                        infoDao.addInfo(info);
                    }else{
                        throw new LimitVoteException();
                    }
                }else{
                    throw new CannotVoteException();
                }
            }
        }catch (LimitVoteException e) {
            e.printStackTrace();
            //此处要抛出一个子异常，否则前面抛出的异常会被捕获
            throw e;
        }catch (CannotVoteException e) {
            e.printStackTrace();
            //此处要抛出一个子异常，否则前面抛出的异常会被捕获
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
    public List<Info> findAllInfo() throws Exception{
        try {
            return infoDao.findAllInfo();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
