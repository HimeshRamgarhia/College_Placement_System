

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

    function updateCompany(companyId, newCompanyName) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "CompanyUpdateServlet", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
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
        var params = "id=" + encodeURIComponent(companyId) + "&companyName=" + encodeURIComponent(newCompanyName);
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

    // Attach click event listener to edit icons
    document.addEventListener('click', function(event) {
        var target = event.target;
        if (target.classList.contains('edit-icon')) {
            editCompany(target);
        }
    });

// Attach click event listener to edit icons
document.addEventListener('click', function(event) {
    var target = event.target;
    if (target.classList.contains('edit-icon')) {
        editCompany(target);
    }
});






// Function to redirect to placed_students.jsp
function redirectToStudentsDetails() {
    window.location.href = "students_details.jsp";
}
