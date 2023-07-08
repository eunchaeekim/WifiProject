package Controller;

import Dto.History;
import Dto.Row;

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

@WebServlet("/wifiHistoryDelete")
public class WifiHistory extends HttpServlet {
    private static int Id = 1;

    public void save(Row row) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT MAX(id) FROM history");
            rs = preSt.executeQuery();
            if (rs.next()) {
                Id = rs.getInt(1) + 1;
            }
            preSt = connection.prepareStatement("INSERT INTO history (id, lnt, lat, date) values (?, ?, ?, ?)");
            preSt.setInt(1, Id);
            preSt.setDouble(2, row.getLNT());
            preSt.setDouble(3, row.getLAT());
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

    public List<History> select() {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        List<History> historyList = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("SELECT * FROM history");
            rs = preSt.executeQuery();

            historyList = new ArrayList<>();
            while (rs.next()) {
                History history  = new History();
                history.setId(rs.getInt((1)));
                history.setLnt(rs.getDouble(2));
                history.setLat(rs.getDouble(3));
                history.setDate(rs.getString(4));
                historyList.add(history);
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
        return historyList;
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String s = request.getParameter("id");
        delete(s);
        RequestDispatcher dis = request.getRequestDispatcher("/history.jsp");
        dis.forward(request,response);
    }

    public void delete(String s) {
        String url = "jdbc:mariadb://localhost:3306/wifi";
        String dbUserId = "testuser1";
        String dbPassword = "zerobase";
        Connection connection = null;
        PreparedStatement preSt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            preSt = connection.prepareStatement("DELETE FROM history WHERE id = ?");
            preSt.setString(1, s);
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
