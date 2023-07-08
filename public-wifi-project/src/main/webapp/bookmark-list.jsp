<!-- 메뉴 : 북마크 보기 -->

<%@ page import="Controller.WifiHistory" %>
<%@ page import="Dto.History" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.BookMarkGroupAddController" %>
<%@ page import="Dto.BookMarkGroup" %>
<%@ page import="Controller.BookMarkAddController" %>
<%@ page import="Dto.BookMark" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    BookMarkAddController bookMarkAddController = new BookMarkAddController();
    List<BookMark> bookMarkList = bookMarkAddController.select();
%>
<!DOCTYPE html>
<html>
<head>
    <style>
        #customers {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #customers td, #customers th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #customers tr:nth-child(even){background-color: #f2f2f2;}

        #customers tr:hover {background-color: #ddd;}

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>

<body>


<h1>북마크 그룹 관리</h1>
<a href="index.jsp">홈</a> |
<a href="history.jsp">위치 히스토리 목록</a> |
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
<a href="bookmark-list.jsp">북마크 보기</a> |
<a href="bookmark-group.jsp">북마크 그룹 관리</a>
<br>
<table id="customers">
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이 명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    <% for (int i = 0; i < bookMarkList.size(); i++) {%>
    <tr>
        <td><%=bookMarkList.get(i).getId()%></td>
        <td><%=bookMarkList.get(i).getName()%></td>
        <td><%=bookMarkList.get(i).getWifiName()%></td>
        <td><%=bookMarkList.get(i).getDate()%></td>
        <td>
            <input type="hidden" name="id" value="<%=bookMarkList.get(i).getId()%>" >
            <input type="submit" value="삭제" onclick="moveDelete('<%=bookMarkList.get(i).getName()%>', '<%=bookMarkList.get(i).getWifiName()%>', '<%=bookMarkList.get(i).getDate()%>')">
        </td>
    </tr>
    <% } %>

</table>

</body>
</html>
<script>
    function moveDelete(value, value1, value3) {
        window.location.href = "bookmark-list-delete.jsp?name=" + value + "&wifiName=" + value1 + "&date=" + value3;
    }
</script>