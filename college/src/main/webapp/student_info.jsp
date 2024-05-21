<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="student_dashboard.css">
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= session.getAttribute("username") %></h1>
        <h2>Upload Your Information</h2>
        <form action="InsertInfoServlet" method="post" class="form-grid">
            <div class="form-group">
                <label for="studentName">Student Name:</label>
                <input type="text" id="studentName" name="studentName" required>
            </div>
            <div class="form-group">
                <label for="rollNumber">Roll Number:</label>
                <input type="text" id="rollNumber" name="rollNumber" pattern="^(?=[a-zA-Z0-9]*[a-zA-Z])[a-zA-Z0-9]{9}$" required>
            </div>
            <div class="form-group">
                <label for="completedSemester">Completed Semester:</label>
                <input type="number" id="completedSemester" name="completedSemester" min="1" max="8" required>
            </div>
            <div class="form-group">
                <label for="aggregatedGPA">Aggregated GPA till Completed Semester:</label>
                <input type="number" id="aggregatedGPA" name="aggregatedGPA" step="0.01" min="0" max="10" required>
            </div>
            <div class="form-group">
                <label for="prePlacementOffer">Pre-Placement Offer:</label>
                <input type="text" id="prePlacementOffer" name="prePlacementOffer">
            </div>
            <div class="form-group">
                <label for="tenthSchool">10th School:</label>
                <input type="text" id="tenthSchool" name="tenthSchool" required>
            </div>
            <div class="form-group">
                <label for="tenthBoard">10th Board:</label>
                <input type="text" id="tenthBoard" name="tenthBoard" required>
            </div>
            <div class="form-group">
                <label for="tenthMarks">10th Marks:</label>
                <input type="number" id="tenthMarks" name="tenthMarks" min="0" max="100" required>
            </div>
            <div class="form-group">
                <label for="twelfthSchool">12th School:</label>
                <input type="text" id="twelfthSchool" name="twelfthSchool" required>
            </div>
            <div class="form-group">
                <label for="twelfthBoard">12th Board:</label>
                <input type="text" id="twelfthBoard" name="twelfthBoard" required>
            </div>
            <div class="form-group">
                <label for="twelfthMarks">12th Marks:</label>
                <input type="number" id="twelfthMarks" name="twelfthMarks" min="0" max="100" required>
            </div>
             <div class="form-group">
        <button type="submit">Submit</button>
    		</div>
        </form>
    </div>
</body>
</html>
