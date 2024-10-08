
import { loadOrderTable } from './Item.js'
var recordIndex;

$(document).ready(function() {
    loadOrderTable(); // Load orders when the page is ready
});

$("#orderTable").on("click","tr",function (){
    let index = $(this).index();
    recordIndex = index;
    let orderId =$(this).find(".id").text();
    let custId =$(this).find(".custId").text();
    let total = $(this).find(".total").text();
    let date =$(this).find(".date").text();
    let name =$(this).find(".custName").text();
    $("#customerName").val(name);
    $("#orderId").val(orderId);
    $("#orderDate").val(date);
    $("#orderTotal").text(total);
   // let proceedItemsArray = getProceedItemsArray(orderId);
    /*console.log(proceedItemsArray);*/
    $.ajax({
        url: `http://localhost:8080/posbackend/order?id=${orderId}`,
        type: 'GET',
        success: function (response) {
            // Assuming the response is an OrderDTO object containing order details and items array
            let order = response;

            // Set the order details in the form
            $("#customerName").val(name);
            $("#orderId").val(orderId);
            $("#orderDate").val(date);
            $("#orderTotal").text(total);
            console.log(order.itemDtoList)
            // Load the items into the table
            loadTable(order.itemDtoList);
        },
        error: function (xhr, status, error) {
            console.error("Failed to retrieve order details:", error);
        }
    });
    //loadTable(proceedItemsArray);


});



function loadTable(itemsArray){
    $("#orderItemList").empty();
    itemsArray.map((item, index) => {
        var total = item.quantity * item.price;
        var newRow = `
            <tr>
                <td class="id">${item.name}</td>
                <td class="custId">${item.quantity}</td>
                <td class="castName">${item.price}</td>
                <td class="total">${total}</td>
            </tr>
        `;
        $("#orderItemList").append(newRow);
    });
}
$("#clearFields").click(function (){
    clearFields();
});



function clearFields() {
    // Clear input fields
    $("#customerName").val("");
    $("#orderId").val("");
    $("#orderDate").val("");

    // Clear orderItemListTable
    $("#orderItemList").empty();
}
$("#deleteBtn").click(function (){
    let orderId =   $("#orderId").val();
    console.log(orderId)
    $.ajax({
        url: `http://localhost:8080/posbackend/order?id=${orderId}`,
        type: 'DELETE',
        success: function (response) {
            console.log("order deleted successfully");

            // Refresh the table or perform other UI updates here
            loadOrderTable();
            $("#deleteModal").modal("hide");
            clearFields();
            $("#text").text("Successfully Deleted a Order");
            $("#successModal").modal("show");
        },
        error: function (xhr, status, error) {
            console.error("Failed to delete customer:", error);
            alert("Failed to delete customer");
        }
    });

})

function displayFilteredOrders(filteredOrders) {
    console.log(filteredOrders)
    console.log(Array.isArray(filteredOrders))
    $("#filteredOrdersTable").empty();
    filteredOrders.map(order => {
        var newRow = `
            <tr>
                <td class="orderId">${order.orderId}</td>
                <td class="custId">${order.customerId}</td>
                <td class="CustName">${order.customerName}</td>
                <td class="total">${order.total}</td>
                <td class="date">${order.date.toLocaleString()}</td>
            </tr>
        `;
        $("#filteredOrdersTable").append(newRow);
    });

    $("#filteredOrdersModal").modal("show");

}
var orderTableIndex;
$("#filteredOrdersTable").on("click","tr",function (){
    let index = $(this).index();
    orderTableIndex = index;
    let orderId = $(this).find(".orderId").text();
    let custId = $(this).find(".custId").text();
    let total = $(this).find(".total").text();
    let date = $(this).find(".date").text();
    let name = $(this).find(".custName").text();
    $("#customerName").val(name);
    $("#orderId").val(orderId);
    $("#orderDate").val(date);
    $("#orderTotal").text(total);

    
})


function searchOrders(customerId) {
    let filteredOrders = [];
    $.ajax({
        url: `http://localhost:8080/posbackend/orderHistory?id=${customerId}`,
        type: 'GET',
        success: function (response) {
           filteredOrders = response;
           console.log(response)
            if (filteredOrders.length === 0) {
                $("#errorText").text("No orders found for the given customer ID.")
                $("#errorModal").modal("show");
                return
            }
            displayFilteredOrders(filteredOrders);

        },
        error: function (xhr, status, error) {
            console.error("Failed to retrieve order details:", error);
        }
    });




}

$("#searchButton").click( function (e) {
    performSearch();

});
$("#searchCustomerId").keypress(function(e) {
    if (e.which === 13) { // Enter key is pressed
        e.preventDefault(); // Prevent form refresh
        performSearch();
    }
});

function performSearch() {
    var customerId = $("#searchCustomerId").val().trim();

    if (!customerId) {
        $("#errorText").text("Please enter a customer ID to search.");
        $("#errorModal").modal("show");
        return;
    }
    searchOrders(customerId);
}

