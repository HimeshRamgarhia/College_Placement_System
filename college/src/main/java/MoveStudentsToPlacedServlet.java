import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/MoveStudentsToPlacedServlet")
public class MoveStudentsToPlacedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read JSON data from request
        BufferedReader reader = request.getReader();
        String jsonData = reader.readLine();
        reader.close();

        // Parse JSON data to get selected student IDs
        Gson gson = new Gson();
        String[] selectedStudentIds = gson.fromJson(jsonData, String[].class);

        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String username = "root";
        String password = "7410";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            // Move selected students to placed_students table with the company name set to null
            String moveQuery = "INSERT INTO placed_students (id, name, roll_no, section, email, 10th, 12th, aggregate, company) "
                             + "SELECT id, name, roll_no, section, email, 10th, 12th, aggregate, NULL "
                             + "FROM students WHERE id IN (" + String.join(",", selectedStudentIds) + ")";
            PreparedStatement moveStatement = con.prepareStatement(moveQuery);
            int rowsAffected = moveStatement.executeUpdate();

            // Delete selected students from students table
            String deleteQuery = "DELETE FROM students WHERE id IN (" + String.join(",", selectedStudentIds) + ")";
            PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsAffected > 0 && rowsDeleted > 0) {
                // Send success response
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Send error response
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Send error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}
