



$(document).ready(function () {


    $("#addCustomer").click(function () {
        var customerId = $("#id").val().trim();
        var customerName = $("#fName").val().trim();
        var customerContact = $("#Contact").val().trim();
        var customerAddress = $("#Address").val().trim();
        var customerNote = $("#Note").val().trim();

        //check if all the input fields are filled
        if (!customerId || !customerName || !customerContact || !customerAddress || !customerNote) {
            alert("Please fill in all fields.");
            return;
        }

        //check customer id
        if (!customerId){
            $("#errorText").text("Customer ID is required")
            $("#errorModal").modal("show");
            return;
        } else if (!/^C\d{3}$/.test(customerId)) {
            $("#errorText").text("Customer ID must be in the format 'C001'.")
            $("#errorModal").modal("show");
            return;
        }
        // Check if customer name contains numbers
        if (!customerName){
            $("#errorText").text("Customer name is required")
            $("#errorModal").modal("show");
            return;
        }
        if (/\d/.test(customerName)) {
            $("#errorText").text("Customer name cannot contain numbers.")
            $("#errorModal").modal("show");
            return;
        }

        //check customer Address
        if (!customerAddress){
            $("#errorText").text("Customer address is required");
            $("#errorModal").modal("show");
            return;
        }

        // Check contact format (e.g., 10-digit number)
        if ( !customerContact){
            $("#errorText").text("Customer contact is required")
            $("#errorModal").modal("show");
            return;
        }
       else if (!/^\d{10}$/.test(customerContact)) {
            $("#errorText").text("Invalid contact number format. Please enter a 10-digit number.")
            $("#errorModal").modal("show");
            return;
        }


        let customer = {
            id: customerId,
            name: customerName,
            contact: customerContact,
            address: customerAddress,
            note: customerNote
        };
        $.ajax({
            url: "http://localhost:8080/posbackend/customer",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(customer),
            success: function(response) {
                $("#text").text("Successfully added new customer")
                $("#successModal").modal("show");
                console.log(response);
            },
            error: function(xhr, status, error) {
                console.error("Error saving customer:", xhr.responseText);
                alert("Failed to save customer. Please try again.");
            }
        });

        loadTable();


        resetInputFields();
    });

    function resetInputFields() {
        $(".info-section input, .info-section textarea").val(""); // Set value to empty string
    }
    function loadTable(){
        $("#customerTable").empty();
        $.ajax({
            url: "http://localhost:8080/posbackend/customer", // Adjust the URL if needed
            type: "GET",
            contentType: "application/json",// The type of data you're expecting back from the server
            success: function(customers) {
                // Clear the existing table rows
                $("#customerTable").empty();

                // Iterate through the customers array and append each one to the table
                customers.forEach((item, index) => {
                    let newRow = `
                    <tr>
                        <td class="id">${item.id}</td>
                        <td class="name">${item.name}</td>
                        <td class="contact">${item.contact}</td>
                        <td class="address">${item.address}</td>
                        <td class="note">${item.note}</td>
                        <td>
                            <button class="btn btn-danger btn-sm" ><i class="fa-solid fa-trash"></i></button>
                            <button class="btn btn-warning btn-sm" ><i class="fa-solid fa-pen-to-square"></i></button>
                        </td>
                    </tr>
                `;
                    $("#customerTable").append(newRow);
                });
            },
            error: function(xhr, status, error) {
                console.error("Error fetching customers:", xhr.responseText);
                alert("Failed to load customers. Please try again later.");
            }
        });
    }


    var recordIndex;
    var editCustomer;

    $("#customerTable").on("click","tr",function (){
        let index = $(this).index();
        recordIndex = index;
        let id =$(this).find(".id").text();
        let name =$(this).find(".name").text();
        let contact = $(this).find(".contact").text();
        let address =$(this).find(".address").text();
        let note = $(this).find(".note").text()

        $("#id").val(id);
        $("#fName").val(name);
        $("#Contact").val(contact);
        $("#Address").val(address);
        $("#Note").val(note);
       /* editCustomer = new CustomerModel(id,name,contact,address,note);*/
        editCustomer={
            id:id,
            name:name,
            contact:contact,
            address:address,
            note:note
        }
    });
    $("#editCustomer").click(function (){
        $("#idModal").val(editCustomer.id);
        $("#fNameModal").val(editCustomer.name);
        $("#ContactModal").val(editCustomer.contact);
        $("#AddressModal").val(editCustomer.address);
        $("#NoteModal").val(editCustomer.note);
    });

    $("#saveChanges").click(function (){
        var idModalValue = $("#idModal").val();
        var fNameModalValue = $("#fNameModal").val();
        var ContactModalValue = $("#ContactModal").val();
        var AddressModalValue = $("#AddressModal").val();
        var NoteModalValue = $("#NoteModal").val();
       let customer={
           id:idModalValue,
           name:fNameModalValue,
           contact:ContactModalValue,
           address:AddressModalValue,
           note:NoteModalValue
       }


        $.ajax({
            url: "http://localhost:8080/posbackend/customer",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(customer),
            success: function (response) {
                console.log(response);
                $("#editModal").modal("hide");
                $("#successModal").modal("show");
                // Refresh the table or perform other UI updates here
                loadTable();
                resetInputFields();
            },
            error: function (xhr, status, error) {
                console.error("Failed to update customer:", error);
                alert("Failed to update customer");
            }
        });

    });

    $("#okBtn").click(function (){
        $("#successModal").modal("hide");
    });


    $("#confirmDelete").click(function (){
        let id =$("#id").val();
        console.log(id)
       /* customerArray.splice(recordIndex,1);*/
        $.ajax({
            url: `http://localhost:8080/posbackend/customer?id=${id}`,
            type: 'DELETE',
            success: function (response) {
                console.log("Customer deleted successfully");

                // Refresh the table or perform other UI updates here
                loadTable();
                $("#customerDeleteModal").modal("hide");
                resetInputFields();
                $("#text").text("Successfully Deleted a customer")
                $("#successModal").modal("show");
            },
            error: function (xhr, status, error) {
                console.error("Failed to delete customer:", error);
                alert("Failed to delete customer");
            }
        });

    });

    //---search customer---

    // Trigger search on button click
    $("#customerSearch").click(function(e) {
        performSearch();
    });

    // Trigger search on Enter key press
    $("#customerInput").keypress(function(e) {
        if (e.which === 13) { // Enter key is pressed
            e.preventDefault(); // Prevent form refresh
            performSearch();
        }
    });

    function performSearch() {
        var customerId = $("#customerInput").val().trim();


        if (!customerId) {
            $("#errorText").text("Please enter a customer ID to search.");
            $("#errorModal").modal("show");
            return;
        }
        searchCustomer(customerId);
    }

    function searchCustomer(customerId) {

        console.log(customerId)
        $.ajax({
            url: `http://localhost:8080/posbackend/customer?id=${customerId}`,
            type: 'GET',
            success: function (response) {
                if (response) {
                    displayFilteredCustomer([response]); // Pass the response to display the customer
                } else {
                    $("#errorText").text("No Customer Data found for the given customer ID.");
                    $("#errorModal").modal("show");
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status === 404) {
                    $("#errorText").text("No Customer Data found for the given customer ID.");
                } else {
                    $("#errorText").text("An error occurred while fetching customer data.");
                }
                $("#errorModal").modal("show");
                console.error("Failed to retrieve customer:", error);
            }
        });



    }

    function displayFilteredCustomer(filterCustomer) {
         filterCustomer.forEach(customer=>{
             $("#id").val(customer.id);
             $("#fName").val(customer.name);
             $("#Contact").val(customer.contact);
             $("#Address").val(customer.address);
             $("#Note").val(customer.note);
         })

    }

    loadTable();





});

