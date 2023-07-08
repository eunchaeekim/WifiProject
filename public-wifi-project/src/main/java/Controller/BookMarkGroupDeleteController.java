package Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/bookMarkGroupDelete")
public class BookMarkGroupDeleteController extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        RequestDispatcher dis = request.getRequestDispatcher("bookmark-group.jsp");
        delete(name);
        dis.forward(request,response);
    }
    public void delete(String name) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("DELETE FROM bookMarkGroup WHERE name = ?");
            preSt.setString(1, name);
            preSt.executeUpdate();
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
}
