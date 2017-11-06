package cn.xjn.java.service;

import cn.xjn.java.dao.*;
import cn.xjn.java.domain.*;
import cn.xjn.java.util.Encrypted;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BbsService {
    private TypeDao td = new TypeDao();
    private AdminDao ad = new AdminDao();
    private UserDao ud = new UserDao();
    private AddressDao adds = new AddressDao();
    private FlowDao fd = new FlowDao();
    private TopicDao topd = new TopicDao();
    private ReplyDao rd = new ReplyDao();
    public List<Type> findAllType() throws Exception {
        try {
            List<Type> typelist = td.findAllType();
            for(Type type : typelist){
                Admin admin = ad.findAdminByTitle(type.getTitle());
                type.setAdmin(admin);
            }
            return typelist;
        } catch (SQLException e) {
            throw new Exception();
        }
    }
    public void register(User user) throws Exception{
        try {
            String passwordMD5 = Encrypted.encodeByMd5(user.getPassword());
            user.setPassword(passwordMD5);
            ud.register(user);
        } catch (Exception e) {
            throw new Exception();
        }
    }
    public boolean checkOnline(User user,List<String> usernameList) {
        boolean flag = false;
        if (usernameList != null && usernameList.size() == 0) {
            flag = false;
            //当前还没有用户
            usernameList.add(user.getUsername());
        } else {
            if (usernameList.contains(user.getUsername())) {
                //用户列表中存在此用户
                flag = true;
            } else {
                flag = false;
                usernameList.add(user.getUsername());
            }
        }
        return flag;
    }

    public User login(User user) throws Exception{
        try {
            //对密码进行MD5编码
            String passwordMD5 = Encrypted.encodeByMd5(user.getPassword());
            user.setPassword(passwordMD5);
            return ud.login(user);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public Address findAddressByIP(String ip) throws Exception{
        try {
            return adds.findAddressByIP(ip);
        } catch (SQLException e) {
            throw new Exception();
        }
    }

    //根据日期查询流量
    public Flow findFlowByDate(Date date) throws Exception{
        try {
            return fd.findFlowByDate(date);
        } catch (SQLException e) {
            throw new Exception();
        }
    }

    //根据版块查询主题
    public List<Topic> findTopicByType(int typeId) throws Exception{
        try {
            List<Topic> topicList =  topd.findTopicByType(typeId);
            for(Topic topic : topicList){
                int replyNum = rd.getReplyNumByTopic(topic.getId());
                topic.setReplyNum(replyNum);
            }
            return topicList;
        } catch (SQLException e) {
            throw new Exception();
        }
    }

    //根据主题查询所有回复
    public List<Reply> findReplyByTopic(int topicId) throws Exception{
        try {
            return rd.findReplyByTopic(topicId);
        } catch (SQLException e) {
            throw new Exception();
        }
    }

    //根据版块更新点击数
    public void updateClickByType(int typeId) throws Exception{
        try {
            td.updateClickByType(typeId);
        } catch (SQLException e) {
            throw new Exception();
        }
    }

    //判段该用户是否点击过false表示没有点击过，true表示点击过
    public boolean isClicked(String typeId, List<String> typeIdList) {
        boolean flag = false;
        if(typeIdList!=null && typeIdList.size()==0){
            flag = false;
            typeIdList.add(typeId);
        }else{
            //以前点击过
            if(typeIdList.contains(typeId)){
                flag = true;
            }else{
                //以前没有点击过
                flag = false;
                typeIdList.add(typeId);
            }
        }
        return flag;
    }
}
