<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
 <body>
  欢迎${!empty sessionScope.user?user.username:"游客"}光临
  <table border="2" align="center" width="60%">
   <caption><h1>候选人基本信息</h1></caption>
   <tr>
    <th>姓名</th>
    <th>票数</th>
    <th>操作</th>
   </tr>
    <c:forEach var="vote" items="${requestScope.votelist}">
	<tr>
     <td>
      <a href="${pageContext.request.contextPath}/VoteServlet?method=findVoteById&id=${vote.id}" style="text-decoration:none">
       ${vote.content}
      </a>
     </td>
     <td>${vote.ticket}</td>
     <td>
     <c:if test="${!empty sessionScope.user}">
       <a href="${pageContext.request.contextPath}/VoteServlet?method=updateVoteById&id=${vote.id}" style="text-decoration:none">
     	投票
       </a>
      </c:if>
     </td>
    </tr>
   </c:forEach>
   <tr> 
    <td>
      <a href="${pageContext.request.contextPath}/VoteServlet?method=toLogin" style="text-decoration:none">
     	投票登陆
      </a>
     </td>
     <td>
      <a href="${pageContext.request.contextPath}/VoteServlet?method=findAllInfo" style="text-decoration:none">
     	投票人信息
      </a>
     </td>
     <td>
      <c:choose>
       <c:when test="${!empty sessionScope.user}">
        <a href="${pageContext.request.contextPath}/VoteServlet?method=exit" style="text-decoration:none">
     	  退出登陆
        </a>
       </c:when>
       <c:otherwise>
                         退出登陆
       </c:otherwise>
      </c:choose>
     </td>
    </tr> 
  </table>
 </body>
</html>