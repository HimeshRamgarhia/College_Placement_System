import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ChangePasswordAdmin")
public class ChangePasswordAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String dbUsername = "root";
        String dbPassword = "7410";

        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Check if username and old password match the ones in the database
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM login WHERE uname = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, oldPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Update the password
                PreparedStatement updateStmt = con.prepareStatement("UPDATE login SET password = ? WHERE uname = ?");
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();

                response.sendRedirect("admin_panel.jsp"); // Redirect to admin panel after successful password change
            } else {
                // Password validation failed
                request.setAttribute("errorMessage", "Incorrect username or old password");
                request.getRequestDispatcher("change_password.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error updating password");
        }
    }
}
