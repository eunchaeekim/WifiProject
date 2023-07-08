<!-- 메뉴 : 홈  -->

<!--  JSP 페이지의 속성을 설정 -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- html 구조  -->
<!--
<html>
<head>
    <style>
    --- CSS ----
    </style>
    <script>
    --- JavaScript ---
    </script>
</head>
<body>
   <table>
        <thead>
            <tr>
                <th>헤더</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>데이터</td>
            </tr>
        </tbody>
    </table>
</body>
</html>
  -->
  
<!DOCTYPE html>
<html>
<head>
    <style>
        /* customers는 id 속성 값이 "customers"인 요소를 선택. HTML에서 id="customers"로 정의된 요소를 가리킴  */
        #customers {
            font-family: Arial, Helvetica, sans-serif; /* 글꼴, 용자의 시스템에 Arial 글꼴이 설치되어 있다면 해당 글꼴을 사용하고, 설치되어 있지 않다면 Helvetica를 사용  */
            border-collapse: collapse; /* 선택된 요소의 테두리를 병합하여 테이블 모양 생성 */
            width: 100%; /* 선택된 요소의 너비를 부모 요소의 100%로 설정 */
        }
        /* tr : table row (테이블 행, th와 td를 담음) */
		/*  th: table header (컬럼제목), td : table data (실제 데이터) */
        #customers td, #customers th {
            border: 1px solid #ddd; /* 테두리를 1px의 너비로, #ddd(회색 계통)으로 설정 */
            padding: 8px; /* 요소의 안쪽 여백을 8px로 설정 */
        }

        #customers tr:nth-child(even){background-color: #f2f2f2;} /* 배경색을 #f2f2f2(연한 회색)으로 설정  */

        #customers tr:hover {background-color: #ddd;} /* 마우스를 올린 행은 #ddd(회색 계통) 배경  */
        

        #customers th {
            padding-top: 12px; /* 요소의 위쪽 여백을 12px */
            padding-bottom: 12px; /* 요소의 아래쪽 여백을 12px로 설정 */
            text-align: left; /* 요소의 텍스트를 왼쪽으로 정렬 */
            background-color: #04AA6D; /* 배경색을 #04AA6D(진한 초록색)으로 설정 */
            color: white; /* 선택된 요소의 텍스트 색상을 흰색으로 설정 */
        }
    </style>
    <script>
	    function getLocation() {
	    	// 브라우저가 위치 정보를 지원하는 경우 
	        if (navigator.geolocation) {
	            navigator.geolocation.getCurrentPosition(function (position) { // 현재 위치를 가져옴 
	                alert(position.coords.latitude.toFixed(7) + ' ' + position.coords.longitude.toFixed(7)); // 경고창을 통해 위도와 경도 좌표를 소수점 아래 7자리까지 표시
	                var lat = position.coords.latitude.toFixed(7); // 위도 값을 변수에 저장
	                document.getElementById("latid").value = lat ; // id가 "latid"인 요소의 값을 위도 값으로 설정
	                var lnt = position.coords.longitude.toFixed(7);  // 경도 값을 변수에 저장
	                document.getElementById("lntid").value = lnt;  // id가 "lntid"인 요소의 값을 경도 값으로 설정
	            },
	            function (error) { // 위치 가져오기에 실패한 경우
	                console.error(error); // 에러를 콘솔에 출력
	            },
	            {
	                enableHighAccuracy: false, // 위치 정보의 정확도를 설정. true는 배터리 소모를 더 많이 할 수 있으므로, false로 설정하는 것이 기본적으로 권장
	                maximumAge: 0, // 캐시된 위치 정보의 최대 유효 기간을 설정 (0으로 설정하면 항상 새로운 위치 정보)
	                timeout: Infinity // 치 정보를 가져오는 데 최대 허용 시간을 설정 (Infinity로 설정하면 시간 제한이 없음)
	            });
	        } else {
	            alert('GPS를 지원하지 않습니다'); // 위치 정보를 지원하지 않는 경우 경고창을 표시
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
    <!-- action: 폼이 서버로 제출될 때 데이터를 처리할 URL을 지정 -->
    <!-- 폼 데이터를 서버로 전송하는 HTTP 메서드를 지정. POST 방식은 폼 데이터를 요청 본문에 포함시켜 전송하는 방식. -->
    <form action="location.do" method="post">
   		 <!-- 사용자에게 위도 값을 입력받기 위한 텍스트 입력(input) 요소
             id 속성은 "latid"로 설정되어 해당 요소를 식별할 수 있음  
             name 속성은 "latname"으로 설정되어 서버로 전송될 때 해당 이름으로 값을 식별할 수 있음 
             초기 값으로 "0.0"이 설정되어 있음  -->
        <label for="latid">LAT:</label>
        <input type="text" id="latid" name="latname" value="0.0">
         
 		<!-- 사용자에게 경도 값을 입력받기 위한 텍스트 입력(input) 요소 
             id 속성은 "lntid"로 설정되어 해당 요소를 식별할 수 있음  
             name 속성은 "lntname"으로 설정되어 서버로 전송될 때 해당 이름으로 값을 식별할 수 있음 .
             초기 값으로 "0.0"이 설정되어 있음  -->
        <label for="lntid">, LNT:</label>
        <input type="text" id="lntid" name="lntname" value="0.0">
        
        <!-- 사용자가 클릭하면 getLocation() 함수를 실행하는 버튼(input) 요소 
             버튼의 텍스트는 "내 위치 가져오기"로 표시됩니다. -->
        <input type="button" value="내 위치 가져오기" onclick="getLocation()">
         <!-- 사용자가 클릭하면 폼(form)을 서버로 제출하는 버튼(input) 요소
             버튼의 텍스트는 "근처 WIPI 정보 보기"로 표시 -->
        <input type="submit" value="근처 WIPI 정보 보기">
    </form>
</div>


<table id="customers">
	<thead>
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
    </thead>
    	<tbody>
			<tr>
				<td id="initial-value" colspan='17' style="text-align: center;">위치 정보를 입력한 후에 조회해 주세요.</td>
			</tr>
    	</tbody>
</table>

</body>
</html>