# 📚 Library Management System REST API

A modular Library Management System developed using **Spring Boot**. It follows clean architecture principles and includes layers like Controller, Service, Repository, DTOs, Mappers, and coustom exception handling for some exceptions.

---

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **H2/MySQL/PostgreSQL** (pluggable)
- **Maven**
- **Lombok**
- **MapStruct** (for DTO mapping)
- **Spring Validation**
---

## 📁 Project Structure

```
src/
 └── main/
     └── java/
         └── com.racoonash.library/
             ├── Configuration/       # Config classes (e.g., Swagger, Security)
             ├── controller/          # REST Controllers
             ├── DTO/                 # Data Transfer Objects
             ├── Entity/              # JPA Entities (Book, User, etc.)
             ├── Enum/                # Enums for role, status, etc.
             ├── Exceptions/          # Custom Exceptions and Handlers
             ├── Mappers/             # MapStruct Interfaces for DTO ↔ Entity
             ├── Repository/          # Spring Data JPA Repositories
             ├── Response/            # Standardized API response models
             ├── Service/             # Business Logic Services
             └── LibrarymanagementApplication.java # Main class
```

---

## 🚀 Features

- ✅ Add, update, delete, and list books
- ✅ Issue and return books
- ✅ Manage readers
- ✅ DTO-Entity mapping using **MapStruct**
- ✅ Centralized exception handling
- ✅ Validation using **Spring Validator**
- ✅ RESTful API design
- ✅ Modular structure for scalability

---

## 🔧 Setup & Run

### Prerequisites
- Java 17+
- Maven
- (Optional) MySQL/PostgreSQL for production

### Run Locally
```bash
# Clone the project
git clone https://github.com/your-username/library-management-system.git
cd library-management-system

# Build and run
mvn clean install
mvn spring-boot:run
```

### API Docs

API documentation and schema are available in Swagger UI.
Once running, access Swagger UI at:  
`http://localhost:8080/swagger-ui/index.html`



## 📄 Example Entities

### Book
```json
{
  "bookId": 0,
  "bookName": "string",
  "author": "string",
  "isbn": "string",
  "publishDate": "2025-07-07T15:32:36.465Z"
}
```

### User
```json
  {
    "readerId": 0,
    "username": "string",
    "email": "string",
    "role": "ADMIN",
    "joinDateTime": "2025-07-07T15:32:07.634Z"
  }
```
