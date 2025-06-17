
# To-Do List REST API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-blue?logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.5-green?logo=spring-boot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql&logoColor=white" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/Docker-blue?logo=docker&logoColor=white" alt="Docker"/>
  <img src="https://img.shields.io/badge/JWT_Auth-black?logo=jsonwebtokens&logoColor=white" alt="JWT"/>
</p>

## Overview

This project is a secure, robust, and scalable **RESTful API** designed for a modern to-do list application.  
It was built from the ground up using **Java 21 and Spring Boot 3**, demonstrating a deep understanding of backend development   
principles required for professional, enterprise-level services.

The primary goal of this project is to showcase a mastery of key backend concepts, including:
- **Clean API Architecture**: Implementing a decoupled, maintainable, and scalable system design.

- **Modern Security Practices**: Securing an application with stateless, token-based authentication (JWT).

- **Data Persistence & Management**: Integrating with a relational database and managing data lifecycles effectively.

- **Professional Tooling**: Leveraging industry-standard tools for builds, containerization, and development efficiency.

---

## Architecture & Engineering Principles
This API was engineered with a professional, clean architecture to ensure maintainability and scalability.

#### Layered Architecture
The application follows a strict **separation of concerns** into three distinct layers:
1. **Controller (Presentation Layer)**: Handles all HTTP requests, response formatting, and validation. It is intentionally "thin," delegating all business logic to the service layer.

2. **Service (Business Logic Layer)**: Contains the core application logic, orchestrates data operations, and enforces business rules and security checks.

3. **Repository (Data Access Layer)**: Manages all communication with the database using Spring Data JPA, completely abstracting persistence logic from the rest of the application.

#### Stateless Authentication with JWT
To ensure scalability and reliability, the API is completely stateless. 
Authentication is handled using JSON Web Tokens (JWT). 
The server does not store session information; instead, a signed JWT is issued upon successful login. 
The client then includes this token in the header of all subsequent requests, allowing the server to verify authenticity without a session store.

#### Decoupled API Contract with DTOs
JPA entities are never exposed directly to the client. 
The API's public contract is defined by a layer of Data Transfer Objects (DTOs). 
This critical pattern decouples the API from the internal database schema, 
enhances security by preventing data leaks (e.g., hashed passwords), 
and avoids common issues related to lazy loading during JSON serialization.

#### Centralized Exception Handling
A global exception handler `(@ControllerAdvice)` intercepts all exceptions thrown from the controllers. 
ensures that clients always receive consistent, structured, and informative JSON error responses
without exposing internal server stack traces, 
which is a key security and usability best practice.

---

## Key Features
- Full User Authentication with secure password hashing via BCrypt.

- Token-Based Security using JWT to protect all sensitive endpoints.

- Complete CRUD Operations for managing user-specific to-do items.

- Data Ownership enforcement to ensure users can only access their own data.

- Declarative Server-Side Validation for all user inputs using Jakarta Bean Validation.

- Efficient Data Retrieval with built-in Pagination and Sorting.

## Tech Stack & Tools
| Category            | Technology / Tool                                                                                                                                                                                           |
| ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Backend Framework** | ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?logo=spring-boot&logoColor=white)                                                                                                           |
| **Language** | ![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk&logoColor=white)                                                                                                                   |
| **Security** | ![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-green?logo=spring-security&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-black?logo=jsonwebtokens&logoColor=white)                   |
| **Database** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql&logoColor=white) ![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-green?logo=spring&logoColor=white)                         |
| **Development Tools** | ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-red?logo=apache-maven&logoColor=white) ![Lombok](https://img.shields.io/badge/Lombok-purple?logo=lombok&logoColor=white) |

---

## Local Development Setup
Follow these steps to get the application running on your local machine.

#### Prerequisites
- Java Development Kit (JDK) 21+
- Apache Maven
- Docker
- An API Client like Postman

1. Clone the Repository
    ```bash
    git clone 
    cd todolist-api
    ```
   
2. Run PostgreSQL in Docker
   A Docker container is used to run the database for a clean and isolated environment.
   This is all written on one line, I just wrote it like this for presentation
   ```bash
   docker run --name todolist-postgres-container
   -e POSTGRES_PASSWORD=todolistapipassword
   -e POSTGRES_USER=todolist_user
   -e POSTGRES_DB=todolist_db
   -p 5432:5432
   -d postgres
   ```

3. Configure the Application
   The main configuration is located in `src/main/resources/application.properties`.
   **Important**: Generate a new, secure 512-bit Base64 key for `app.jwtSecret`. Do not use the placeholder key in a production environment.

4. Build and Run the Application
   ```bash
   mvn spring-boot:run
   ```
   The API will start up be available at `http://localhost:8080`.

---

## API Endpoints
All endpoints are prefixed with `/api`. Authorization requiers a *Bearer Token* in the `Authorization` header

#### Authentication
| Method | Endpoint         | Description                        | Sample Body (`application/json`)                                               |
| :----- |:-----------------|:-----------------------------------|:-------------------------------------------------------------------------------|
| `POST` | `/auth/register` | Registers a new user.              | `{"username": "test", "email": "test@example.com", "password": "password123"}` |
| `POST` | `/auth/login`    | Logins in a user and returns a JWT | `{"username": "test", "password": "password123"}`                              |

#### To-Do Items
| Method  | Endpoint      | Description                                  | Sample Body (`application/json`)                        |
|:--------|:--------------|:---------------------------------------------|:--------------------------------------------------------|
| `GET`   | `/todos`      | Gets a paginated list of todos for the user. | (None)                                                  |
| `POST`  | `/todos`      | Creates a new to-do for the user.            | `{"title": "My New Task", "description": "Details..."}` |
| `GET`   | `/todos/{id}` | Gets a signle to-do by its ID.               | (None)                                                  |
| `PUT`   | `/todos/{id}` | Updates an existing to-do.                   | `{"title": "Updated Title", "completed": true}`         |

## License 
This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

Uphile Ntuli - uphilentuli43@gmail.com

Project Link: 
