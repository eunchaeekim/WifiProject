package Controller;

import Dto.BookMark;

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

@WebServlet("/bookMarkAdd")
public class BookMarkAddController extends HttpServlet {
    private static int Id = 1;
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String wifiName = request.getParameter("wifiName");
        String bookName = request.getParameter("bookName");
        System.out.println(bookName);

        add(wifiName, bookName);
        
        // 포워딩은 웹 애플리케이션에서 클라이언트의 요청을 다른 자원(서블릿, JSP, HTML 등)으로 전달하는 기능
        // 클라이언트가 웹 서버에 요청을 보내면, 웹 서버는 해당 요청을 처리하기 위해 적절한 자원(서블릿, JSP 등)을 찾는다.
        // 이때, 포워딩을 사용하면 웹 서버는 클라이언트의 요청을 다른 자원으로 전달하여 추가적인 처리나 작업을 수행할 수 있다.
        RequestDispatcher dis = request.getRequestDispatcher("/bookmark-list.jsp");
        dis.forward(request, response);
    }
    public void add(String wifiName, String bookName) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT MAX(id) FROM bookmark");
            rs = preSt.executeQuery();
            if (rs.next()) {
                Id = rs.getInt(1) + 1;
            }
            preSt = connection.prepareStatement("INSERT INTO bookMark (id, name, wifiName, date) values (?, ?, ?, ?)");
            preSt.setInt(1, Id);
            preSt.setString(2, bookName);
            preSt.setString(3, wifiName);
            preSt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
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

    public List<BookMark> select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        List<BookMark> bookMarks = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT * FROM bookMark");
            rs = preSt.executeQuery();

            bookMarks = new ArrayList<>();
            while (rs.next()) {
                BookMark bookMark = new BookMark();
                bookMark.setId(String.valueOf(rs.getInt((1))));
                bookMark.setName(rs.getString(2));
                bookMark.setWifiName(rs.getString(3));
                bookMark.setDate(rs.getString(4));
                bookMarks.add(bookMark);
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
        return bookMarks;
    }
}
