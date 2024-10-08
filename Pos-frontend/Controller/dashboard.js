$(document).ready(function() {

    // Assuming you have your revenue, expense, and sales data in variables
    // named revenueData, expenseData, and salesData (replace with your actual data)
    const revenueData = [1000, 1500, 2000, 1800, 2500];
    const expenseData = [800, 1200, 1100, 1300, 1000];
    const salesData = [1200, 1800, 2200, 2000, 2800];

    // Chart configuration for revenue chart
    const revenueChartConfig = {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May'],
            datasets: [{
                label: 'Revenue',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                data: revenueData,
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    };

    // Create revenue chart
    const revenueCtx = $('#revenueChart');
    const revenueChart = new Chart(revenueCtx, revenueChartConfig);

    // Similar chart configurations and creation for expense and sales charts
    const expenseChartConfig = {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May'],
            datasets: [{
                label: 'Expenses',
                backgroundColor: 'rgba(246,209,160,0.68)',
                borderColor: 'rgba(229,199,124,0.9)',
                data: expenseData,
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    };

    const expenseCtx = $('#expensesChart');
    const expenseChart = new Chart(expenseCtx, expenseChartConfig);

    const salesChartConfig = {
        type: 'line',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May'],
            datasets: [{
                label: 'Sales',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                data: salesData,
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    };

    const salesCtx = $('#saleChart');
    const salesChart = new Chart(salesCtx, salesChartConfig);

});
