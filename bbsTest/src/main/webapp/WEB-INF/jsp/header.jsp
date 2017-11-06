<%--
  Created by IntelliJ IDEA.
  User: xiang
  Date: 2017/11/1
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <script type="text/javascript">
            /*
            window.onbeforeunload=function(){
                var url = "/bbs/BbsServlet?method=exit";
                window.location.href = url;
            }
            */
        </script>
        <style type="text/css">
            a{
                text-decoration:none
            }
        </style>
    </head>
<body>
    欢迎<font color="red">${!empty sessionScope.user?user.username:'游客'}</font>光临<br/>
    <a href="${pageContext.request.contextPath}/welcome.jsp">首页</a>|
    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/BbsServlet?method=toRegisterJsp">免费注册</a>|
            <a href="${pageContext.request.contextPath}/BbsServlet?method=toLoginJsp">亲，登录</a>|
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/BbsServlet?method=exit">安全退出</a>
        </c:otherwise>
    </c:choose>
<div style="position:absolute;left:720px;top:15px">
    你的IP：${sessionScope.address.ip}
    归属地：${sessionScope.address.location}<br/>
    在线人数：${!empty applicationScope.online?applicationScope.online:'0'}人
    你是第：${!empty sessionScope.caller?sessionScope.caller:'0'}个来访者
</div>
</body>
</html>