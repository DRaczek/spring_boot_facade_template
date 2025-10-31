# 🧩 Spring Boot Facade Template

## 📖 Project Overview

**Spring Boot Facade Template** is a starter project for building modular Spring Boot applications using the **Facade architectural pattern**.  
It provides a clean, extensible structure with separated domain modules, unified configuration, and reusable components — ideal as a **base template for new projects**.

---

## 🏗️ Architecture

The project follows a **facade-based domain architecture**, organizing business logic into domain modules such as `user`, `auth`, `role`, `address`, etc. 

```
src/main/java/draczek/facadetemplate/
├── auth/ # no auth endpoints regarding authentication process
├── user/ # User and account management, web security configuration, refresh token logic
├── role/ # Role management 
├── address/ # Address handling
├── configuration/ # Some configuration files
├── common/ # commonly used classes
├── userActionToken/ # verification tokens : account activation, password reset etc.
├── infrastructure.security/ # Security facade for fetching authenticated user's data 
```

Each domain module typically contains:
- `command`
  - `Facade` - unified entry point for the module; orchestrates use cases and exposes operations to controllers
  - `Config` - creates and configures the facade bean along with its dependencies
  - `Use cases` - core business logic for write operations (create, update, delete)
  - `Validation Helpers` - helper classes and methods for validating input data before processing
  - `Repositories` - Spring Data repositories for persisting and querying entities
  - `Mappers` - classes that map usually between entities and DTOs typically with MapStruct
  - `Specifications` - Custom JPA specifications done with `specification-arg-resolver` for easier data filtering
  - `Entity` - JPA entities representing the database tables
- `dto` – Data transfer objects related to the facade
- `enumerated` – Enumerated types
- `exception` – Custom exceptions

---

## 🧠 What Is the Facade Pattern?

The **facade pattern** provides a simplified interface to complex subsystems.

In this template:
- Each domain module exposes a single **Facade class** (`UserFacade`, `AuthFacade`, etc.)
- Controllers communicate **only with facades**, not directly with services or repositories
- This keeps the **presentation layer lightweight** and decoupled from domain logic

✅ Benefits:
- Clear separation of concerns  
- Easier testing and maintenance  
- High reusability and modularity  

---

## ⚙️ Technologies

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA / Hibernate**
- **Spring Security (JWT-based)**
- **Liquibase** – database migrations
- **PostgreSQL**
- **Spock Framework(Groovy)** – sample integration testing
- **Lombok**, **MapStruct**, **specification-arg-resolver**
- SwaggerUI

---

## 🧪 Testing

### This template includes:
- Integration tests (Spock) – samples for future reference

---

## 🔑 JWT Authentication

### Authentication and authorization are handled via JWT tokens.

Endpoints:
- POST `/api/auth/login` – generate token
- POST `/api/auth/refresh_token` – refresh token
- Authorization: Bearer <token> – access secured endpoints

---

## 🧱 Database Migrations

### Database schema changes are managed with Liquibase.

Changelogs are located in:
- `src/main/resources/db/changelog/`

> [!NOTE]
> 🧱 This project serves as a **base template for building Spring Boot applications** using a clean **facade-based architecture**, domain-driven modules, and best development practices to my knowledge
