<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<body>
		<form action="/LoginServlet" method="post">
			<table border="2" align="center">
				<tr>
				 <th>用户名</th>
				 <td><input type="text" name ="username"/></td>
				</tr>
				<tr>
				 <th>密码</th>
				 <td><input type="password" name ="password"/></td>
				</tr>
				<tr>
				 <td colspan="2" align="center">
				 <input type="submit" value="登陆"/>
				 </td>
				</tr>
			</table>
		</form>
	</body>
</html>