import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CompanyUpdateServlet")
public class CompanyUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the request
        int id = Integer.parseInt(request.getParameter("id"));
        String companyName = request.getParameter("companyName");
        String originalCompanyName = request.getParameter("originalCompanyName"); // Retrieve original company name
        System.out.println(originalCompanyName);
            // JDBC Connection parameters
        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String username = "root";
        String password = "7410";

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            if (!companyName.equals(originalCompanyName)) { // Compare with original company name
                // Prepare the update query for placed_students table
                String updateQuery = "UPDATE placed_students SET company = ? WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
                preparedStatement.setString(1, companyName);
                preparedStatement.setInt(2, id);

                // Execute the update query for placed_students table
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if the company exists in the companies table
                String checkQuery = "SELECT id FROM companies WHERE name = ?";
                PreparedStatement checkStatement = con.prepareStatement(checkQuery);
                checkStatement.setString(1, companyName);
                ResultSet resultSet = checkStatement.executeQuery();

                if (!resultSet.next()) {
                    // Company doesn't exist, insert a new row in the companies table
                    String insertQuery = "INSERT INTO companies (name, total_students) VALUES (?, 1)";
                    PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                    insertStatement.setString(1, companyName);
                    insertStatement.executeUpdate();
                } else {
                    int companyId = resultSet.getInt("id");
                    if (!companyName.equals(originalCompanyName)) {
                        // If the company name has been changed, update total_students count
                        String updateCompanyQuery = "UPDATE companies SET total_students = total_students + 1 WHERE id = ?";
                        PreparedStatement updateCompanyStatement = con.prepareStatement(updateCompanyQuery);
                        updateCompanyStatement.setInt(1, companyId);
                        updateCompanyStatement.executeUpdate();
                    }
                }

                // Send response based on the update result
                if (rowsAffected > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().println("Company name updated successfully");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().println("Failed to update company name");
                }

                // Close resources
                preparedStatement.close();
                checkStatement.close();
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error updating company name");
        }
    }
}
