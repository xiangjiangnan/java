package com.test.spring.controller;

import com.test.spring.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author xiang
 */
//@Controller
public class IndexController {
    /**
     * RequestMapping用于获取浏览器HTTP请求
     * ResponseBody用于服务器响应浏览器请求
     * 指定两个不同的访问路径
     * Autowired实现了控制反转
     */
    @Autowired
    private IndexService is;
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession session) {
        return "Hello spring, "+session.getAttribute("msg")+is.sayHi();
    }

    /**
     * 获取路径参数以及请求参数
     */
    @RequestMapping(path = "/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "hello") String key) {
        return String.format("{%s},{%s},{%d},{%s}", groupId, userId, type, key);
    }

    /**
     * 使用模板，指定到具体的页面
     */
    @RequestMapping(value = {"/vm"})
    public String news() {
        return "news";
    }

    /**
     * String用于操作少量数据
     * StringBuilder用于单线程下大量数据操作（非线程安全）
     * StringBuffer用于多线程下大量数据操作（线程安全）
     * 获取请求头相关参数
     */
    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse response,
                          HttpSession httpSession) {
        response.setHeader("content-type", "text/plain;charset=utf-8");
        StringBuilder sb = new StringBuilder();
        Enumeration<String> em = request.getHeaderNames();
        while (em.hasMoreElements()) {
            String name = em.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        for (Cookie c : request.getCookies()) {
            sb.append("Cookie:");
            sb.append(c.getName());
            sb.append(":");
            sb.append(c.getValue());
            sb.append(":");
            sb.append(c.getVersion());
            sb.append("<br>");
        }
        sb.append("getMethod:" + request.getMethod() + "<br>");
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");
        sb.append("getQueryString:" + request.getQueryString() + "<br>");
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");
        sb.append("getContentType:" + request.getContentType() + "<br>");
        return sb.toString();
    }

    @RequestMapping(value = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "userId", defaultValue = "springUserId") String userId,
                           @RequestParam(value = "name", defaultValue = "xiang") String name,
                           @RequestParam(value = "password", defaultValue = "123456") String password,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(name, password));
        response.addHeader(name, password);
        return "userId is:"+name;
    }

    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session) {
        session.setAttribute("msg", "Jump from redirect.");
        return "redirect:/";
    }

    /**
     *spring异常处理
     */
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage();
    }
}