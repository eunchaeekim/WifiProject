<!-- 메뉴 : 위치 히스토리 목록-->

<%@ page import="Controller.WifiHistory" %>
<%@ page import="Dto.History" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<%
    WifiHistory history = new WifiHistory();
    List<History> hi = history.select();
%>
<body>


<h1>위치 히스토리 목록</h1>
<a href="index.jsp">홈</a> |
<a href="history.jsp">위치 히스토리 목록</a> |
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
<a href="bookmark-list.jsp">북마크 보기</a> |
<a href="bookmark-group.jsp">북마크 그룹 관리</a>
<br>

<table id="customers">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <% for (int i = hi.size() -1; i >= 0; i--) { %>
    <tr>
        <td><%=hi.get(i).getId()%></td>
        <td><%=hi.get(i).getLnt()%></td>
        <td><%=hi.get(i).getLat()%></td>
        <td><%=hi.get(i).getDate()%></td>
        <td>
          <form action="wifiHistoryDelete" method="post">
                <input type="hidden" name="id" value="<%=hi.get(i).getId()%>">
                <input type="submit" value="삭제">
         </form>
        </td>
    </tr>
  
    <%} %>
</table>

</body>
</html>