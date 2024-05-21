function deleteSelected() {
    var selectedCompanyIds = [];
    var checkboxes = document.querySelectorAll('input[name="selectedCompanies"]:checked');
    
    checkboxes.forEach(function(checkbox) {
        selectedCompanyIds.push(checkbox.value);
    });

    if (selectedCompanyIds.length === 0) {
        alert("Please select at least one company.");
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "DeleteCompany", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Remove selected company rows from the table
                checkboxes.forEach(function(checkbox) {
                    checkbox.closest("tr").remove();
                });
            } else {
                console.error("Error deleting companies:", xhr.responseText);
                alert("Error deleting companies. Please try again.");
            }
        }
    };
    
    // Send selected company IDs to the servlet
    xhr.send(JSON.stringify(selectedCompanyIds));
}

function redirectToAdminPanel() {
    window.location.href = "admin_panel.jsp";
}
