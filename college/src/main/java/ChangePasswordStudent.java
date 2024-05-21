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

@WebServlet("/ChangePasswordStudent")

public class ChangePasswordStudent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String dbUsername = "root";
        String dbPassword = "7410";

        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Check if the user exists in the students table
            PreparedStatement stmtStudents = con.prepareStatement("SELECT * FROM students WHERE email = ? AND password = ?");
            stmtStudents.setString(1, email);
            stmtStudents.setString(2, oldPassword);
            ResultSet rsStudents = stmtStudents.executeQuery();

            if (rsStudents.next()) {
                // Update the password in the students table
                PreparedStatement updateStmtStudents = con.prepareStatement("UPDATE students SET password = ? WHERE email = ?");
                updateStmtStudents.setString(1, newPassword);
                updateStmtStudents.setString(2, email);
                updateStmtStudents.executeUpdate();

                response.sendRedirect("student_dashboard.jsp"); // Redirect to student dashboard after successful password change
            } else {
                // Check if the user exists in the placed_students table
                PreparedStatement stmtPlacedStudents = con.prepareStatement("SELECT * FROM placed_students WHERE email = ? AND password = ?");
                stmtPlacedStudents.setString(1, email);
                stmtPlacedStudents.setString(2, oldPassword);
                ResultSet rsPlacedStudents = stmtPlacedStudents.executeQuery();

                if (rsPlacedStudents.next()) {
                    // Update the password in the placed_students table
                    PreparedStatement updateStmtPlacedStudents = con.prepareStatement("UPDATE placed_students SET password = ? WHERE email = ?");
                    updateStmtPlacedStudents.setString(1, newPassword);
                    updateStmtPlacedStudents.setString(2, email);
                    updateStmtPlacedStudents.executeUpdate();

                    response.sendRedirect("student_dashboard.jsp"); // Redirect to student dashboard after successful password change
                } else {
                    // Password validation failed
                    request.setAttribute("errorMessage", "Incorrect email or old password");
                    request.getRequestDispatcher("ChangePasswordStudent.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error updating password");
        }
    }
}
