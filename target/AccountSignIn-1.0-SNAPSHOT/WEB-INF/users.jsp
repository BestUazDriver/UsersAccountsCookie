<%@ page import="com.sabitov.models.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 15.10.2021
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>users list</title>
</head>
<body>
    <%
List <User> users=(List<User>) request.getAttribute("listUsers");
for(User user : users){

%>
<td><%=user.getLogin()%>
</td>
<td><%=user.getPassword()%>
</td>
    <%
}
%>
<
/body>
</html>
