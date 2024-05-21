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
    xhr.open("POST", "MoveStudentsToPlacedServlet", true);
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

// Function to redirect to placed_students.jsp
function redirectToPlacedStudents() {
    window.location.href = "placed_students.jsp";
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
    xhr.open("POST", "DeleteStudentsServlet", true);
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



// Function to redirect to placed_students.jsp
function redirectToPlacedStudents() {
    window.location.href = "placed_students.jsp";
}

