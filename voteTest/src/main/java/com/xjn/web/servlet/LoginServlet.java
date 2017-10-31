package com.xjn.web.servlet;

import com.xjn.web.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username!=null&&username.trim().length()>0){
            User user = new User();
            user.setPassword(password);
            user.setUsername(username);
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/welcome.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

    }
}
