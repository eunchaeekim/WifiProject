package Controller;

import Dto.Row;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Dispatch;
import java.io.IOException;
import java.util.List;


/*
 * wifi 이름 클릭 시 동작  
 */
@WebServlet("/wifiDetail")
public class WifiDetail extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String s = request.getParameter("wifiMainName");
        int idx = wifiDetail(s);
        FindNearWifiInfo findNear = new FindNearWifiInfo();
        List<Row> rows = findNear.find();
        Row row = rows.get(idx);
        request.setAttribute("row", row);
        RequestDispatcher dis = request.getRequestDispatcher("/wifiInfo.jsp");
        dis.forward(request, response);
    }

    public int wifiDetail(String s) {
        FindNearWifiInfo findNear = new FindNearWifiInfo();
        List<Row> rows = findNear.find();
        int idx = 0;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getX_SWIFI_MAIN_NM().equals(s)) {
                idx = i;
            }
        }
        return idx;
    }
}
