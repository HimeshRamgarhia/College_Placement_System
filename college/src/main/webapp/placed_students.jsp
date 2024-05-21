<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Display Student Data</title>
    <link rel="stylesheet" type="text/css" href="students_details.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
    #students-table th {
        background-color: #214f73;
    }
    </style>
</head>
<body>

<!-- Top buttons -->
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
        <th>Company</th> <!-- New table header for Company field -->
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM placed_students");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String rollNo = rs.getString("roll_no");
                String section = rs.getString("section");
                String email = rs.getString("email");
                double tenthMarks = rs.getDouble("10th");
                double twelfthMarks = rs.getDouble("12th");
                double aggregate = rs.getDouble("aggregate");
                String company = rs.getString("company"); // Fetch company information
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
    <td data-id="<%= id %>">
        <input type="text" class="edit-company" value="<%= company %>">
        <span class="edit-icon" style="cursor: pointer">✏️</span>
    </td>
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

<!-- JavaScript for handling button clicks -->
<script>


    function redirectToStudentsDetails() {
        window.location.href = "students_details.jsp";
    }

    // Attach click event listener to edit icons only
    document.addEventListener('click', function(event) {
        var target = event.target;
        if (target.classList.contains('edit-icon')) {
            var tdElement = target.parentNode;
            var companyId = tdElement.getAttribute('data-id');
            var companyNameInput = tdElement.querySelector('.edit-company');
            var companyName = companyNameInput.value;
            var newCompanyName = prompt("Enter new company name:", companyName);
            if (newCompanyName !== null && newCompanyName !== '') {
                companyNameInput.value = newCompanyName;
                updateCompany(companyId, newCompanyName);
            }
        }
    });

    function updateCompany(companyId, newCompanyName) {
        // Retrieve the original company name from the hidden input field
        var originalCompanyName = document.querySelector('input[name="originalCompanyName"]').value;

        // Check if the new company name is the same as the original name
        if (originalCompanyName === newCompanyName) {
            alert("New company name cannot be the same as the original name.");
            return;
        }

        // Create a new XMLHttpRequest object
        var xhr = new XMLHttpRequest();

        // Configure the request
        xhr.open("POST", "CompanyUpdateServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Define the callback function to handle the response
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    // Handle success
                    alert("Company name updated successfully");
                    // Optionally, you can update the UI here
                } else {
                    // Handle error
                    console.error("Error updating company name:", xhr.responseText);
                    alert("Error updating company name. Please try again.");
                }
            }
        };

        // Prepare the parameters to send with the request
        var params = "id=" + encodeURIComponent(companyId) + "&companyName=" + encodeURIComponent(newCompanyName) + "&originalCompanyName=" + encodeURIComponent(originalCompanyName);

        // Send the request
        xhr.send(params);
    }



    function moveSelected() {
        var selectedStudentIds = [];
        var checkboxes = document.querySelectorAll('input[name="selectedStudents"]:checked');
        checkboxes.forEach(function(checkbox) {
            selectedStudentIds.push(checkbox.value);
        });

        if (selectedStudentIds.length === 0) {
            alert("Please select at least one student.");
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "MoveStudentsToStudentsServlet", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    // Remove selected student rows from the table
                    checkboxes.forEach(function(checkbox) {
                        checkbox.closest("tr").remove();
                    });
                } else {
                    console.error("Error moving students:", xhr.responseText);
                    alert("Error moving students. Please try again.");
                }
            }
        };
        xhr.send(JSON.stringify(selectedStudentIds));
    }

    
    function deleteSelected() {
        var selectedStudentIds = [];
        var checkboxes = document.querySelectorAll('input[name="selectedStudents"]:checked');
        
        checkboxes.forEach(function(checkbox) {
            selectedStudentIds.push(checkbox.value);
        });

        if (selectedStudentIds.length === 0) {
            alert("Please select at least one student.");
            return;
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "DeletePlacedStudServlet", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    // Remove selected student rows from the table
                    checkboxes.forEach(function(checkbox) {
                        checkbox.closest("tr").remove();
                    });
                } else {
                    console.error("Error moving students:", xhr.responseText);
                    alert("Error moving students. Please try again.");
                }
            }
        };
        
        // Send selected student IDs to the servlet
        xhr.send(JSON.stringify(selectedStudentIds));
    }


</script>

<button type="submit" class="process-button" onclick="redirectToStudentsDetails()">Previous</button>

<script src="placed_students.js"></script>
</body>
</html>
