document.addEventListener('DOMContentLoaded', function() {
    const selectElement = document.getElementById('test-purpose');
    const otherInput = document.getElementById('other-purpose');

    selectElement.addEventListener('change', function() {
        if (selectElement.value === 'others') {
            otherInput.style.display = 'block'; // Show the input field
        } else {
            otherInput.style.display = 'none'; // Hide the input field
            otherInput.value = ''; // Clear the input field when hidden
        }
    });
});

