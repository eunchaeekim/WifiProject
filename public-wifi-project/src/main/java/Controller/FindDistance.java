package Controller;

import Dto.Root;
import Dto.Row;
import Dto.WifiInfo;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * 근처 WIPI 정보 보기 클릭 시 동작  
 */
@WebServlet("/location.do")
public class FindDistance extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        // 사용자로부터 위도와 경도 값을 받아옴
        double lat = Double.parseDouble(request.getParameter("latname"));
        double lnt = Double.parseDouble(request.getParameter("lntname"));
        // select() 메서드를 호출하여 해당 위치에 대한 Wi-Fi 정보 검색
        List<Row> rows = select(lat, lnt);
        // 검색 결과 및 위도, 경도 값을 요청 객체의 속성에 설정
        request.setAttribute("lat", lat);
        request.setAttribute("lnt", lnt);
        request.setAttribute("rows", rows);
        // 검색 결과를 출력하기 위해 "/nearWifi.jsp"로 포워드
        RequestDispatcher dis = request.getRequestDispatcher("/nearWifi.jsp");
        dis.forward(request, response);
    }
    
    // 주어진 위도와 경도 값을 기준으로 Wi-Fi 정보를 검색하는 메서드
    public List<Row> select(double lat, double lnt){
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;  // 데이터베이스 연결
        PreparedStatement preSt = null;  //SQL 문을 실행하기 위한 준비된 문
        ResultSet rs = null; //쿼리의 실행 결과인 데이터 집합을 나타내는 객체
        List<Row> rowInfo = null; // 검색 결과 값 담는 list 
        try {
        	// MariaDB JDBC 드라이버 로드
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 예외 정보를 콘솔에 출력
        }
        try {
        	// 데이터베이스 연결
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            // SQL 쿼리 실행
            preSt = connection.prepareStatement("SELECT *, ROUND(6371 * ACOS(COS(RADIANS(?)) * COS(RADIANS(LAT)) * COS(RADIANS(LNT) - RADIANS(?)) + SIN(RADIANS(?)) * SIN(RADIANS(LAT))), 4)  AS DISTANCE_KM FROM wifi_info ORDER BY DISTANCE_KM ASC LIMIT 20;");
            preSt.setDouble(1, lnt);
            preSt.setDouble(2, lat);
            preSt.setDouble(3, lnt);
            rs = preSt.executeQuery();
            // 검색 결과를 저장할 리스트 생성
            rowInfo = new ArrayList<>();

            while (rs.next()) {
            	// 각 행의 Wi-Fi 정보를 Row 객체에 저장
                Row row = new Row();
                row.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                row.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                row.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                row.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                row.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                row.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                row.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                row.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                row.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                row.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                row.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                row.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                row.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                row.setLNT(rs.getDouble("lnt"));
                row.setLAT(rs.getDouble("lat"));
                row.setWORK_DTTM(rs.getString("WORK_DTTM"));
                row.setDISTANCE(rs.getDouble("DISTANCE_KM"));
                rowInfo.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	// 사용한 리소스 정리
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 검색 결과 리스트 반환
        return rowInfo;
    }
}