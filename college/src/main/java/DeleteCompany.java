import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;

@WebServlet("/DeleteCompany")
public class DeleteCompany extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read JSON data from request
        BufferedReader reader = request.getReader();
        String jsonData = reader.readLine();
        reader.close();

        // Parse JSON data to get selected company IDs
        Gson gson = new Gson();
        String[] selectedCompanyIds = gson.fromJson(jsonData, String[].class);

        // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String username = "root";
        String password = "7410";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            // Move students associated with deleted companies to students table
            String moveStudentsQuery = "INSERT INTO students (id, name, roll_no, section, email, `10th`, `12th`, aggregate) "
                    + "SELECT id, name, roll_no, section, email, `10th`, `12th`, aggregate "
                    + "FROM placed_students WHERE company IN (SELECT name FROM companies WHERE id IN (" + String.join(",", selectedCompanyIds) + "))";

            PreparedStatement moveStudentsStatement = con.prepareStatement(moveStudentsQuery);
            int studentsMoved = moveStudentsStatement.executeUpdate();

            // Delete students associated with deleted companies from placed_students table
            String deleteStudentsQuery = "DELETE FROM placed_students WHERE company IN (SELECT name FROM companies WHERE id IN (" + String.join(",", selectedCompanyIds) + "))";
            PreparedStatement deleteStudentsStatement = con.prepareStatement(deleteStudentsQuery);
            int studentsDeleted = deleteStudentsStatement.executeUpdate();

            // Delete selected companies from companies table
            String deleteCompaniesQuery = "DELETE FROM companies WHERE id IN (" + String.join(",", selectedCompanyIds) + ")";
            PreparedStatement deleteCompaniesStatement = con.prepareStatement(deleteCompaniesQuery);
            int companiesDeleted = deleteCompaniesStatement.executeUpdate();

            if (studentsMoved > 0 && studentsDeleted > 0 && companiesDeleted > 0) {
                // Send success response
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Send error response
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            // Send error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
