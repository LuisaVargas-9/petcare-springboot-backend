# 🐾 PetCare — Spring Boot Backend

REST API for **PetCare**, a Full Stack platform developed collaboratively during the **Certified Tech Developer program at Digital House**.

This repository contains the backend of the project, responsible for authentication, business logic, persistence, reservations, service management, pet-related data, file uploads, and email notifications.

> The frontend is maintained in a separate React repository.

---

## 🚀 About the Project

PetCare is a web platform focused on pet care services. The backend exposes REST endpoints consumed by the React frontend and follows a layered Spring Boot architecture.

The application includes public and protected resources, JWT-based authentication, role-based authorization, relational persistence with MySQL, file uploads to AWS S3, and OpenAPI/Swagger documentation.

---

## ✨ Main Features

### 🔐 Authentication & Security

- User registration and login
- JWT-based authentication
- Stateless Spring Security configuration
- BCrypt password hashing
- Role-based authorization
- Protected endpoints for `ADMIN` and `CLIENTE` roles
- Configurable CORS support

### 🐾 PetCare Domain

The API includes resources for:

- Services
- Categories
- Service characteristics
- Users
- Pets
- Species
- Reservations
- Favorites
- Orders and order details

### 📅 Reservations

- Create and manage reservations
- Retrieve reservations by user
- Filter reservations by status
- Retrieve confirmed reservation dates for services
- Associate reservations with authenticated users and services

### ☁️ Integrations

- AWS S3 file uploads
- Email notifications through Spring Mail
- Swagger / OpenAPI API documentation

---

## 🛠️ Tech Stack

### Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)

### Data & Persistence

![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=flat-square&logo=hibernate&logoColor=white)

### Cloud & API

![AWS](https://img.shields.io/badge/AWS-S3-232F3E?style=flat-square&logo=amazonwebservices&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)

Additional technologies and libraries include:

- Spring Data JPA
- JWT
- Lombok
- Hibernate Validator
- Jackson
- Spring Mail
- JUnit

---

## 🏗️ Architecture

The project follows a layered backend structure:

```text
HTTP Request
     │
     ▼
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
JPA / Hibernate
     │
     ▼
MySQL
```

Main package organization:

```text
src/main/java/com/petcare/backend/proyectoIntegrador/
├── config/       # Security, JWT, AWS S3 and Swagger configuration
├── controller/   # REST controllers
├── DTO/          # Request and response objects
├── entity/       # JPA entities
├── repository/   # Data access layer
├── service/      # Business logic
└── util/         # Utility classes
```

---

## 🔐 Authentication Flow

The API uses JWT authentication with Spring Security.

```text
Client
  │
  │ Login / Register
  ▼
Auth Controller
  │
  ▼
Authentication Service
  │
  ▼
JWT Token
  │
  ▼
Authorization: Bearer <token>
  │
  ▼
Protected API Resources
```

The security configuration distinguishes between public resources and endpoints restricted to authenticated users or administrators.

---

## ⚙️ Environment Configuration

The repository includes a `.env.example` file documenting the environment variables required by the application.

Create a local `.env` file or configure the equivalent environment variables:

```env
DB_HOST=localhost
DB_NAME=petcare_db
DB_USER=<YOUR_DB_USER>
DB_PASSWORD=<YOUR_DB_PASSWORD>

JWT_SECRET=<YOUR_JWT_SECRET>

AWS_ACCESS_KEY_ID=<YOUR_AWS_ACCESS_KEY_ID>
AWS_SECRET_ACCESS_KEY=<YOUR_AWS_SECRET_ACCESS_KEY>

MAIL_USERNAME=<YOUR_MAIL_USERNAME>
MAIL_PASSWORD=<YOUR_MAIL_PASSWORD>
```

> Never commit real credentials or secrets to the repository.

---

## 💻 Running the Project Locally

### Requirements

- Java 21
- MySQL 8+
- Maven or the included Maven Wrapper

### 1. Clone the repository

```bash
git clone https://github.com/LuisaVargas-9/petcare-springboot-backend.git
```

### 2. Enter the project directory

```bash
cd petcare-springboot-backend
```

### 3. Configure environment variables

Use `.env.example` as a reference and configure the required values for your environment.

### 4. Run the application

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API runs by default on:

```text
http://localhost:8080
```

---

## 📚 API Documentation

The project includes Swagger/OpenAPI integration through Springdoc.

When the application is running, the Swagger UI is available through the application's Swagger endpoint, allowing the REST API to be explored and tested interactively.

---

## 🔗 Frontend

The frontend was developed with React and Vite.

👉 [View PetCare React Frontend](https://github.com/LuisaVargas-9/petcare-react-frontend)

---

## 👥 Project Context

PetCare was developed collaboratively as part of the **Certified Tech Developer program at Digital House**.

The project involved teamwork across frontend development, backend development, REST API integration, database persistence, authentication, cloud integrations, and deployment-related tasks using Git and GitHub.

### My Participation

I participated as a member of the development team. This repository is included in my portfolio to showcase the technologies, backend architecture, Full Stack workflow, and collaborative development experience involved in the project.

**Luisa Vargas**  
Full Stack Developer

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Luisa_Vargas-0A66C2?style=flat-square&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/luisa-vargas-233494200/)

---

## 📌 Repository Status

This repository is maintained primarily as part of my **software development portfolio** and represents one of the collaborative Full Stack projects completed during my professional developer training.
