package Controller;

import Dto.Root;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/bookMarkChange")
public class BookMarkGroupChangeController extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String turn = request.getParameter("turn");
        String id = request.getParameter("id");
        change(name, turn, id);
        RequestDispatcher dis = request.getRequestDispatcher("/bookmark-group.jsp");
        dis.forward(request, response);
    }

    public void change(String name, String turn, String id) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;
        Root root = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT id FROM bookMarkGroup");
            rs = preSt.executeQuery();

            while (rs.next()) {
                if(rs.getString(1).equals(id)) {
                    String distanceUpdate = "UPDATE bookMarkGroup SET name = ?, turn = ?, dateNew = ? WHERE id = ?";
                    PreparedStatement distanceSt = connection.prepareStatement(distanceUpdate);
                    distanceSt.setString(1, name);
                    distanceSt.setString(2, turn);
                    distanceSt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    distanceSt.setString(4, id);
                    distanceSt.addBatch(); //여러 개의 SQL 문을 한꺼번에 실행하기 위해 일시적으로 메모리에 모아
                    distanceSt.executeBatch(); //추가된 모든 SQL 문을 실행
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    }
}
