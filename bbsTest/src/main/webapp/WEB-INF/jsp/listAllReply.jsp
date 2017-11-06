<%--
  Created by IntelliJ IDEA.
  User: xiang
  Date: 2017/11/3
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<table border="1" align="center" width="80%">
    <caption>回复信息</caption>
    <tr>
        <th></th>
        <th>主题</th>
        <th>内容</th>
        <th>作者</th>
        <th>更新时间</th>
    </tr>
    <c:forEach var="reply" items="${requestScope.replyList}" varStatus="status">
        <tr>
            <td>
                <c:if test="${status.first==true}">
                    <img src="image/comment.jpg" height="20px" width="30px"/>
                </c:if>
            </td>
            <td>
                    ${reply.title}
            </td>
            <td>${reply.content}</td>
            <td>${reply.name}</td>
            <td>
                <fmt:formatDate
                        value="${reply.time}"
                        type="both"
                        dateStyle="full"
                        timeStyle="default"
                />
            </td>
        </tr>
    </c:forEach>
</table>
<p align="right">
    <a href="${pageContext.request.contextPath}/BbsServlet?method=findTopicByType&typeId=${param.typeId}">返回上一级</a>
</p>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>