# SmartFix – Complaint & Maintenance Management System

Full-stack starter application:
- Backend: Java 17, Spring Boot 3.5.6, Spring Security, JWT, Spring Data JPA, MySQL
- Frontend: React + Vite
- Roles: RESIDENT, TECHNICIAN, ADMIN
- Features: registration/login, complaint creation/listing, assignment, status updates, dashboard statistics, complaint history.

## 1. Prerequisites
- JDK 17
- IntelliJ IDEA (Community is fine)
- MySQL 8
- Node.js 20+ and npm

## 2. Create the database
Run in MySQL Workbench:
```sql
CREATE DATABASE smartfix_db;
```

## 3. Configure backend
Open `backend/src/main/resources/application.properties`.
The defaults are:
- URL: `jdbc:mysql://localhost:3306/smartfix_db`
- username: `root`
- password: `root`

Change `spring.datasource.username` and `spring.datasource.password` to match your local MySQL setup.

## 4. Run backend in IntelliJ
1. Extract this ZIP.
2. IntelliJ IDEA > Open > select the `smartfix/backend` folder (or open the root and then open backend as a Maven project).
3. Set Project SDK to JDK 17.
4. Wait for Maven import.
5. Run `SmartfixApplication.java`.
6. Backend runs at `http://localhost:8080`.

If IntelliJ uses a newer JDK (e.g. 27), change the Project SDK and Maven Runner JRE to JDK 17 in Settings > Build, Execution, Deployment > Build Tools > Maven.

## 5. Run frontend
Open a terminal in `frontend`:
```bash
npm install
npm run dev
```
Open `http://localhost:5173`.

## 6. Demo accounts
The backend creates these accounts on first startup if they do not already exist:
- Admin: `admin@smartfix.com` / `Admin@123`
- Technician: `tech@smartfix.com` / `Tech@123`

Register a resident from the UI using the Register link.

## 7. Main API routes
All routes are under `/api`.
- `POST /auth/register`
- `POST /auth/login`
- `GET /complaints` (authenticated; residents see their own)
- `POST /complaints` (resident/admin)
- `GET /complaints/{id}`
- `PATCH /complaints/{id}/status` (assigned technician/admin)
- `PATCH /complaints/{id}/assign/{technicianId}` (admin)
- `GET /dashboard/stats`
- `GET /users/technicians` (admin)

JWT is sent as `Authorization: Bearer <token>` by the frontend.

## 8. Notes
This is a portfolio/learning implementation, not a production-hardened service. Before production use, add email delivery, image storage, rate limiting, refresh-token rotation, stronger audit controls, integration tests, and secrets management. The default demo passwords must be changed.
