import java.io.*;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/21bcon121", "root", "7410");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            // Check if the user exists in either the login table, students table, or placed_students table
            String sql = "SELECT 'admin' AS role, uname FROM login WHERE uname=? AND password=? "
                       + "UNION "
                       + "SELECT 'student' AS role, email FROM students WHERE email=? AND password=? "
                       + "UNION "
                       + "SELECT 'student' AS role, email FROM placed_students WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, username);
            ps.setString(6, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                if ("admin".equals(role)) {
                    RequestDispatcher rd = request.getRequestDispatcher("admin_panel.jsp");
                    rd.forward(request, response);
                } else if ("student".equals(role)) {
                    RequestDispatcher rd = request.getRequestDispatcher("student_dashboard.jsp");
                    rd.forward(request, response);
                }
            } else {
                out.println("<font color=red size=18 > Login Failed!! <br>");
                out.println("<a href=login.jsp>Try Again!!</a>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.print(e);
        }
    }

}
