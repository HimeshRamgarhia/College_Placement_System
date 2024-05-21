<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Display Company Data</title>
    <link rel="stylesheet" href="students_details.css"> <!-- Include the students_details.css -->
</head>
<body>

<!-- Top-left buttons -->
<div class="top-buttons">
    <button onclick="deleteSelected()">Delete Selected</button>
</div>

<!-- Table -->
<table id="students-table">
    <thead>
    <tr>
        <th>Select</th>
        <th>ID</th>
        <th>Name</th>
        <th>Total Students</th>
    </tr>
    </thead>
    <tbody>
        <% 
            String url = "jdbc:mysql://localhost:3306/21bcon121";
            String username = "root";
            String password = "7410";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM companies");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int totalStudents = rs.getInt("total_students");
        %>
        <tr>
            <td><input type="checkbox" name="selectedCompanies" class="select-row" value="<%= id %>"></td>
            <td><%= id %></td>
            <td><%= name %></td>
            <td><%= totalStudents %></td>
        </tr>
        <%
                }
                con.close();
            } catch (Exception e) {
                out.println(e);
            }
        %>
    </tbody>
</table>

<!-- Process Selected button -->
<button type="submit" class="process-button" onclick="redirectToAdminPanel()">prev</button>


<!-- JavaScript for handling button clicks -->
<script src="company_details.js"></script>
</body>
</html>
