$(document).ready(function() {
    // Existing functionality for "test-purpose" dropdown and other UI interactions
    document.getElementById("test-purpose").addEventListener("change", function() {
        var otherPurposeInput = document.getElementById("other-purpose");
        if (this.value === "others") {
            otherPurposeInput.style.display = "block";
            otherPurposeInput.setAttribute("name", "otherPurposeTest");
        } else {
            otherPurposeInput.style.display = "none";
            otherPurposeInput.removeAttribute("name");
        }
    });

    document.getElementById('culture').addEventListener('change', function() {
        const cultureOptions = document.getElementById('culture-options');
        cultureOptions.style.display = this.checked ? 'inline' : 'none';
        if (!this.checked) {
            cultureOptions.value = ""; // Reset the value if unchecked
        }
    });

    document.getElementById('others-para').addEventListener('change', function() {
        const specifyInput = document.getElementById('specify-para');
        specifyInput.style.display = this.checked ? 'inline' : 'none';
        if (!this.checked) {
            specifyInput.value = ""; // Reset the value if unchecked
        }
    });

    // Radio button logic for "Releasing Results" section
    document.querySelectorAll("input[name='releasingResults']").forEach(function(radio) {
        radio.addEventListener("change", function() {
            var regionalOfficeInput = document.getElementById("regional-office");
            if (this.value === "regional") {
                regionalOfficeInput.style.display = "block";
                regionalOfficeInput.setAttribute("name", "regionalOffice");
            } else {
                regionalOfficeInput.style.display = "none";
                regionalOfficeInput.removeAttribute("name");
            }
        });
    });

    // Sample section: Add new sample inputs
    document.getElementById('addSampleButton').addEventListener('click', function() {
        var newSampleDiv = document.createElement('div');
        newSampleDiv.className = 'form-section';

        var newLabel = document.createElement('label');
        newLabel.textContent = 'Sample Description and Details:';

        var newTextarea = document.createElement('textarea');
        newTextarea.name = 'sampleDetails';
        newTextarea.id = 'sampleDescription' + (document.querySelectorAll('textarea[name="sampleDetails"]').length + 1); // Unique ID

        newSampleDiv.appendChild(newLabel);
        newSampleDiv.appendChild(newTextarea);

        document.getElementById('additionalSamplesContainer').appendChild(newSampleDiv);
    });

    // Sample section: Remove last sample input
    document.getElementById('deleteSampleButton').addEventListener('click', function() {
        var container = document.getElementById('additionalSamplesContainer');
        var samples = container.children;

        if (samples.length > 0) {
            container.removeChild(samples[samples.length - 1]);
        }
    });

    // New Code: Category radio button functionality for monitoring/walk-in
    const categoryRadios = document.querySelectorAll('input[name="sample_category"]');
    const orNoContainer = document.getElementById('orNoContainer');
    const addSampleButton = document.getElementById('addSampleButton');
    const deleteSampleButton = document.getElementById('deleteSampleButton');
    const sampleInputs = document.querySelectorAll('#clientSampleCode, #sampleDescription, #sampleSource');

    // Handle the display logic based on selected category
    function handleCategoryChange() {
        // Reset all fields
        resetFormFields();
        
        if (document.getElementById('monitoring').checked) {
            orNoContainer.style.display = 'none'; // Hide OR Number field
            sampleInputs.forEach(input => input.removeAttribute('disabled')); // Enable sample inputs
            addSampleButton.style.display = 'inline'; // Show Add button
            deleteSampleButton.style.display = 'inline'; // Show Delete button
            addSampleButton.disabled = false; // Enable Add button
            deleteSampleButton.disabled = false; // Enable Delete button
        } else if (document.getElementById('walk-in').checked) {
            orNoContainer.style.display = 'block'; // Show OR Number field
            sampleInputs.forEach(input => input.removeAttribute('disabled')); // Enable initial sample fields
            addSampleButton.style.display = 'none'; // Hide Add button
            deleteSampleButton.style.display = 'none'; // Hide Delete button
            addSampleButton.disabled = true; // Disable Add button
            deleteSampleButton.disabled = true; // Disable Delete button
        }
    }

    // Function to reset all form fields
    function resetFormFields() {
        // Clear specific fields (not autofilled ones)
        const manualInputs = document.querySelectorAll('input[type="text"]:not([readonly]), input[type="number"], input[type="date"], textarea');
        manualInputs.forEach(input => {
            input.value = ''; // Clear input values
        });

        // Clear additional samples
        const additionalSamplesContainer = document.getElementById('additionalSamplesContainer');
        while (additionalSamplesContainer && additionalSamplesContainer.firstChild) {
            additionalSamplesContainer.removeChild(additionalSamplesContainer.firstChild);
        }

        // Check and Clear OR No. field
        const orNoField = document.getElementById('orNo');
        if (orNoField) {
            orNoField.value = '';
        } else {
            console.log("Element with ID 'orNo' not found."); // Debug log
        }

        // Check and Clear LD Control Number field
        const ldNoField = document.getElementById('ldNo');
        if (ldNoField) {
            ldNoField.value = '';
        } else {
            console.log("Element with ID 'ldNo' not found."); // Debug log
        }

        // Reset examination section checkbox inputs
        const checkboxItems = document.querySelectorAll('.checkbox-item input[type="checkbox"]');
        checkboxItems.forEach(checkbox => {
            checkbox.checked = false; // Uncheck each checkbox
        });

        // Reset culture dropdown
        const cultureDropdown = document.getElementById('culture-options');
        if (cultureDropdown) {
            cultureDropdown.style.display = 'none'; // Hide culture options
            cultureDropdown.selectedIndex = 0; // Reset to default
        } else {
            console.log("Element with ID 'culture-options' not found."); // Debug log
        }

        // Reset certification checkbox
        const certifyCheckbox = document.getElementById('certify');
        if (certifyCheckbox) {
            certifyCheckbox.checked = false; // Uncheck certification checkbox
        } else {
            console.log("Element with ID 'certify' not found."); // Debug log
        }

        // Clear selected radio buttons in the releasing results section
        const releasingResultsRadios = document.querySelectorAll("input[name='releasingResults']");
        releasingResultsRadios.forEach(radio => {
            radio.checked = false; // Uncheck each radio button
        });

        // Reset the regional office input
        const regionalOfficeInput = document.getElementById("regional-office");
        if (regionalOfficeInput) {
            regionalOfficeInput.value = ''; // Clear regional office input
            regionalOfficeInput.style.display = 'none'; // Hide regional office input
        } else {
            console.log("Element with ID 'regional-office' not found."); // Debug log
        }

        // Reset the dropdown for Purpose of Laboratory Test
        const purposeDropdown = document.getElementById("test-purpose");
        if (purposeDropdown) {
            purposeDropdown.selectedIndex = 0; // Reset to default option
        } else {
            console.log("Element with ID 'test-purpose' not found."); // Debug log
        }

        const otherPurposeInput = document.getElementById("other-purpose");
        if (otherPurposeInput) {
            otherPurposeInput.style.display = "none"; // Hide the other-purpose input
        } else {
            console.log("Element with ID 'other-purpose' not found."); // Debug log
        }

        // Reset "Others" checkbox and its input field
        const othersCheckbox = document.getElementById("others-para");
        if (othersCheckbox) {
            othersCheckbox.checked = false; // Uncheck the Others checkbox
        } else {
            console.log("Element with ID 'others-para' not found."); // Debug log
        }

        const specifyParaInput = document.getElementById("specify-para");
        if (specifyParaInput) {
            specifyParaInput.value = ''; // Clear specify input field
            specifyParaInput.style.display = 'none'; // Hide the specify input field
        } else {
            console.log("Element with ID 'specify-para' not found."); // Debug log
        }
    }


    // Attach event listeners to all category radio inputs for monitoring/walk-in
    categoryRadios.forEach(radio => {
        radio.addEventListener('change', handleCategoryChange);
    });

    $(document).ready(function() {
        // Get elements
        const reviewSubmitBtn = $('#reviewSubmit');
        const reviewPopup = $('#reviewPopup');
        const closePopupBtn = $('.close-popup');
        const reviewContent = $('#reviewContent');
        const confirmSubmitBtn = $('#confirmSubmit');
        const labRequestForm = $('form');

        // Function to populate the popup with form data
        function populateReviewContent() {
            const formData = labRequestForm.serializeArray();
            let contentHtml = `
    <div class="pdf-header">
        <h2>Laboratory Request Form Review</h2>
    </div>
    <hr>
    <div class="pdf-section">
        <h3>Client Information</h3>
        <table class="pdf-table">
            <tbody>`;

            // Add client-related fields
            formData.forEach(item => {
                if (["companyName", "representativeName", "email", "contactNumber", "ltoNo", "clientClassif", "address"].includes(item.name)) {
                    const formattedName = item.name
                        .replace(/([A-Z])/g, ' $1')
                        .replace(/^./, str => str.toUpperCase());
                    contentHtml += `<tr><td class="pdf-field">${formattedName}</td><td class="pdf-value">${item.value || 'N/A'}</td></tr>`;
                }
            });

            contentHtml += `
            </tbody>
        </table>
    </div>

    <div class="pdf-section">
        <h3>Sample Details</h3>
        <table class="pdf-table">
            <tbody>`;

            // Add sample-related fields
            formData.forEach(item => {
                if (["clientSampleCode", "sampleDetails", "sampleSource", "productionDate", "expirationDate", "samplingDate", "weightGrams", "purposeTest", "microbioTests", "molecTests", "chemTests"].includes(item.name)) {
                    const formattedName = item.name
                        .replace(/([A-Z])/g, ' $1')
                        .replace(/^./, str => str.toUpperCase());
                    contentHtml += `<tr><td class="pdf-field">${formattedName}</td><td class="pdf-value">${item.value || 'N/A'}</td></tr>`;
                }
            });

            contentHtml += `
            </tbody>
        </table>
    </div>

    <div class="pdf-section">
        <h3>Additional Information</h3>
        <table class="pdf-table">
            <tbody>`;

            // Add additional fields
            formData.forEach(item => {
                if (["orNo", "releasingResults", "regionalOffice", "sample_category"].includes(item.name)) {
                    const formattedName = item.name
                        .replace(/([A-Z])/g, ' $1')
                        .replace(/^./, str => str.toUpperCase());
                    contentHtml += `<tr><td class="pdf-field">${formattedName}</td><td class="pdf-value">${item.value || 'N/A'}</td></tr>`;
                }
            });

            contentHtml += `
            </tbody>
        </table>
    </div>
    `;

            reviewContent.html(contentHtml);
        }


        // Open the popup
        reviewSubmitBtn.on('click', function(event) {
            event.preventDefault(); // Prevent form submission
            populateReviewContent();
            reviewPopup.fadeIn(); // Use fadeIn for a smooth display effect
        });

        // Close the popup when clicking the close button
        closePopupBtn.on('click', function() {
            reviewPopup.fadeOut(); // Use fadeOut for a smoother hide effect
        });

        // Handle the confirm submission button inside the popup
        confirmSubmitBtn.on('click', function() {
            reviewPopup.fadeOut(); // Close the popup before form submission
            labRequestForm.submit(); // Submit the form
        });

        // Function to generate PDF from the review content
        function generatePDF() {
            // Set options for the PDF
            const options = {
                margin: 0.5,
                filename: 'request-form-review.pdf',
                image: { type: 'jpeg', quality: 0.98 },
                html2canvas: { scale: 2 },
                jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
            };

            // Use html2pdf to generate the PDF from the reviewContent element
            html2pdf().set(options).from(reviewContent[0]).save();
        }

        // Add event listener for the Download PDF button (add this button in your HTML)
        $('#downloadPdfBtn').on('click', function() {
            generatePDF();
        });
    });

    document.getElementById('weight').addEventListener('input', function (e) {
        const weightInput = this.value;
        const weightError = document.getElementById('weightError');

        console.log('Weight input changed:', weightInput);  // Log the input value

        // Check if the input contains invalid characters (non-numeric)
        if (isNaN(weightInput) || weightInput === "") {
            console.log('Invalid input detected');  // Log when invalid input is found
            weightError.style.display = 'inline';  // Show the error message
        } else {
            console.log('Valid input detected');  // Log when valid input is found
            weightError.style.display = 'none';  // Hide the error message
        }
    });
});