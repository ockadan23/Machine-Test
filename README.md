### README for Product Management System

---

## Project Overview

The **Product Management System** is a Spring Boot application designed to manage products and sales. It provides RESTful APIs for managing products, tracking sales, and generating reports. The application also includes features like role-based access control, PDF generation, and Swagger documentation.

---

## Features

1. **Product Management**:
   - Add, update, delete, and view products.
   - View total revenue and revenue by product.

2. **Sales Management**:
   - Record, update, delete, and view sales.
   - View sales by product.

3. **PDF Generation**:
   - Generate a PDF report of all products and their revenue.

4. **Role-Based Access Control**:
   - Admins can access all APIs (GET, POST, PUT, DELETE).
   - Users can access only GET and POST APIs.

5. **Swagger Documentation**:
   - Interactive API documentation available at `/swagger-ui.html`.

6. **Global Exception Handling**:
   - Centralized error handling for consistent API responses.

---

## Technologies Used

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: MySQL
- **PDF Generation**: iTextPDF
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Testing**: JUnit 5, Mockito
- **Build Tool**: Maven

---

## Prerequisites

1. **Java**: JDK 21 or higher.
2. **MySQL**: Ensure MySQL is installed and running.
3. **Maven**: Ensure Maven is installed or use the provided Maven wrapper (mvnw).

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd product
```

### 2. Configure the Database
Update the database configuration in application.properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_db
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Build the Project
Use the Maven wrapper to build the project:
```bash
mvn clean install
```

### 4. Run the Application
Start the application using:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

---

## API Endpoints

### Product APIs
| Method | Endpoint                  | Description                     | Role Required |
|--------|---------------------------|---------------------------------|---------------|
| GET    | `/api/products`           | Get all products                | USER, ADMIN   |
| GET    | `/api/products/{id}`      | Get product by ID               | USER, ADMIN   |
| POST   | `/api/products`           | Add a new product               | ADMIN         |
| PUT    | `/api/products/{id}`      | Update an existing product      | ADMIN         |
| DELETE | `/api/products/{id}`      | Delete a product                | ADMIN         |
| GET    | `/api/products/revenue/total` | Get total revenue             | USER, ADMIN   |
| GET    | `/api/products/revenue/{productId}` | Get revenue by product ID | USER, ADMIN   |
| GET    | `/api/products/download/pdf` | Download product table as PDF | USER, ADMIN   |

### Sales APIs
| Method | Endpoint                  | Description                     | Role Required |
|--------|---------------------------|---------------------------------|---------------|
| GET    | `/api/sales`              | Get all sales                   | USER, ADMIN   |
| GET    | `/api/sales/product/{id}` | Get sales by product ID         | USER, ADMIN   |
| POST   | `/api/sales`              | Record a new sale               | ADMIN         |
| PUT    | `/api/sales/{id}`         | Update an existing sale         | ADMIN         |
| DELETE | `/api/sales/{id}`         | Delete a sale                   | ADMIN         |

---


### Product Management
- **URL**: `http://localhost:8080/products`
- Features:
  - View all products.
  - Add, edit, and delete products.
  - View revenue details.

### Sales Management
- **URL**: `http://localhost:8080/sales`
- Features:
  - View all sales.
  - Add, edit, and delete sales.

---

## Swagger Documentation

- **URL**: `http://localhost:8080/swagger-ui.html`
- Provides an interactive interface to test all APIs.

---

## Testing

### Run Unit Tests
```bash
mvn test
```

### Test Coverage
- Controllers: `ProductController`, `SaleController`
- Services: `ProductServiceImpl`, `SaleServiceImpl`

---

## Security

- **Authentication**: Basic authentication.
- **Roles**:
  - `ADMIN`: Full access to all APIs.
  - `USER`: Limited access (GET and POST APIs only).
- **IP Filtering**: Only requests from `127.0.0.1` and `0:0:0:0:0:0:0:1` are allowed.

---

## PDF Generation

- **Endpoint**: `/api/products/download/pdf`
- Generates a PDF report of all products.

---

## Logging

- Logs are configured using SLF4J.
- Log levels can be adjusted in application.properties:
```properties
logging.level.org.springframework=INFO
logging.level.com.example=DEBUG
```

---

## Future Enhancements

1. Add user registration and JWT-based authentication.
2. Implement advanced filtering and sorting for products and sales.
3. Add support for exporting sales data as CSV.
4. 4. Integrate Thymeleaf

---

## License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.

---

Let me know if you need further assistance!
