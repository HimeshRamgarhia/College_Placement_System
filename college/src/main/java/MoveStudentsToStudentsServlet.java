import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/MoveStudentsToPlacedServlet")
public class MoveStudentsToStudentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        // Parse JSON data to get selected student IDs
        Gson gson = new Gson();
        String[] selectedStudentIds = gson.fromJson(json.toString(), String[].class);

        System.out.println("Selected Student IDs: " + Arrays.toString(selectedStudentIds)); // Debug statement

        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String username = "root";
        String password = "7410";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            // Move selected students to placed_students table
        	// Move selected students to students table
        	String moveQuery = "INSERT INTO students (id, name, roll_no, section, email, 10th, 12th, aggregate) "
        	                 + "SELECT id, name, roll_no, section, email, 10th, 12th, aggregate "
        	                 + "FROM placed_students WHERE id IN (" + String.join(",", selectedStudentIds) + ")";

            PreparedStatement moveStatement = con.prepareStatement(moveQuery);
            int rowsAffected = moveStatement.executeUpdate();

            // Delete selected students from students table
            String deleteQuery = "DELETE FROM placed_students WHERE id IN (" + String.join(",", selectedStudentIds) + ")";
            PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsAffected > 0 && rowsDeleted > 0) {
                // Redirect to placed_students.jsp after successful operation
                response.sendRedirect("students_details.jsp");
            } else {
                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"error\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\"}");
        }
    }
}
