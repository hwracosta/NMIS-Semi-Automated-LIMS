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

    document.getElementById('others-para').addEventListener('change', function() {
        const specifyInput = document.getElementById('specify-para');
        if (this.checked) {
            specifyInput.style.display = 'inline'; // Show the specify input field
        } else {
            specifyInput.style.display = 'none'; // Hide the specify input field
            specifyInput.value = ""; // Reset the input if unchecked
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
        
        function formatTestName(testName) {
            // Replace underscores and hyphens with spaces, then capitalize the first letter of each word
            return testName
                .replace(/[_]/g, ' ') // Replace underscores and hyphens with spaces
                .replace(/\b\w/g, char => char.toUpperCase()); // Capitalize the first letter of each word
        } 
        
        // Function to populate the popup with form data
        function populateReviewContent() {
            const formData = labRequestForm.serializeArray();
            let contentHtml = `
            <div class="pdf-header">
                <h2>Laboratory Request Form Review</h2>
            </div>
            <hr>
            <div class="pdf-section" id="client-info-section">
                <h3>Client Information</h3>
                <table class="pdf-table">
                    <tbody>`;
        
            // Add client-related fields
            formData.forEach(item => {
                if (["certify"].includes(item.name)) return; // Skip the certify field
                
                // Only process non-test fields in this loop
                if (!["microbioTests", "molecTests", "chemTests", "sampleDetails", "sample_category", "clientSampleCode", 
                    "sampleSource", "productionDate", "weightGrams", "expirationDate", "samplingDate", "otherMicrobioTests", "regional-office"   
                 ].includes(item.name)) {
                    const formattedName = item.name
                        .replace(/([A-Z])/g, ' $1')
                        .replace(/^./, str => str.toUpperCase());
                    contentHtml += `<tr><td class="pdf-field">${formattedName}</td><td class="pdf-value">${item.value || 'N/A'}</td></tr>`;
                }
            });
        
            contentHtml += `
                    </tbody>
                </table>
            </div>`;
        
            // Group and display sample information
            const sampleDetails = formData
                .filter(item => item.name === "sampleDetails") 
                .map(item => item.value)                       
                .join(", "); // Join sample details as a comma-separated string
        
            const sampleCategory = formData.find(item => item.name === "sample_category")?.value || 'N/A';
            const clientSampleCode = formData.find(item => item.name === "clientSampleCode")?.value || 'N/A';
            const sampleSource = formData.find(item => item.name === "sampleSource")?.value || 'N/A';
            const productionDate = formData.find(item => item.name === "productionDate")?.value || 'N/A';
            const weightGrams = formData.find(item => item.name === "weightGrams")?.value || 'N/A';
            const expirationDate = formData.find(item => item.name === "expirationDate")?.value || 'N/A';
            const samplingDate = formData.find(item => item.name === "samplingDate")?.value || 'N/A';
            const regionalOffice = formData.find(item => item.name === "regional-office")?.value || 'N/A';
        
            contentHtml += `
            <div class="pdf-section" id="sample-info-section">
                <h3>Sample Information</h3>
                <table class="pdf-table">
                    <tbody>
                        <tr><td class="pdf-field">Sample Details</td><td class="pdf-value">${sampleDetails || 'N/A'}</td></tr>
                        <tr><td class="pdf-field">Sample Category</td><td class="pdf-value">${sampleCategory}</td></tr>
                        <tr><td class="pdf-field">Client Sample Code</td><td class="pdf-value">${clientSampleCode}</td></tr>
                        <tr><td class="pdf-field">Sample Source</td><td class="pdf-value">${sampleSource}</td></tr>
                        <tr><td class="pdf-field">Production Date</td><td class="pdf-value">${productionDate}</td></tr>
                        <tr><td class="pdf-field">Weight (Grams)</td><td class="pdf-value">${weightGrams}</td></tr>
                        <tr><td class="pdf-field">Expiration Date</td><td class="pdf-value">${expirationDate}</td></tr>
                        <tr><td class="pdf-field">Sampling Date</td><td class="pdf-value">${samplingDate}</td></tr>
                        <tr><td class="pdf-field">Regional Office</td><td class="pdf-value">${regionalOffice}</td></tr>
                    </tbody>
                </table>
            </div>`;
        
            contentHtml += `
            <div class="pdf-section" id="test-requests-section">
                <h3>Test/s Requested</h3>
                <table class="pdf-table">
                    <tbody>`;
        
            // Group and display test fields
            const testCategories = {
                microbioTests: [],
                molecTests: [],
                chemTests: []
            };

            formData.forEach(item => {
                if (item.name === "microbioTests") {
                    testCategories.microbioTests.push(item.value); // Add selected microbioTests
                } else if (item.name === "otherMicrobioTests" && item.value) {
                    testCategories.microbioTests.push(item.value); // Add otherMicrobioTests value directly
                } else if (item.name === "others-para" && item.checked) {
                    // If 'others-para' checkbox is checked, include the value from 'specify-para'
                    const specifyParaValue = document.getElementById('specify-para').value;
                    if (specifyParaValue) {
                        testCategories.microbioTests.push(specifyParaValue); // Push the specify-para input value directly
                    } else {
                        // Optionally handle the case where specify-para is empty, if needed
                        testCategories.microbioTests.push('No input for others'); // Fallback if no input
                    }
                } else if (testCategories[item.name]) {
                    testCategories[item.name].push(item.value);
                }
            });
            
            // Combine all microbio tests (including "otherMicrobioTests") into one row
            const microbioTestList = testCategories.microbioTests.join(', ');
            
            // Add microbioTests row to the content
            if (microbioTestList.length > 0) {
                contentHtml += `<tr><td class="pdf-field">Microbio Tests</td><td class="pdf-value">${microbioTestList}</td></tr>`;
            }            
                      

            // Continue adding molecTests and chemTests as usual
            Object.keys(testCategories).forEach(category => {
                if (testCategories[category].length > 0 && category !== "microbioTests") {
                    const categoryName = category
                        .replace(/([A-Z])/g, ' $1')
                        .replace(/^./, str => str.toUpperCase())
                        .replace('Tests', ' Tests');
                    const testList = testCategories[category].join(', ');
                    contentHtml += `<tr><td class="pdf-field">${categoryName}</td><td class="pdf-value">${testList}</td></tr>`;
                }
            });

        
            contentHtml += `
                    </tbody>
                </table>
            </div>`;
        
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

        confirmSubmitBtn.on('click', function() {
            // Validate required fields
            const purposeTest = $('#test-purpose').val();
            const releasingResults = $('input[name="releasingResults"]:checked').val();
            const weightGrams = $('#weight').val();
            const isCertifyChecked = $('#certify').is(':checked'); // Check if the certify checkbox is checked
            
            // Check if at least one test is selected
            const isTestSelected = $('input[name="microbioTests"]:checked').length > 0 ||
                                   $('input[name="molecTests"]:checked').length > 0 ||
                                   $('input[name="chemTests"]:checked').length > 0;
        
            if (!purposeTest || !releasingResults || !weightGrams || !isTestSelected || !isCertifyChecked) {
                alert("Please fill out all required fields, select at least one test, and ensure the certification is checked.");
            } else {
                reviewPopup.fadeOut(); // Close the popup before form submission
                labRequestForm.submit(); // Submit the form
            }
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