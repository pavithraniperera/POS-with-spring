
import {loadItemTable} from "./AddedItem.js"



export function loadOrderTable(){
    $("#orderTable").empty();

    var orders=[];
    $.ajax({
        url: 'http://localhost:8080/posbackend/order',
        type: 'GET',
        success: function(response) {
            $("#orderTable").empty();
            orders =response;
            console.log(response)
            orders.forEach((item) => {
                var newRow = `
                    <tr>
                        <td class="id">${item.orderId}</td>
                        <td class="custId">${item.customerId}</td>
                        <td class="custName">${item.customerName}</td>
                        <td class="total">${item.total}</td>
                        <td class="date">${item.date}</td>
                        <td>
                            <button class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i></button>
                            <button class="btn btn-warning btn-sm"><i class="fa-solid fa-pen-to-square"></i></button>
                        </td>
                    </tr>
                `;
                $("#orderTable").append(newRow);
            });
        },
        error: function(xhr, status, error) {
            console.error("Failed to retrieve orders:", error);
        }
    });
}

 $(document).ready(function (){
    $(document).on("click", ".item-button", function () {
        console.log("clicked");
        var itemName = $(this).closest(".card-body").find(".card-title").text();
        var itemImageSrc = $(this).closest(".item-card").find(".card-img-top").attr("src");
        var itemWeight = $(this).closest(".item-card").find(".form-select").val();
        var itemPriceText = $(this).closest(".item-card").find(".card-text").text();
        var itemPrice = parseFloat(itemPriceText.replace("Price: Rs.", ""));
        var item;
        var maxQuantity;
        $.ajax({
            url: `http://localhost:8080/posbackend/item?name=${itemName}`,
            type: 'GET',
            success: function (response) {
                if (response) {
                   item=response;
                     maxQuantity = item.quantity;
                   console.log(response)
                } else {
                   console.log("No data found")
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status === 404) {
                    $("#errorText").text("No Item Data found for the given Item name.");
                } else {
                    $("#errorText").text("An error occurred while fetching item data.");
                }
                $("#errorModal").modal("show");
                console.error("Failed to retrieve item:", error);
            }
        });



        //check if the item is already in the shopping cart
        let itemExist =false;
        $(".cart-item").each(function (){
            var existingItemName =$(this).find(".item-name").text();
            console.log(existingItemName);

            if (existingItemName===itemName){
                itemExist = true;
                return false;
            }
        });
        console.log(itemExist)
        if (itemExist){

            showAlert(`This Item is already in your cart .  Increased quantity of ${itemName}.`);
        }else {
            //check item availability
            if (maxQuantity<=0){
                 showAlert("Item out of stock.");
                return;
            }


            var newItem = `
    <div class="cart-item">
        <img src="${itemImageSrc}" alt="Item Image" class="item-image">
        <span class="item-name">${itemName}</span>
        <input type="number" class="form-control quantity-input" value="1" min="1" max="${maxQuantity}">
        <button id="cartDelete" class="delete-item-button"><i class="fas fa-trash"></i></button>
        <span class="item-price">Rs. ${itemPrice.toFixed(2)}</span>
    </div>
`;
            $(".shopping-cart").append(newItem);
            updateTotalPrice();
        }


    });

     function showAlert(message) {
         $("#alertMessage").text(message);
         var alertElement = $("#notificationAlert");

         alertElement.show();

         // Auto-hide after 5 seconds (5000 milliseconds)
         setTimeout(function() {
             alertElement.addClass('hide').removeClass('show');
             setTimeout(function() {
                 alertElement.hide();
             }, 300);
         }, 5000);
     }
    function updateTotalPrice(){
        var totalPrice =0;
        $(".cart-item").each(function() {
            var itemPriceText = $(this).find(".item-price").text();
            var itemPrice = parseFloat(itemPriceText.replace("Rs.", ""));
            var quantity = $(this).find(".quantity-input").val();
            totalPrice += itemPrice * quantity;
        });

        $(".total-price").text("Total: Rs." + totalPrice.toFixed(2));
    }
    $(document).on("change", ".quantity-input", function() {

        var quantity = $(this).val();
        var maxQuantity = $(this).attr(`max`);
        if (parseInt(quantity) === parseInt(maxQuantity)) {
            $(this).val(maxQuantity);
            showAlert("Cannot exceed available stock quantity in this item.");
        }
        updateTotalPrice();
    });

    $(".checkout-btn").on("click", function() {



    });
     var items =[];
     var totalPrice = 0;

    $("#proceed").click(function() {

        //check there are at least one item in the cart
        if ($(".cart-item").length === 0) {
            alert("Your shopping cart is empty. Please add items to the cart before proceeding to checkout.");
            $("#checkoutModal").modal("hide");
            return;
        }
        alert("Proceeding to checkout. Total amount: " + $(".total-price").text());
        // Get all cart items
        var cartItems = $(".cart-item");


        $("#checkoutModal").modal("show");


        // Loop through each cart item to calculate total price and display item details
        cartItems.each(function(index, item) {
            var itemName = $(item).find(".item-name").text();
            var itemQuantity = parseInt($(item).find(".quantity-input").val());
            var itemPriceText = $(item).find(".item-price").text();
            var itemPrice = parseFloat(itemPriceText.replace("Rs. ", ""));
            var itemId;


            //  item subtotal
            var itemSubtotal = itemPrice * itemQuantity;



            //  total price
            totalPrice += itemSubtotal;
            $.ajax({
                url: `http://localhost:8080/posbackend/item?name=${itemName}`,
                type: 'GET',
                success: function (response) {
                    console.log(response);
                    if (response) {
                       itemId = response.id;
                        console.log(itemId)


                        let itemObject = {
                            id:itemId,
                            name:itemName,
                            quantity:itemQuantity,

                        }
                        console.log(itemId)
                        items.push(itemObject);

                    }
                },
                error: function (xhr, status, error) {
                    console.error("Failed to retrieve item:", error);
                }
            });

        });

        console.log("Total Price:", totalPrice);
     /*   console.log(proceedItems);*/
        $("#payTotal").text(totalPrice);
        setTotalAmount(totalPrice);
        setCustomerId();
    });



     $("#pay").click(function () {
         var date = getCurrentTime();

         var customerId ;
         $("#customerId").change(function() {
              customerId = $(this).val();
             console.log("Selected Customer ID:", customerId);
         });
         console.log(customerId)
         console.log(orderId);



         // Send the order data to the backend via AJAX

         $.ajax({
             url: 'http://localhost:8080/posbackend/orderHistory',
             type: 'GET',
             success: function(response) {
                 var lastOrderId = response.lastOrderId || null; // Handle null if no order exists
                 var orderId = generateNextOrderId(lastOrderId);
                 console.log("Generated Order ID:", orderId);

                 let orderDto = {
                     orderId: orderId,
                     customerId: selectedCustomerId,
                     itemDtoList: items,
                     date: date,
                     total: parseFloat($("#payment").text())
                 };
                 console.log(orderDto);

                 // Send the order data to the backend via AJAX
                 $.ajax({
                     url: 'http://localhost:8080/posbackend/order',
                     type: 'POST',
                     contentType: 'application/json',
                     data: JSON.stringify(orderDto),
                     success: function (response) {
                         // Handle the response (order saved successfully)
                         console.log("Order saved successfully:", response);

                         // Reload the item and order tables
                         loadItemTable();
                         loadOrderTable();

                         // Clear proceedItems and cart UI
                         clearCart();

                         // Hide checkout modal and show success message
                         $("#checkoutModal").modal("hide");
                         $("#text").text("Order Successful");
                         $("#successModal").modal("show");
                     },
                     error: function (xhr, status, error) {
                         // Handle errors
                         console.error("Error saving order:", error);
                         $("#text").text("Order Failed. Please try again.");
                         $("#successModal").modal("show");
                     }
                 });
             },
             error: function (xhr, status, error) {
                 console.error("Error retrieving last order ID:", error);
                 $("#text").text("Unable to process order. Please try again.");
                 $("#successModal").modal("show");
             }
         });




     });

    $("#okBtn").click(function (){
        $("#successModal").modal("hide");
    });

     function clearCart() {
         $(".cart-item").remove();
         // Or clear specific elements that represent the cart items
     }

    function getCurrentTime() {
        return new Date(); // Get current date as a string
    }
    var orderNumber=1;
    function generateOrderId() {
        var nextOrderId;
        $.ajax({
            url: 'http://localhost:8080/posbackend/orderHistory',  // Update the URL as needed
            type: 'GET',
            success: function(response) {
                if (response.lastOrderId) {
                    console.log(response);
                    console.log(response.lastOrderId)
                    // Generate the next order ID
                     nextOrderId = generateNextOrderId(response.lastOrderId);



                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to retrieve last order ID:", error);
            }
        });
        return nextOrderId;
    }
     function generateNextOrderId(lastOrderId) {
         if (!lastOrderId) {
             // Start from O001 if there are no orders in the database
             return "O001";
         }

         // Extract the numeric part from the lastOrderId (e.g., "O005" -> "005")
         let numericPart = parseInt(lastOrderId.substring(1));

         // Increment the numeric part
         numericPart++;

         // Format the new order ID (e.g., "O006")
         return "O" + numericPart.toString().padStart(3, '0');
     }

     var discountedTotal;
     function setTotalAmount(total){
         console.log(total);
         var discount = total * 0.15;
          discountedTotal = total - (total * 0.15); // 15% discount
         $("#discount").text(discount.toFixed(2));
         $("#payment").text(discountedTotal.toFixed(2));

     }

     function setCustomerId(){
         // Find the select element by its ID
         const customerIdSelect = document.getElementById("customerId");

        // Clear any existing options
         customerIdSelect.innerHTML = "";

        // Create a default option
         const defaultOption = document.createElement("option");
         defaultOption.value = "";
         defaultOption.textContent = "Select Customer ID";
         defaultOption.disabled = true;
         defaultOption.selected = true;

         // Append the default option to the select element
         customerIdSelect.appendChild(defaultOption);
          var customers=[];
         $.ajax({
             url: `http://localhost:8080/posbackend/customer`,
             type: 'GET',
             success: function (response) {
                 if (response) {
                     customers=response;
                     console.log(customers);
                     customers.forEach(customer => {
                         const option = document.createElement("option");

                         option.textContent = customer.id;

                         customerIdSelect.appendChild(option);
                     });
                 } else {
                     console.log("No customers to display")
                 }
             },
             error: function (xhr, status, error) {

                 $("#errorModal").modal("show");
                 console.error("Failed to retrieve customer:", error);
             }
         });


     }

    const customerIdSelect = document.getElementById("customerId");
    const custNameInput = document.getElementById("custName");
    var selectedCustomerId ;
    var selectedCustomer;


    customerIdSelect.addEventListener("change", function() {
         selectedCustomerId = this.value; // Get customer iD

        $.ajax({
            url: `http://localhost:8080/posbackend/customer?id=${selectedCustomerId}`,
            type: 'GET',
            success: function (response) {
                if (response) {
                    selectedCustomer=response;
                    if (selectedCustomer) {
                        custNameInput.value = selectedCustomer.name;
                    } else {
                        custNameInput.value = ""; // Clear  input field if no customer is selected
                    }
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



    });







      //delete function
     $(document).on('click', '.delete-item-button', function () {
         // Get the parent cart-item div
         let cartItemDiv = $(this).closest('.cart-item');

         // Remove the item
         cartItemDiv.remove();
         showAlert("Item is Removed" );

         updateTotalPrice();
     });








 });