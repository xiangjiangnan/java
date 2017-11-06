package cn.xjn.java.filter;

import cn.xjn.java.domain.Address;
import cn.xjn.java.domain.Flow;
import cn.xjn.java.service.BbsService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;

/**
 * @author xiang
 */
//过滤welcome.jsp页面
public class IpAddressFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        try {
            //接口强转
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            // 取得HttpSession对象
            HttpSession session = request.getSession();
            // 判段当前用户HttpSession中是否存在IP归属地信息
            Address address = (Address) session.getAttribute("address");
            // 如果没有
            if(address == null){
                // 取得IP地址
                String ip = request.getRemoteAddr();
                BbsService bbsService = new BbsService();
                // 取得对应的归属地
                address = bbsService.findAddressByIP(ip);
                // 绑定到HttpSession中
                session.setAttribute("address",address);
            }

            ServletContext context = session.getServletContext();
            Integer yesterdayFlow = (Integer) context.getAttribute("yesterdayFlow");
            //第一次
            if(yesterdayFlow==null){
                BbsService bbsService = new BbsService();
                //取得昨天日期
                Calendar c = Calendar.getInstance();
                //设置日历为一天前
                //c.add(Calendar.DATE,-1);
                c.add(Calendar.DATE,0);
                //取得昨日流量
                Flow flow = bbsService.findFlowByDate(c.getTime());
                context.setAttribute("yesterdayFlow",flow.getNum());
            }
            //发行资源
            chain.doFilter(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
