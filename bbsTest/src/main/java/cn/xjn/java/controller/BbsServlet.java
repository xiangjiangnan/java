package cn.xjn.java.controller;

import cn.xjn.java.domain.Reply;
import cn.xjn.java.domain.Topic;
import cn.xjn.java.domain.Type;
import cn.xjn.java.domain.User;
import cn.xjn.java.form.LoginForm;
import cn.xjn.java.form.RegisterForm;
import cn.xjn.java.service.BbsService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author xiang
 */
public class BbsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        this.doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String method = request.getParameter("method");
        if("findAllType".equals(method)){
            this.findAllType(request, response);
        }else if("toRegisterJsp".equals(method)){
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        }else if("register".equals(method)){
            this.register(request, response);
        }else if("toLoginJsp".equals(method)){
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }else if("login".equals(method)){
            this.login(request, response);
        }else if("exit".equals(method)){
            this.exit(request, response);
        }else if("findTopicByType".equals(method)){
            this.findTopicByType(request, response);
        }else if("findReplyByTopic".equals(method)){
            this.findReplyByTopic(request, response);
        }
    }

    public void findAllType(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException{
        try {
            BbsService bs = new BbsService();
            List<Type> typeList = bs.findAllType();
//            for(Type type : typeList){
//                System.out.println(type.getId()+":"+type.getTitle()+":"+type.getImage());
//            }
            request.setAttribute("typelist", typeList);
            request.getRequestDispatcher("/WEB-INF/jsp/listAllType.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "查询所有版本失败");
            request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request, response);
        }
    }

    private void register(HttpServletRequest request,
                          HttpServletResponse response)throws ServletException, IOException {
        //获取所有注册参数
        Enumeration<String> enums = request.getParameterNames();
        User user = new User();
        while(enums.hasMoreElements()){
            String key = enums.nextElement();
            String[] values = request.getParameterValues(key);
            try {
                BeanUtils.setProperty(user, key, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //对从表单获取数据验证
        RegisterForm registerForm = new RegisterForm();
        boolean flag = registerForm.validate(user);
        //MD5加密
        if(flag){
            try {
                BbsService bbsService = new BbsService();
                bbsService.register(user);
                request.setAttribute("message","注册成功");
                request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message","注册失败");
                request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
            }
        }else{
            //注册信息有误
            request.setAttribute("registerForm",registerForm);
            request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        Enumeration<String> enums = request.getParameterNames();
        User user = new User();
        while(enums.hasMoreElements()){
            String key = enums.nextElement();
            String[] values = request.getParameterValues(key);
            try {
                BeanUtils.setProperty(user,key,values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //验证用户名密码格式
        LoginForm loginForm = new LoginForm();
        boolean flag = loginForm.validate(user);
        if(flag){
            //获取所有用户名
            List<String> usernameList = (List<String>) this.getServletContext().getAttribute("usernameList");
            if(usernameList==null){
                usernameList = new ArrayList<String>();
                this.getServletContext().setAttribute("usernameList",usernameList);
            }
            BbsService bbsService = new BbsService();
            flag = bbsService.checkOnline(user,usernameList);
            if(flag){
                request.setAttribute("message","<font color='blue' size='44'>当前用户已在线</font>");
                request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
            }else{
                try {
                    bbsService = new BbsService();
                    User u = bbsService.login(user);
                    if(u!=null){
                        //将当前用户放到session域
                        request.getSession().setAttribute("user",user);
                        request.setAttribute("message","登录成功");
                        request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
                    }else{
                        //用户不存在
                        request.setAttribute("message","登陆失败");
                        request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("message","登陆失败");
                    request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
                }
            }
        }else{
            //用户名或密码格式错误
            request.setAttribute("loginForm",loginForm);
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        }
    }

    private void exit(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        List<String> usernameList = (List<String>) this.getServletContext().getAttribute("usernameList");
        User user = (User) request.getSession().getAttribute("user");
        String username = user.getUsername();
        //将session域中用户从上下文中删除，session域失效，回到首页
        usernameList.remove(username);
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/welcome.jsp");
    }

    //根据版块显示对应的主题
    private void findTopicByType(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            //取得版块编号
            String typeId = request.getParameter("typeId");
            BbsService bbsService = new BbsService();
            //查询版块对应的所有主题
            List<Topic> topicList = bbsService.findTopicByType(Integer.parseInt(typeId));
            request.setAttribute("topicList",topicList);
            HttpSession session = request.getSession();
            if(session.isNew()){
                ;
            }else{
                List<String> typeIdList = (List<String>) session.getAttribute("typeIdList");
                if(typeIdList==null){
                    typeIdList = new ArrayList<String>();
                    session.setAttribute("typeIdList",typeIdList);
                }
                bbsService = new BbsService();
                boolean flag = bbsService.isClicked(typeId,typeIdList);
                if(flag){
                    //如果已点击过
                    ;
                }else{
                    //未点击过
                    bbsService.updateClickByType(Integer.parseInt(typeId));
                }
            }
            request.getRequestDispatcher("/WEB-INF/jsp/listAllTopic.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message","根据版块显示对应的主题失败");
            request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
        }
    }

    //根据主题查询所有回复
    private void findReplyByTopic(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            String topicId = request.getParameter("topicId");
            BbsService bbsService = new BbsService();
            List<Reply> replyList = bbsService.findReplyByTopic(Integer.parseInt(topicId));
            request.setAttribute("replyList",replyList);
            request.getRequestDispatcher("/WEB-INF/jsp/listAllReply.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message","根据主题查询所有回复失败");
            request.getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(request,response);
        }
    }
}
