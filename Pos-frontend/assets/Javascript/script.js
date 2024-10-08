

var css1 = {
    display:"none"
}
    var css2 ={
    display: "block",

}


   function handleNavClick(clickedElementId){

       switch (clickedElementId) {
           case "nav-home":
               showSection("homeSection");
               break;
           case "nav-customer":
               showSection("customerSection");
               break;
           case "cust-nav":
               showSection("customerSection");
               break;
           case "nav-item":
               showSection("itemSection");
               break;

           case "nav-order":
               showSection("orderSection");
               break;

           case "nav-profile":
               showSection("profileSection");
               break;

           case "addedItem":
               showSection("addedItemSection");
               break;

           case "nav-report":
               showSection("ReportSection");

       }
   }
   function showSection(sectionId){
       $("#homeSection").css(css1);
       $("#customerSection").css(css1);
       $("#addedItemSection").css(css1);
       $("#itemSection").css(css1);
       $("#profileSection").css(css1);
       $("#ReportSection").css(css1);
       $("#orderSection").css(css1);
       $(`#${sectionId}`).css(css2);
   }

    $("#nav-item").click(function () {
        handleNavClick($(this).attr("id"));
    });
    $("#cust-nav").click(function () {
    handleNavClick($(this).attr("id"));
    $("#checkoutModal").modal("hide");
});

    $("#nav-home").click(function () {
        handleNavClick($(this).attr("id"));
    });
    $("#nav-report").click(function () {
        handleNavClick($(this).attr("id"));
    });
    $("#nav-profile").click(function () {
        handleNavClick($(this).attr("id"));
    });
    $("#nav-order").click(function () {
        handleNavClick($(this).attr("id"));
    });
    $("#nav-customer").click(function () {
        handleNavClick($(this).attr("id"));
    });

    $("#addedItem").click(function () {
        handleNavClick($(this).attr("id"));
    });
    $(document).ready(function() { // This function runs when the document is ready
      /*  handleNavClick("nav-home");*/
        $("#dashboard").css(css1);
        $("body").css({
            paddingTop:0
        })
    });



    $("#logOut").click(function (){
        $("#dashboard").css(css1);
        $("#login").css(css2);
        $("body").css({
            paddingTop:0
        })
    })

function handleSignIn(users) {

    const usernameValue = $('#usernameModal').val().trim();
    if (!usernameValue) {
        alert('Please enter your username.');
        return;
    }

    // Validate password
    const passwordValue = $('#passwordModal').val().trim();
    if (!passwordValue) {
        alert('Please enter your password.');
        return;
    }
    // Find user by username
    const foundUser = users.find(user => user.userName === usernameValue);

    // Validate username
    if (!usernameValue || !foundUser) {
        alert('Invalid username.');
        return;
    }

    // Validate password
    if (foundUser.password !== passwordValue) {
        alert('Incorrect password.');
        return;
    }

        $("#login").css(css1)
        $("#dashboard").css(css2);
        handleNavClick("nav-home");
        $("body").css({
            paddingTop:150
        })

    $("#text").text("Login Successful")
    $("#successModal").modal("show");





}

$(document).ready(function () {
        // Add event listener to the form for form submission
        $("#signIn").click(function () {
            $.ajax({
                    url: "http://localhost:8080/posbackend/user",
                    type: "GET",
                    contentType: "application/json",
                success: function(response) {
                        console.log(response)
                    handleSignIn(response);
                },
                error: function(xhr, status, error) {
                    alert("Error fetching users: " + xhr.responseText);
                }
                }
            )
            $("#login").css(css1)
            $("#dashboard").css(css2);
            handleNavClick("nav-home");
            $("body").css({
                paddingTop:150
            })

            $("#text").text("Login Successful")
            $("#successModal").modal("show");
        })
    });



