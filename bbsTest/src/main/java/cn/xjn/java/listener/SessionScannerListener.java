package cn.xjn.java.listener;

import cn.xjn.java.domain.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.*;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiang
 */
public class SessionScannerListener implements ServletContextListener,
        HttpSessionListener {
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    private List<HttpSession> sessionList = new ArrayList<HttpSession>();
    public SessionScannerListener() {
        sessionList = Collections.synchronizedList(sessionList);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MyTimerTask task = new MyTimerTask(sessionList);
        //执行一个1s定时任务
        executorService.scheduleAtFixedRate(task,0,1, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //取得该用户的HttpSession，每当有新的session，就添加到集合中
        HttpSession session = se.getSession();
        synchronized (sessionList) {
            sessionList.add(session);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}

 class MyTimerTask implements Runnable{
    private List<HttpSession> sessionList;
    public MyTimerTask(List<HttpSession> sessionList) {
        this.sessionList = sessionList;
    }
    @Override
    public void run() {
        //当有HttpSession存在时，再迭代
        if(sessionList.size()>0){
            ListIterator<HttpSession> it = sessionList.listIterator();
            synchronized (sessionList) {
                while (it.hasNext()) {
                    HttpSession session = it.next();
                    User user = (User) session.getAttribute("user");
                    //用户未按"安全退出"
                    if(user!=null){
                        //获取session与服务器上一次交互的时间间隔，10秒没交互就认为不在线，将session从集合删除，用户也从用户集合删除
                        int mid = (int) ((System.currentTimeMillis()-session.getLastAccessedTime())/1000);
                        if(mid > 10){
                            //将该HttpSession从集合中删除
                            it.remove();
                            //取得在线用户列表
                            List<String> usernameList = (List<String>) session.getServletContext().getAttribute("usernameList");
                            //取得用户名
                            String username = user.getUsername();
                            //从列表中删除该用户名
                            usernameList.remove(username);
                            //销毁当前用户的HttpSession
                            session.removeAttribute("user");
                        }
                    }else{
                        //用户已按"安全退出"
                    }
                }
            }
        }
    }
}


