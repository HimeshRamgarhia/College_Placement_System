<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Panel</title>
    <link rel="stylesheet" href="admin_panel.css"> <!-- You can define your CSS styles -->
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h2>Dashboard Menu</h2>
            <ul>
                <li><a href="https://jecrcuniversity.edu.in/">College</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="ChangePassword_Student.jsp" >Change Password</a></li>
                <li><a href="login.jsp">Logout</a></li> <!-- Assuming you have a logout page -->
            </ul>
        </div>
        <div class="main-content">
            <h1>Student Dashboard</h1>
            <div class="dashboard-info">
                <a href="display_students.jsp"><button>View Student Information</button></a>
                <a href="student_info.jsp"><button>Insert Info</button></a> <!-- New button for inserting info -->
                <!-- You can include additional buttons or information here -->
            </div>
            <p>Welcome! Student</p>
        </div>
    </div>
</body>
</html>
