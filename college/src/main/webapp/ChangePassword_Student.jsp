<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <link rel="stylesheet" href="change_password.css">
</head>
<body>

<div id="changePasswordModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <h2>Change Password</h2>
    <form action="ChangePasswordStudent" method="post">
      <input type="text" name="username" placeholder="Username" required>
      <input type="password" name="oldPassword" placeholder="Old Password" required>
      <input type="password" name="newPassword" placeholder="New Password" required>
      <button type="submit">Change Password</button>
    </form>
  </div>
</div>

<script>
// Function to open the change password modal
function openChangePasswordModal() 
{
  var modal = document.getElementById('changePasswordModal');
  modal.style.display = "block";
}

// Function to close the change password modal
function closeChangePasswordModal() {
  var modal = document.getElementById('changePasswordModal');
  modal.style.display = "none";
}

// Close the modal when the close button (×) is clicked
var closeBtn = document.getElementsByClassName("close")[0];
closeBtn.onclick = function() {
  closeChangePasswordModal();
};

// Close the modal when the user clicks anywhere outside of the modal
window.onclick = function(event) {
  var modal = document.getElementById('changePasswordModal');
  if (event.target == modal) {
    closeChangePasswordModal();
  }
};
</script>

</body>
</html>
