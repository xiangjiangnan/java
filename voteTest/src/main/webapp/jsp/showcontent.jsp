<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <body>
  <table border="2" align="center" width="40%">
   <caption><h1>候选人详细信息</h1></caption>
   <tr>
    <th>姓名</th>
    <td>${content.vote.content}</td>
    </tr>
    <tr>
    <th>年龄</th>
    <td>${content.age}</td>
    </tr>
    <tr>
    <th>描述</th>
    <td>${content.description}</td>
   </tr>
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