
# POS  API(Spring version)

This project is a backend API for a Point of Sale (POS) system. It allows you to manage items, customers, orders, and users. The API is built using Java EE with a MySQL database for data persistence.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [API Documentation](#api-documentation)
- [Database Structure](#database-structure)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Management**: Create, update, delete, and authenticate users.
- **Item Management**: Add, update, and delete items, with images uploaded via form data type.
- **Customer Management**: Add, update, and delete customers.
- **Order Management**: Create, view, and delete orders.

## Technologies

- **Java EE**
- **Spring**
- **MySQL**
- **Hibernate**
- **Spring Data JPA**
- **Log4j for logging**

## Setup

1. Clone the repository
2. Set up the MySQL database and configure the application properties for database connection.
3. Run the Spring  application.

## API Documentation

For detailed API documentation, you can visit the link below:

[API Documentation](https://documenter.getpostman.com/view/36195888/2sAXxTcB6o)

## Database Structure

The system includes the following main entities:

- **User**: Stores user information and credentials.
- **Item**: Stores information about the items for sale, including their base64 image.
- **Customer**: Stores customer data.
- **Order**: Contains details of customer and orders .
- **OrderItems**: Contains details of  orders and associated items.

## Usage

- **User Management**: Manage users (create, update, delete, login).
- **Items**: Add, view, update, and delete items with an image upload.
- **Customers**: Manage customer data.
- **Orders**: Create and view customer orders.

## Contributing

Feel free to contribute to this project by submitting a pull request or opening an issue.

## License

This project is licensed under the MIT License.
