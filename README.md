# SmartFix – Complaint & Maintenance Management System

A full-stack complaint and maintenance management platform that enables residents to report maintenance issues, administrators to assign technicians, and technicians to track and resolve complaints.

**Live Demo:** [SmartFix Application](https://smart-fix-complaint-maintenance-sys.vercel.app/)

**Backend API:** [SmartFix Backend](https://smartfix-backend-sxb8.onrender.com/)

**GitHub Repository:** [SmartFix – Source Code](https://github.com/ajaykumarr05/SmartFix-Complaint-Maintenance-System)

---

## Overview

SmartFix streamlines maintenance request management through a centralized platform with role-based access.

Instead of managing complaints manually, users can submit requests, track their progress, and view updates. Administrators can manage complaints and assign technicians, while technicians can update the status of assigned work.

## Key Features

### Authentication & Authorization
- User registration and login.
- JWT-based authentication.
- Role-based access control.
- Three user roles: Resident, Technician, and Admin.
- Secure password hashing using BCrypt.

### Complaint Management
- Create and view maintenance complaints.
- Categorize complaints by type.
- Set complaint priority.
- Track complaint status.
- View complaint activity history.
- Record complaint details and location.

### Admin Dashboard
- View complaint statistics.
- Monitor maintenance requests.
- Assign technicians to complaints.
- Manage complaint status.

### Technician Workflow
- View maintenance assignments.
- Update complaint status.
- Track assigned work.

### Additional Functionality
- Dashboard statistics.
- SLA-based due dates.
- Complaint status tracking.
- Persistent relational database storage.

## Technology Stack

| Layer | Technologies |
|---|---|
| Frontend | React.js, Vite, JavaScript |
| Backend | Java 17, Spring Boot 3.5.6 |
| Security | Spring Security, JWT, BCrypt |
| ORM | Spring Data JPA, Hibernate |
| Database | TiDB Cloud (MySQL-compatible) |
| Build Tool | Maven |
| Deployment | Vercel, Render |
| Version Control | Git, GitHub |

## System Architecture

```text
             USER
               |
               v
       React + Vite Frontend
          (Vercel)
               |
          REST API / HTTP
               |
               v
       Spring Boot Backend
            (Render)
               |
       Spring Security + JWT
               |
               v
       Spring Data JPA
          / Hibernate
               |
               v
         TiDB Cloud
       (MySQL Compatible)
```

## Application Workflow

1. A resident registers or logs in.
2. The backend authenticates the user and issues a JWT.
3. The resident submits a maintenance complaint.
4. The administrator reviews the complaint and assigns a technician.
5. The technician updates the complaint status.
6. The application displays the latest complaint information and activity history.

## REST API Endpoints

Base URL:

```text
https://smartfix-backend-sxb8.onrender.com/api
```

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a resident |
| POST | `/auth/login` | Authenticate a user |

### Complaint Management

| Method | Endpoint | Description |
|---|---|---|
| GET | `/complaints` | Retrieve complaints |
| POST | `/complaints` | Create a complaint |
| PATCH | `/complaints/{id}/status` | Update complaint status |
| PATCH | `/complaints/{id}/assign/{technicianId}` | Assign a technician |
| GET | `/complaints/{id}/history` | Retrieve complaint history |

### Dashboard & Users

| Method | Endpoint | Description |
|---|---|---|
| GET | `/dashboard/stats` | Retrieve dashboard statistics |
| GET | `/users/technicians` | Retrieve available technicians |

Protected endpoints require a JWT access token:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

## Project Structure

```text
SmartFix-Complaint-Maintenance-System/
│
└── SmartFix/
    │
    ├── backend/
    │   ├── src/
    │   │   └── main/
    │   │       ├── java/com/ajay/smartfix/
    │   │       │   ├── config/
    │   │       │   ├── controller/
    │   │       │   ├── dto/
    │   │       │   ├── model/
    │   │       │   ├── repository/
    │   │       │   ├── security/
    │   │       │   └── service/
    │   │       └── resources/
    │   ├── Dockerfile
    │   └── pom.xml
    │
    ├── frontend/
    │   ├── src/
    │   ├── public/
    │   ├── package.json
    │   └── vite.config.js
    │
    └── database/
```

## Running the Project Locally

### Prerequisites

- Java 17 or later
- Node.js and npm
- MySQL-compatible database
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/ajaykumarr05/SmartFix-Complaint-Maintenance-System.git
```

```bash
cd SmartFix-Complaint-Maintenance-System/SmartFix
```

### 2. Configure the Backend

Navigate to the backend directory:

```bash
cd backend
```

Configure the database connection and required environment variables.

Example:

```properties
server.port=8080

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

app.jwt.secret=${JWT_SECRET}
app.demo.admin-password=${DEMO_ADMIN_PASSWORD}
app.demo.technician-password=${DEMO_TECH_PASSWORD}
```

Provide the remaining required configuration values from your backend configuration.

Run the backend:

```bash
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

### 3. Configure the Frontend

Open another terminal:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

Ensure the frontend API base URL points to your local backend when running locally.

## Deployment

| Component | Platform |
|---|---|
| Frontend | Vercel |
| Backend | Render |
| Database | TiDB Cloud |

- **Live Application:** https://smart-fix-complaint-maintenance-sys.vercel.app/
- **Backend:** https://smartfix-backend-sxb8.onrender.com/

Deployment configuration uses environment variables for database credentials, JWT secrets, and demo account passwords. Never commit production secrets to GitHub.

## Security

- JWT-based stateless authentication.
- BCrypt password hashing.
- Role-based authorization.
- Protected REST API endpoints.
- Environment-based configuration for sensitive credentials.

## Future Improvements

- Email notifications for complaint updates.
- Image attachments for maintenance complaints.
- Advanced analytics and reporting.
- Real-time complaint status notifications.
- Pagination and advanced complaint filtering.
- Automated testing and CI/CD integration.

## Author

**Ajay Kumar**

B.Tech – Computer Science & Engineering

[GitHub](https://github.com/ajaykumarr05)

---

*SmartFix – Making maintenance management more organized, transparent, and efficient.*
