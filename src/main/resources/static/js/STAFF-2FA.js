window.onload = function() {
    const errorMessage = document.getElementById("errorMessage").value; // Using a hidden input to pass error
    if (errorMessage) {
        alert(errorMessage);
        // Redirect to STAFF login page after the alert
        window.location.href = "/STAFF-login"; // Adjust the URL as needed
    }
};
