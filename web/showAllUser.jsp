<%--
  Created by IntelliJ IDEA.
  User: Hang_ccccc
  Date: 2020/5/21
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>任务队列用户信息</title>
    <style>
        td, tr, th{
            align-content: center;
        }

    </style>
</head>
<body>
<h1 style="text-align: center">成功加入自动签到的同学</h1>
<table border="2" style="border-style: solid; border-color: greenyellow" align="center">
    <tr style="border-color: green;">
        <td>姓名</td>
        <td>学号</td>
    </tr>
    <c:forEach items="${requestScope.get('allUser')}" var="user" varStatus="s">
        <tr>
            <td>${user.name}</td>
            <td>${user.gh}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
