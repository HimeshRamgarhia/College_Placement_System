<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Data</title>
</head>
<body>
    <h1>Student Data</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Student Name</th>
            <th>Roll Number</th>
            <th>Completed Semester</th>
            <th>Aggregated GPA</th>
            <th>Pre-Placement Offer</th>
            <th>10th School</th>
            <th>10th Board</th>
            <th>10th Marks</th>
            <th>12th School</th>
            <th>12th Board</th>
            <th>12th Marks</th>
        </tr>
        <% 
            String url = "jdbc:mysql://localhost:3306/21bcon121";
            String username = "root";
            String password = "7410";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM studentinfo");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String studentName = rs.getString("studentName");
                    String rollNumber = rs.getString("rollNumber");
                    int completedSemester = rs.getInt("completedSemester");
                    float aggregatedGPA = rs.getFloat("aggregatedGPA");
                    String prePlacementOffer = rs.getString("prePlacementOffer");
                    String tenthSchool = rs.getString("tenthSchool");
                    String tenthBoard = rs.getString("tenthBoard");
                    int tenthMarks = rs.getInt("tenthMarks");
                    String twelfthSchool = rs.getString("twelfthSchool");
                    String twelfthBoard = rs.getString("twelfthBoard");
        %>
<tr>
            <td><%= id %></td>
            <td><%= rs.getString("studentName") %></td>
            <td><%= rs.getString("rollNumber") %></td>
            <td><%= rs.getInt("completedSemester") %></td>
            <td><%= rs.getFloat("aggregatedGPA") %></td>
            <td><%= rs.getString("prePlacementOffer") %></td>
            <td><%= rs.getString("tenthSchool") %></td>
            <td><%= rs.getString("tenthBoard") %></td>
            <td><%= rs.getInt("tenthMarks") %></td>
            <td><%= rs.getString("twelfthSchool") %></td>
            <td><%= rs.getString("twelfthBoard") %></td>
            <td><%= rs.getInt("twelfthMarks") %></td>
        </tr>
        <% 
                }
                con.close();
            } catch (Exception e) {
                out.println(e);
            }
        %>
    </table>
</body>
</html>
