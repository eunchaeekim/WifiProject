<!-- 근처 WIPE 정보 클릭 시 나오는 화면   -->

<%@ page import="Controller.FindNearWifiInfo" %>
<%@ page import="Dto.Row" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    List<Row> row = (List<Row>) request.getAttribute("rows"); //rows의 속성 값 row에 저장 
    String lat = request.getParameter("lat");
    String lnt = request.getParameter("lnt");
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

        .wifiName {
            cursor: pointer;

            text-decoration: underline;


        }
        .wifiName:hover {
            color:darkblue;
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
    <script>
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                        alert(position.coords.latitude + ' ' + position.coords.longitude);
                        var lat = position.coords.latitude;
                        document.getElementById("latid").value = lat ;
                        var lnt = position.coords.longitude;
                        document.getElementById("lntid").value = lnt;
                    },
                    function (error) {
                        console.error(error);
                    },
                    {
                        enableHighAccuracy: false,
                        maximumAge: 0,
                        timeout: Infinity
                    });
            } else {
                alert('GPS를 지원하지 않습니다');
            }
        }


    </script>
</head>
<body>


<h1>와이파이 정보 구하기</h1>
<a href="index.jsp">홈</a> |
<a href="history.jsp">위치 히스토리 목록</a> |
<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
<a href="bookmark-list.jsp">북마크 보기</a> |
<a href="bookmark-group.jsp">북마크 그룹 관리</a>
<br>
<div>
    <form action="/location.do" method="post">
        <label for="latid">LAT:</label>
        <input type="text" id="latid" name="latname" value="${lat}">


        <label for="lntid">, LNT:</label>
        <input type="text" id="lntid" name="lntname" value="${lnt}">

        <input type="button" value="내 위치 가져오기" onclick="getLocation()">
        <input type="submit" value="근처 WIPI 정보 보기">
    </form>
</div>


<table id="customers">
    <tr>
        <th>거리(Km)</th>
        <th>관리번호</th>
        <th>자치구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI접속환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    <form id="wifiForm" action="wifiDetail" method="post">
        <% for (int i = 0; i < row.size(); i++) { %>
        <tr>
            <td><%=row.get(i).getDISTANCE()%></td>
            <td><%=row.get(i).getX_SWIFI_MGR_NO()%></td>
            <td><%=row.get(i).getX_SWIFI_WRDOFC()%></td>
            <td class="wifiName" data-wifi-id="<%=row.get(i).getX_SWIFI_MAIN_NM()%>" onclick="submitForm(this.dataset.wifiId)"><%=row.get(i).getX_SWIFI_MAIN_NM()%></td>
            <td><%=row.get(i).getX_SWIFI_ADRES1()%></td>
            <td><%=row.get(i).getX_SWIFI_ADRES2()%></td>
            <td><%=row.get(i).getX_SWIFI_INSTL_FLOOR()%></td>
            <td><%=row.get(i).getX_SWIFI_INSTL_TY()%></td>
            <td><%=row.get(i).getX_SWIFI_INSTL_MBY()%></td>
            <td><%=row.get(i).getX_SWIFI_SVC_SE()%></td>
            <td><%=row.get(i).getX_SWIFI_CMCWR()%></td>
            <td><%=row.get(i).getX_SWIFI_CNSTC_YEAR()%></td>
            <td><%=row.get(i).getX_SWIFI_INOUT_DOOR()%></td>
            <td><%=row.get(i).getX_SWIFI_REMARS3()%></td>
            <td><%=row.get(i).getLNT()%></td>
            <td><%=row.get(i).getLAT()%></td>
            <td><%=row.get(i).getWORK_DTTM()%></td>
        </tr>
        <%} %>
    </form>

</table>

</body>
</html>
<script>
    function submitForm(value) {
    	// document는 웹페이지의 Document Object Model(DOM)을 나타내는 객체. DOM은 HTML 문서의 구조와 내용을 표현하는 트리 구조입니다.
        document.getElementById('wifiForm').innerHTML += '<input type="hidden" name="wifiMainName" value="' + value + '">';
        document.getElementById('wifiForm').submit();
    }
</script>