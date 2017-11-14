package com.test.spring.service;

import com.test.spring.dao.LoginTicketDAO;
import com.test.spring.dao.UserDAO;
import com.test.spring.model.LoginTicket;
import com.test.spring.model.User;
import com.test.spring.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.*;

/**
 * @author xiang
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public void setUser(User user){
        userDAO.addUser(user);
    }
    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = checkInfo(username, password, map);
        if(flag==false){
            return map;
        }
        User user = userDAO.selectByName(username);

        if (user != null) {
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        // 密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(Md5Util.encodeByMd5(password+user.getSalt()));
        userDAO.addUser(user);

        // 登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    private String  addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        //一天
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public Map<String, Object> login(String username, String password) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = checkInfo(username, password, map);
        if(flag==false){
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msgname", "用户名不存在");
            return map;
        }
        if (!Md5Util.encodeByMd5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public boolean checkInfo(String username, String password, Map<String, Object> map){
        boolean flag = false;
        if (username!= null&&username.trim().length()>0) {
            String usernameMatch = "[a-zA-Z0-9_\u4E00-\uFA29]+";
            if (username.matches(usernameMatch)) {
                flag = true;
            } else {
                map.put("username", "用户名含有非法字符");
                flag = false;
            }
        }else{
            map.put("username", "用户名不能为空");
            flag = false;
        }
        if (password!= null&&password.trim().length()>0) {
            String passwordMatch = "[a-zA-Z0-9]";
            if(password.matches("paswordMatch")){
                flag = true;
            }else{
                map.put("password", "密码含有非法字符");
                flag = false;
            }
        }else{
            map.put("msgpwd", "密码不能为空");
            flag = false;
        }
        return flag;
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}
