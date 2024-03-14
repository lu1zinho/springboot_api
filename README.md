# Voll Med

Simple Doctor/Patient RESTful API
- CRUD (GET, POST, PUT, DELETE)
- Pagination/Sorting
- Error Handling
- JWT authentication
- YAML Configuration
- Bean Validation
- JPQL Query
- API Documentation in HTML format (Swagger UI)
- API Documentation in JSON Format (OpenAPI 3)
- Repositories Integration Test
- API Unit Testing using Spring's MockMvc
- Production profile

## Technologies

- Spring Boot 3
    - Spring Initializr
    - Spring Data JPA
    - Spring Web
    - Spring Security
    - Repositories
- Maven
- Hibernate Validator
- Lombok
- MySQL
- Flyway (DB migration)
- Auth0
- Springdoc

## Getting started

To run the package with production profile:
```
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost/vollmed_api -DDATASOURCE_USERNAME=username -DDATASOURCE_PASSWORD=password -jar api-0.0.1-SNAPSHOT.jar
```

