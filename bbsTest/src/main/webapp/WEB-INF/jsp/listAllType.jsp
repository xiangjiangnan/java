<%--
  Created by IntelliJ IDEA.
  User: xiang
  Date: 2017/11/1
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <div style="position:absolute;left:850px;top:120px;font-size:12px">
        论坛<b>今日</b>流量：<font color="red">${!empty applicationScope.todayFlow?applicationScope.todayFlow:'0'}</font><br/>
        论坛<b>昨日</b>流量：<font color="red">${!empty applicationScope.yesterdayFlow?applicationScope.yesterdayFlow:'0'}</font><br/>
    </div>
    <table border="1" align="center" width="60%">
        <caption><h1>论坛系统</h1></caption>
        <tr>
            <th>图标</th>
            <th>名称</th>
            <th>点击数</th>
            <th>主题数</th>
            <th>最新主题</th>
            <th>版主</th>
        </tr>
        <c:forEach var="type" items="${typelist}">
            <tr>
                <td><img src="${type.image}" width="40" height="30"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/BbsServlet?method=findTopicByType&typeId=${type.id}">
                        ${type.title}
                    </a>
                </td>
                <td>${type.click}</td>
                <td>${type.topicNum}</td>
                <td>
                    <div style="font-size:10px">
                        主题:${type.topic.title}<br/>
                        作者:${type.topic.name}<br/>
                        时间:
                        <fmt:formatDate
                                value="${type.topic.time}"
                                type="both"
                                dateStyle="full"
                                timeStyle="default"
                        />
                        <br/>
                    </div>
                </td>
                <td>${type.admin.name}</td>
            </tr>
        </c:forEach>
    </table>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
