## Spring Boot with Actuator and Global Exception Handle

### Description
Spring Boot Actuator Project with Validation and Global Exception Handle

## Features!
* How to use Spring Boot Actuator
* How to configure Spring Security to work with Spring Boot Actuator
* Way to use Spring Data JPA to interact with MySQL Database
* How to create and delete data using Spring Boot
* How to use Lombok to get rid of boilerplate code
* How to handle global exceptions in Spring Boot
* How to configure Spring Security to work with Spring Boot Actuator

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
* Git

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

[![Collection](https://run.pstmn.io/button.svg)](https://github.com/shakhawatmollah/spring-boot-actuator/blob/main/notes/spring-boot-actuator.postman_collection.json)