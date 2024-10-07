## Spring Boot with Actuator and Global Exception Handle

### Description
This is a Spring Boot application that provides a RESTful API for managing employees and departments. It supports CRUD operations, validation, and includes a set of automated tests. The project is designed to be scalable and maintainable, with a clean architecture and clear separation of concerns.

## Features!
* CRUD operations for Employees and Departments
* Data validation using JSR-303 annotations
* Exception handling with custom exceptions
* Integration with Spring Actuator for monitoring and management
* GitHub Actions for Continuous Integration (CI)
* How to configure Spring Security to work with Spring Boot Actuator
* Way to use Spring Data JPA to interact with MySQL Database
* How to use Lombok to get rid of boilerplate code

### Technology
* JDK 17 or later
* Spring 6.0.8
* Spring JDBC 6.0.8
* Spring Boot 3.3.4
* Spring Boot Actuator 3.0.4
* Spring Security 6.0.2
* Maven 3.0 or later
* MySQL Database
* Lombok-1.18.26
* Mockito for unit testing
* Git

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17 or later installed on your machine
- Maven installed on your machine
- Git installed on your machine

## Getting Started

### Clone the repository

```bash
git clone https://github.com/shakhawatmollah/spring-boot-actuator
cd spring-boot-actuator
```

### Build the project

```bash
mvn clean install
```

### Run the application

You can run the application using the following command:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Employees

- **GET /api/employees**: Retrieve a list of all employees
- **GET /api/employees/{id}**: Retrieve an employee by ID
- **POST /api/employees**: Create a new employee
- **PUT /api/employees/{id}**: Update an existing employee
- **DELETE /api/employees/{id}**: Delete an employee

### Departments

- **GET /api/departments**: Retrieve a list of all departments
- **GET /api/departments/{id}**: Retrieve a department by ID
- **POST /api/departments**: Create a new department
- **PUT /api/departments/{id}**: Update an existing department
- **DELETE /api/departments/{id}**: Delete a department

## Running Tests

To run the tests, use the following command:

```bash
mvn test
```

### **Actuator Endpoints**  `http://localhost:8080/mgt-details`

### **Actuator Secure Access**
**`requestMatchers("/mgt-details/**").hasRole("ADMIN")`**
- Ensures that only users with the `ADMIN` role can access actuator endpoints.

### **Users Access Details**  
- `admin/admin` Role: ADMIN
- `user/user` Role: USER

### **Example CURL request**
  ```sh
    curl -X POST -u admin:admin http://localhost:8080/api/departments -d '{"shortName": "FIN", "name": "Finance Department"}' -H "Content-Type: application/json"
  ```

### Postman Collection

Import Postman collection to quickly get started.

[![Collection](https://run.pstmn.io/button.svg)](https://github.com/shakhawatmollah/spring-boot-actuator/blob/main/notes/spring-bbot-actuator.postman_collection.json)

## GitHub Actions

This project uses GitHub Actions for Continuous Integration. The workflow is defined in `.github/workflows/ci.yml`. It will automatically run tests and build the project on each push or pull request to the main branch.

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please fork the repository and create a pull request.

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [JUnit](https://junit.org/junit5/)
