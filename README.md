# 🛒 E-Commerce API

A production-ready, scalable, and secure E-Commerce REST API built with Spring Boot. This project provides a
comprehensive backend solution for modern online stores, featuring authentication, authorization, product management,
inventory tracking, order processing, payment integration, delivery management, caching, and background job processing.

Designed with scalability, maintainability, and security in mind, this API follows industry best practices and serves as
a strong foundation for enterprise-grade e-commerce applications.

---

## Features

### Authentication & Authorization

* JWT-based Authentication
* Access & Refresh Tokens
* Role-Based Access Control (RBAC)
* Secure Password Hashing with BCrypt
* Email Verification
* Password Reset Flow
* Logout with Token Blacklisting
* CSRF Protection
* Secure HTTP-only Cookies

### User Management

* User Registration
* User Login
* User Profile Management
* Address Management
* Account Verification
* Password Updates

### Product Management

* Product Catalog
* Product Categories
* Product Images
* Product Search & Filtering
* Product Details
* Inventory Management
* Stock Tracking

### Shopping Experience

* Shopping Cart
* Wishlist
* Product Reviews & Ratings
* Order Creation
* Order Tracking
* Order History

### Payment Integration

* Paystack Integration
* Flutterwave Integration
* Stripe Integration
* Payment Verification
* Webhook Processing
* Transaction Tracking

### Delivery Management

* Rider Management
* Delivery Assignment
* Delivery Tracking
* Delivery Status Updates

### Notifications

* Email Notifications
* SMS Notifications
* Background Notification Processing

### Performance & Scalability

* Redis Caching
* Database Indexing
* Pagination & Sorting
* Asynchronous Processing
* Background Jobs using JobRunr

### Security

* Spring Security
* JWT Authentication
* CSRF Protection
* CORS Configuration
* Secure Cookie Handling
* Request Validation
* Input Sanitization

### Database Management

* MySQL Database
* Flyway Database Migrations
* JPA/Hibernate ORM
* Transaction Management

---

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Spring Validation
* Spring Mail

### Database

* MySQL
* Flyway

### Caching

* Redis

### Background Jobs

* JobRunr

### Documentation

* OpenAPI 3
* Swagger UI

### Payments

* Stripe
* Paystack
* Flutterwave

### Containerization

* Docker
* Docker Compose

---

## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.uchechukwu.store
│   │       ├── config
│   │       ├── controllers
│   │       ├── services
│   │       ├── repositories
│   │       ├── entities
│   │       ├── dtos
│   │       ├── filters
│   │       ├── exceptions
│   │       ├── security
│   │       └── utils
│   └── resources
│       ├── db
│       │   └── migration
│       └── application.yaml
└── test
```

---

## ⚙️ Environment Variables

Create a `.env` file in the project root:

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=

REDIS_URL=
REDIS_HOST=
REDIS_PORT=
REDIS_USERNAME=
REDIS_PASSWORD=

SECRET_KEY=

PAYSTACK_SECRET_KEY=
FLUTTERWAVE_SECRET_KEY=

STRIPE_SECRET_KEY=
STRIPE_WEBHOOK_SECRET_KEY=

BREVO_API_KEY=

EMAIL_USERNAME=
EMAIL_PASSWORD=
EMAIL_PORT=587

TERMII_API_KEY=
TERMII_SENDER_ID=
TERMII_BASE_URL=

RESET_SECRET_KEY=
RESET_PASSWORD_SALT=

VERIFY_EMAIL_SECRET_KEY=
VERIFY_EMAIL_SALT=

FRONTEND_URL=
CORS_ALLOWED_ORIGINS=
```

---

## 🐳 Running with Docker

### Build Image

```bash
docker build -t ecommerce-api .
```

### Run Container

```bash
docker run -p 8080:8080 ecommerce-api
```

### Docker Compose

```bash
docker compose up -d
```

---

## 🔄 Database Migration

### Validate Migrations

```bash
./mvnw flyway:validate
```

### Apply Migrations

```bash
./mvnw flyway:migrate
```

### Migration Information

```bash
./mvnw flyway:info
```

---

## 📖 API Documentation

After starting the application:

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI Specification

```text
http://localhost:8080/v3/api-docs
```

---

## 🔐 Security Architecture

This project follows modern security best practices:

* Stateless JWT Authentication
* Refresh Token Rotation
* Access Token Blacklisting
* Secure HTTP-only Cookies
* CSRF Protection
* Role-Based Authorization
* BCrypt Password Encoding
* Request Validation
* Secure CORS Configuration

---

## 📈 Scalability Considerations

The application is designed to support production workloads through:

* Redis Caching
* Background Job Processing
* Database Connection Pooling
* Pagination
* Lazy Loading
* Stateless Authentication
* Containerized Deployment

---

## 🧪 Testing

Run all tests:

```bash
./mvnw test
```

Run with coverage:

```bash
./mvnw verify
```

---

## 🚀 Deployment

The application can be deployed to:

* AWS
* Railway
* Render
* DigitalOcean
* Fly.io
* Google Cloud Platform
* Azure
* Kubernetes Clusters

Deployment is supported through Docker and environment-based configuration.

---

## 📌 Future Enhancements

* Multi-Vendor Marketplace Support
* Real-Time Notifications
* Product Recommendations
* AI-Powered Search
* Analytics Dashboard
* Mobile API Gateway
* Event-Driven Architecture
* Microservices Migration

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to your branch
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Udemezue Uchechukwu**

Backend Engineer | Java & Python Developer

Specializing in:

* Spring Boot
* FastAPI
* Django
* Flask
* REST APIs
* Authentication & Security
* Payment Systems
* Cloud Deployment
* Distributed Systems

If you found this project useful, consider giving it a ⭐ on GitHub.
