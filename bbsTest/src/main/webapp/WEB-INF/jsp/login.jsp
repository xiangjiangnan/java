<%--
  Created by IntelliJ IDEA.
  User: xiang
  Date: 2017/11/2
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<form action="${pageContext.request.contextPath}/BbsServlet?method=login" method="post">
    <table border="1" align="center">
        <caption><h1>用户登录</h1></caption>
        <tr>
            <th>用户名</th>
            <td><input type="text" name="username" value="${loginForm.username}"/></td>
            <td>${loginForm.errors.username}</td>
        </tr>
        <tr>
            <th>密码</th>
            <td><input type="password" name="password"/></td>
            <td>${loginForm.errors.password}</td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="登录"/>
            </td>
        </tr>
    </table>
</form>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
