<%--
  Created by IntelliJ IDEA.
  User: xiang
  Date: 2017/11/3
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<table border="1" align="center" width="60%">
    <caption>主题信息</caption>
    <tr>
        <th></th>
        <th>主题</th>
        <th>作者</th>
        <th>回复数</th>
        <th>更新时间</th>
    </tr>
    <c:forEach var="topic" items="${requestScope.topicList}" varStatus="status">
        <tr>
            <td>
                <c:if test="${status.first==true}">
                    <img src="image/comment.jpg" height="20px" width="30px"/>
                </c:if>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/BbsServlet?method=findReplyByTopic&topicId=${topic.id}&typeId=${param.typeId}">
                        ${topic.title}
                </a>
            </td>
            <td>${topic.name}</td>
            <td>${topic.replyNum}</td>
            <td>${topic.time}</td>
        </tr>
    </c:forEach>
</table>
<p align="right">
    <a href="${pageContext.request.contextPath}/welcome.jsp">返回上一级</a>
</p>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
