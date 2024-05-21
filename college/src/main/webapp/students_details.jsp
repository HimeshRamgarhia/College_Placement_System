<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Display Student Data</title>
    <link rel="stylesheet"  href="students_details.css">
</head>
<body>

<!-- Top-left buttons -->
<div class="top-buttons">
    <button onclick="moveSelected()">Move Selected</button>
    <button onclick="deleteSelected()">Delete Selected</button>
</div>

<!-- Table -->
<table id="students-table">
    <thead>
    <tr>
        <th>Select</th>
        <th>ID</th>
        <th>Name</th>
        <th>Roll No</th>
        <th>Section</th>
        <th>Email</th>
        <th>10th Marks</th>
        <th>12th Marks</th>
        <th>Aggregate</th>
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
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String rollNo = rs.getString("roll_no");
                    String section = rs.getString("section");
                    String email = rs.getString("email");
                    double tenthMarks = rs.getDouble("10th");
                    double twelfthMarks = rs.getDouble("12th");
                    double aggregate = rs.getDouble("aggregate");
        %>
        <tr>
            <td><input type="checkbox" name="selectedStudents" class="select-row" value="<%= id %>"></td>
            <td><%= id %></td>
            <td><%= name %></td>
            <td><%= rollNo %></td>
            <td><%= section %></td>
            <td><%= email %></td>
            <td><%= tenthMarks %></td>
            <td><%= twelfthMarks %></td>
            <td><%= aggregate %></td>
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
<button type="submit" class="process-button" onclick="redirectToPlacedStudents()">next</button>


<!-- JavaScript for handling button clicks -->
<script src="students_details.js"></script>
</body>
</html>
