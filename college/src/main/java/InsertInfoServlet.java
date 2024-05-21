import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/InsertInfoServlet")
public class InsertInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentName = request.getParameter("studentName");
        String rollNumber = request.getParameter("rollNumber");
        int completedSemester = Integer.parseInt(request.getParameter("completedSemester"));
        float aggregatedGPA = Float.parseFloat(request.getParameter("aggregatedGPA"));
        String prePlacementOffer = request.getParameter("prePlacementOffer");
        String tenthSchool = request.getParameter("tenthSchool");
        String tenthBoard = request.getParameter("tenthBoard");
        int tenthMarks = Integer.parseInt(request.getParameter("tenthMarks"));
        String twelfthSchool = request.getParameter("twelfthSchool");
        String twelfthBoard = request.getParameter("twelfthBoard");
        int twelfthMarks = Integer.parseInt(request.getParameter("twelfthMarks"));

        String url = "jdbc:mysql://localhost:3306/21bcon121";
        String username = "root";
        String password = "7410";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                String query = "INSERT INTO studentinfo (studentName, rollNumber, completedSemester, aggregatedGPA, prePlacementOffer, tenthSchool, tenthBoard, tenthMarks, twelfthSchool, twelfthBoard, twelfthMarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, studentName);
                    pstmt.setString(2, rollNumber);
                    pstmt.setInt(3, completedSemester);
                    pstmt.setFloat(4, aggregatedGPA);
                    pstmt.setString(5, prePlacementOffer);
                    pstmt.setString(6, tenthSchool);
                    pstmt.setString(7, tenthBoard);
                    pstmt.setInt(8, tenthMarks);
                    pstmt.setString(9, twelfthSchool);
                    pstmt.setString(10, twelfthBoard);
                    pstmt.setInt(11, twelfthMarks);
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        response.sendRedirect("student_dashboard.jsp");
                    } else {
                        response.sendRedirect("error.jsp"); // Redirect to error page if insertion fails
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to error page if JDBC driver not found
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to error page if SQL exception occurs
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to error page if number format exception occurs
        }
    }
}
