<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
 <body>
  欢迎${!empty sessionScope.user?user.username:"游客"}光临
  <table border="2" align="center" width="60%">
   <caption><h1>投票人信息</h1></caption>
   <tr>
    <th>IP地址</th>
    <th>投票时间</th>
   </tr>
    <c:forEach var="info" items="${requestScope.infolist}">
	<tr>
     <td>${info.ip}</td>
     <td><fmt:formatDate type="both" dateStyle="full" 
     timeStyle="default" value="${info.votetime}"/></td>
    </tr>
   </c:forEach>
   <tr>
   <td align="center" colspan="2">
      <a href="${pageContext.request.contextPath}/welcome.jsp" style="text-decoration:none">
     	返回
      </a>
   </td>
   </tr>
  </table>
 </body>
</html>