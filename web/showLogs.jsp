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
    <title>任务队列打卡日志</title>
    <style>
        td, tr, th{
            align-content: center;
        }

    </style>
</head>
<body>
<table border="2" style="border-style: solid; border-color: greenyellow" align="center">
    <tr style="border-color: green;">
        <td>学号</td>
        <td>消息</td>
    </tr>
    <c:forEach items="${requestScope.get('allLogs')}" var="user" varStatus="s">
        <tr>
            <td>${user.gh}</td>
            <td>${user.msg}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
