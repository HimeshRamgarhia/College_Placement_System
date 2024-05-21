import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/studentloginServlet")
public class StudentLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/21bcon121", "root", "7410");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            PreparedStatement ps = con.prepareStatement("select studentName, rollNumber from studentinfo where studentName=? and rollNumber=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                RequestDispatcher rd = request.getRequestDispatcher("student_dashboard.jsp");
                rd.forward(request, response);
            } else {
                out.println("<font color=red size=18 > Login Failed!! <br>");
                out.println("<a href=login.jsp>Try Again!!</a>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.print(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
