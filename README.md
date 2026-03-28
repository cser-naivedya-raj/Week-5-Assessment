# 🍕 Secure Food Delivery Order Processing System

> **Java Final Assessment**  
> A Spring Boot Microservices backend for a Food Delivery Platform with strict **1:1 entity relationships**, JWT authentication, and cascade operations.

---

## 📖 Description

This project simulates the backend of a food delivery platform (similar to Swiggy/Zomato). It manages the full lifecycle of a food order — from user registration and restaurant onboarding to payment, delivery, shipping, and notifications — using a **microservices architecture** built with Spring Boot.

Each order is tightly coupled to exactly one payment, one delivery, one shipping record, and one notification, enforcing strict **1:1 relationships** across all entities.

---

## 🧩 Entities

| Entity       | Description                            |
|--------------|----------------------------------------|
| User         | Customer placing the order             |
| Restaurant   | Restaurant fulfilling the order        |
| Order        | Core order record linking all entities |
| Payment      | Payment details for an order           |
| Delivery     | Delivery partner and status            |
| Shipping     | Address and estimated delivery time    |
| Notification | Message sent to the user per order     |

---

## 🔗 Relationships (1:1)

```
Order ↔ Payment       (1:1)
Order ↔ Delivery      (1:1)
Order ↔ Notification  (1:1)
Delivery ↔ Shipping   (1:1)
```

---

## 🏗️ Architecture

```
Client (Postman / Frontend)
        ↓
  API Gateway (JWT Auth)
        ↓
┌──────────────────────────────────────────┐
│  Order      Payment    Delivery          │
│  Service    Service    Service           │
│                                          │
│  Notification          Shipping          │
│  Service               Service           │
└──────────────────────────────────────────┘
```

---

## 🚀 API Endpoints

### 1. Create User
```http
POST /users
Content-Type: application/json

{
  "name": "Ravi",
  "email": "ravi@gmail.com",
  "address": "Bangalore"
}
```

### 2. Create Restaurant
```http
POST /restaurants
Content-Type: application/json

{
  "name": "Dominos",
  "location": "Bangalore"
}
```

### 3. Place Order
```http
POST /orders
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "userId": 1,
  "restaurantId": 10,
  "item": "Pizza",
  "amount": 500
}
```

### 4. Make Payment
```http
POST /payments
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "orderId": 101,
  "mode": "UPI",
  "status": "SUCCESS"
}
```

### 5. Create Delivery
```http
POST /delivery
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "orderId": 101,
  "deliveryPartner": "Rahul",
  "status": "OUT_FOR_DELIVERY"
}
```

### 6. Add Shipping Details
```http
POST /shipping
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "deliveryId": 50,
  "address": "Bangalore",
  "estimatedTime": "30 mins"
}
```

### 7. Send Notification
```http
POST /notifications
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "orderId": 101,
  "message": "Order is on the way"
}
```

---

## 📤 Response Format

**Success:**
```json
{
  "status": "SUCCESS",
  "message": "Order placed successfully"
}
```

**Error:**
```json
{
  "status": "ERROR",
  "message": "Payment already exists for this order"
}
```

---

## ⚠️ Constraints

- One order → only **one** payment
- One order → only **one** delivery
- One delivery → only **one** shipping record
- One order → only **one** notification
- **Payment must exist** before delivery can be created
- **JWT authentication** is required for placing orders and all transactional endpoints
- **Cascade delete** is enabled — deleting an order removes all related records

---

## 🛠️ Tech Stack

| Layer         | Technology                    |
|---------------|-------------------------------|
| Language      | Java                          |
| Framework     | Spring Boot                   |
| Architecture  | Microservices                 |
| Auth          | JWT (JSON Web Tokens)         |
| ORM           | Spring Data JPA / Hibernate   |
| Database      | MySQL / PostgreSQL             |
| Build Tool    | Maven                         |
| API Testing   | Postman                       |

---

## 📁 Project Structure

```
food-delivery-microservices/
└── food-delivery/
    ├── user-service/
    ├── restaurant-service/
    ├── order-service/
    ├── payment-service/
    ├── delivery-service/
    ├── shipping-service/
    ├── notification-service/
    └── api-gateway/
```

---

## ▶️ Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL or PostgreSQL running locally

### Run the Application

```bash
# Clone the repository
git clone https://github.com/cser-naivedya-raj/Week-5-Assessment.git

# Navigate to project
cd Week-5-Assessment/food-delivery-microservices/food-delivery

# Build the project
mvn clean install

# Run each service
mvn spring-boot:run
```

> Configure database credentials in each service's `application.properties` before running.

---

## 👤 Author

**Naivedya Raj**  
Java Final Assessment
