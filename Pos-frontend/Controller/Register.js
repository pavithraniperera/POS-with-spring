
$(document).ready(function (){
    $("#registerBtn").click(function (){
        const usernameValue = $('#username').val().trim();
        const passwordValue = $('#password').val().trim();
        const confirmPasswordValue = $('#confirmPassword').val().trim();

        // Validate username
        if (!usernameValue) {
            alert('Please enter a username.');
            return;
        }

        // Validate password length and character content
        if (passwordValue.length < 8 || !/[a-zA-Z]/.test(passwordValue)) {
            alert('Password should have at least 8 characters and contain at least one alphabetic character.');
            return;
        }

        // Validate password confirmation
        if (passwordValue !== confirmPasswordValue) {
            alert('Password and Confirm Password do not match.');
            return;
        }
        let user ={
            userName : usernameValue,
            password : passwordValue

        }
        $.ajax({
            url: "http://localhost:8080/posbackend/user",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function(response){
                alert("User registered successfully!");
                $("#registerModal").modal("hide"); // Hide the modal
            },
            error: function(xhr, status, error) {
                alert("Error: " + xhr.responseText); // Display the error message
            }
        });




    });
})

