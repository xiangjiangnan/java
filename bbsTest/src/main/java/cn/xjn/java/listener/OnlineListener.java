package cn.xjn.java.listener;

import cn.xjn.java.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author xiang
 */
public class OnlineListener implements ServletRequestListener,
        HttpSessionAttributeListener {
    public OnlineListener() {
    }
    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        String name = sbe.getName();//name="user"字符串
        Object obj =  sbe.getValue();//value=user对象
        if(obj instanceof User){
            //确认是向HttpSession中绑定是的user对象
            if("user".equals(name)){
                HttpSession session = sbe.getSession();
                ServletContext context = session.getServletContext();
                Integer online = (Integer) context.getAttribute("online");
                if(online==null){
                    online = 1;
                }else{
                    online ++;
                }
                //在线人数
                context.setAttribute("online", online);
                Integer caller = (Integer) context.getAttribute("caller");
                if(caller==null){
                    caller = 1;
                }else{
                    caller ++;
                }
                //访问次序
                context.setAttribute("caller",caller);//可变
                session.setAttribute("caller",caller);//不变
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        String name = sbe.getName();//name="user"字符串
        //确实是向HttpSession中绑定是的user对象
        if("user".equals(name)){
            HttpSession session = sbe.getSession();
            ServletContext context = session.getServletContext();
            Integer online = (Integer) context.getAttribute("online");
            online --;
            context.setAttribute("online", online);
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession();
        if(session.isNew()){
            ServletContext context = sre.getServletContext();
            Integer todayFlow = (Integer) context.getAttribute("todayFlow");
            if(todayFlow==null){
                todayFlow = 1;
            }else{
                todayFlow ++;
            }
            //将今日流量绑定到ServletContext对象中
            context.setAttribute("todayFlow",todayFlow);
        }else{

        }
    }
}
