<!-- 메뉴 : Open API 와이파이 정보 가져오기 -->

<%@ page import="Db.Db" %>
<%@ page import="Dto.Root" %>
<%@ page import="java.sql.SQLException" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Db db = new Db();
    int total = db.save();

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 style="text-align: center;"><%=total%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
<div style="text-align: center;">
    <a href="index.jsp">홈 으로 가기</a>
</div>
</body>
</html>
