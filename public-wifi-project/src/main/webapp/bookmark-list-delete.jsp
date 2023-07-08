<%@ page import="Controller.WifiHistory" %>
<%@ page import="Dto.History" %>
<%@ page import="java.util.List" %>
<%@ page import="Dto.BookMarkGroup" %>
<%@ page import="Controller.BookMarkGroupAddController" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
  String name = request.getParameter("name");
  String wifiName = request.getParameter("wifiName");
  String date = request.getParameter("date");
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
    #add {
      position: absolute;
      left: 50%;
      margin-top: 10px;
    }
    #add1 {
      position: absolute;
      left: 43%;
      margin-top: 12px;
    }
  </style>
</head>

<body>


<h1>북마크 관리</h1>
<a href="index.jsp">홈</a> |
<a href="history.jsp">위치 히스토리 목록</a> |
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
<a href="bookmark-list.jsp">북마크 보기</a> |
<a href="bookmark-group.jsp">북마크 그룹 관리</a>
<br>
북마크를 삭제하시겠습니까?

<form action="bookMarkDelete" method="post">
  <table id="customers">
    <tr>
      <th>북마크 이름</th>
      <td><%=name%></td>
    </tr>
    <tr>
      <th>와이파이명</th>
      <td><%=wifiName%></td>
      <input type="hidden" name="wifiName" value="<%=wifiName%>">
    </tr>
    <tr>
      <th>등록일자</th>
      <td><%=date%></td>
    </tr>
  </table>
  <a href="bookmark-list.jsp" id="add1">돌아가기   </a>
  <input type="submit" id="add" value="삭제" onclick="alert('삭제 되었습니다.')">
</form>

</body>
</html>