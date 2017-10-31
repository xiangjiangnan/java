package com.xjn.web.servlet;

import com.xjn.web.domain.Content;
import com.xjn.web.domain.Info;
import com.xjn.web.domain.Vote;
import com.xjn.web.exception.CannotVoteException;
import com.xjn.web.exception.LimitVoteException;
import com.xjn.web.service.VoteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

//@WebServlet(name = "VoteServlet")
public class VoteServlet extends HttpServlet {
    private Object lock = new Object();
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        //System.out.println("hello1"+method);
        if("findAllVotes".equals(method)){
            this.findAllVotes(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String method = request.getParameter("method");
        //System.out.println("hello2"+method);
        if("findAllVotes".equals(method)){
            this.findAllVotes(request, response);
        }else if("findVoteById".equals(method)){
            this.findVoteById(request, response);
        }else if("updateVoteById".equals(method)){
            this.updateVoteById(request, response);
        }else if("toLogin".equals(method)){
            this.toLogin(request, response);
        }else if("exit".equals(method)){
            this.exit(request, response);
        }else if("findAllInfo".equals(method)){
            this.findAllInfo(request, response);
        }
    }
    public void findAllVotes(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException{
        try {
            VoteService voteService = new VoteService();
            List<Vote> votelist = voteService.findAllVotes();
            //将候选人信息显示在showlist.jsp中
            request.setAttribute("votelist",votelist);
            request.getRequestDispatcher("/jsp/showlist.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //查找出错时
            request.setAttribute("message","无法获取所有候选人信息");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request,response);
        }
    }
    public void findVoteById(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            VoteService voteService = new VoteService();
            Content content = voteService.findContentById(Integer.parseInt(id));
            request.setAttribute("content", content);
            request.getRequestDispatcher("/jsp/showcontent.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "无法获取候选人详细信息");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request, response);
        }
    }
    public void updateVoteById(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        VoteService voteService = new VoteService();
        try {
            String ip = request.getRemoteAddr();
            //为了解决线程安全问题（多个线程并发修改同一个变量），此处要加锁，锁的对象是一个难点
            synchronized (lock) {
                voteService.updateVoteById(Integer.parseInt(id),ip);
            }
            response.sendRedirect(request.getContextPath()+"/welcome.jsp");
        }catch(LimitVoteException e){
            e.printStackTrace();
            request.setAttribute("message","投票数限制在100以内");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request,response);
        }catch(CannotVoteException e){
            e.printStackTrace();
            request.setAttribute("message","一分钟内不允许投票");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request,response);
        }catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("message","投票失败");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request,response);
        }
    }
    public void toLogin(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/jsp/login.jsp")
                .forward(request, response);
    }
    public void exit(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException{
        try {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath()+"/welcome.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message","存在安全隐患");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request,response);
        }
    }
    public void findAllInfo(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException{
        VoteService voteService = new VoteService();
        try {
            List<Info> listInfo = voteService.findAllInfo();
            request.setAttribute("infolist",listInfo);
            request.getRequestDispatcher("/jsp/infolist.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message","查询投票人信息");
            request.getRequestDispatcher("/jsp/message.jsp")
                    .forward(request,response);
        }
    }
}
