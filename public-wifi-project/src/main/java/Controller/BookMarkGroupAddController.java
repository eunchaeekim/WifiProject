package Controller;

import Dto.BookMarkGroup;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bookmark")
public class BookMarkGroupAddController extends HttpServlet {
    private static int Id = 1;
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String turn = request.getParameter("turn");
        System.out.println(turn);
        
        save(name, turn);
        RequestDispatcher dis = request.getRequestDispatcher("/bookmark-group.jsp");
        dis.forward(request,response);
        
    }
    public void save(String name, String turn) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT MAX(id) FROM bookMarkGroup");
            rs = preSt.executeQuery();
            if (rs.next()) {
                Id = rs.getInt(1) + 1;
            }
            preSt = connection.prepareStatement("INSERT INTO bookMarkGroup (id, name, turn, date, dateNew) values (?, ?, ?, ?, ?)");
            preSt.setInt(1, Id);
            preSt.setString(2, name);
            preSt.setString(3, turn);
            preSt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preSt.setString(5, "");
            preSt.executeUpdate();
            Id++;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (preSt != null)
                    preSt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
    public List<BookMarkGroup> select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;
        
        // 조회 결과를 저장할 BookMarkGroup 객체 리스트
        List<BookMarkGroup> bookMarkGroups = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver"); // JDBC 드라이버 클래스 로드
            connection = DriverManager.getConnection(url, dbUserId, dbPassword); // 데이터베이스 연결 수립
            preSt = connection.prepareStatement("SELECT * FROM bookMarkGroup");  // SQL문을 실행하기 위한 PreparedStatement 객체 생성
            rs = preSt.executeQuery();  // SQL문 실행하여 결과 집합 조회

            bookMarkGroups = new ArrayList<>(); // 조회된 결과를 담을 객체 리스트 초기화
            // 결과 집합을 순회하면서 데이터를 읽어와서 객체에 저장하고 리스트에 추가
            while (rs.next()) { 
                BookMarkGroup bookMarkGroup = new BookMarkGroup();
                bookMarkGroup.setId(String.valueOf(rs.getInt((1))));
                bookMarkGroup.setName(rs.getString(2));
                bookMarkGroup.setTurn(rs.getString(3));
                bookMarkGroup.setDate(rs.getString(4));
                bookMarkGroup.setDateNew(rs.getString(5));
                bookMarkGroups.add(bookMarkGroup);
              
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (preSt != null)
                    preSt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookMarkGroups;
    }
   


}
