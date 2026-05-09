# Task Manager
Lightweight Task Manager (Spring Boot) — quick start guide

This repository contains a small task management API with JWT authentication, Flyway migrations and PostgreSQL. The README below gets you running in minutes and documents the available endpoints with real request / response examples.

Checklist
- [x] Run database (Postgres)
- [x] Set environment variables (see `.env` example)
- [x] Start the app with `./mvnw spring-boot:run` or `mvn spring-boot:run`
- [x] Verify Flyway applied migrations (the app runs them at startup)

Quick requirements
- Java 21 (project uses Java 21)
- Maven (or use the included `./mvnw` wrapper)
- PostgreSQL running and reachable

1-minute quick start
1) Create a PostgreSQL database (example):

```sql
CREATE DATABASE taskmanager;
-- create a user if needed, grant privileges
```

2) Create a `.env` file (example below) or export the variables in your shell.

3) Run the app:

```bash
./mvnw spring-boot:run
# or: mvn spring-boot:run
```

Flyway will run at application startup and apply the SQL files in `src/main/resources/db/migration` (V1__, V2__, ...). If you see Flyway logs and creation of `flyway_schema_history`, migrations ran.

Environment variables / .env (required)
Set these variables in a `.env` file or export them in your environment. Spring Boot maps environment variables by replacing `.` with `_` and by uppercasing, so both `spring.datasource.url` and `SPRING_DATASOURCE_URL` work when provided as environment variables.

Example `.env` (copy to project root):

```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/taskmanager
SPRING_DATASOURCE_USERNAME=lost
SPRING_DATASOURCE_PASSWORD=1984

# JWT secret must be long enough for HMAC-SHA (recommend at least 32 bytes). Example uses a simple string for local dev.
JWT_SECRET=change_this_to_a_long_random_string_at_least_32_chars
# Expiration in milliseconds (e.g. 3600000 = 1 hour)
JWT_EXPIRATION=3600000

# Optional: override Flyway location (defaults to classpath:db/migration)
SPRING_FLYWAY_LOCATIONS=classpath:db/migration
```

Mapping notes
- `JWT_SECRET` -> maps to property `jwt.secret` used by the application
- `JWT_EXPIRATION` -> maps to `jwt.expiration` (milliseconds)

How to pass env variables when running with Maven (example):

```bash
# using env vars in your shell
export JWT_SECRET="mysecretkey_32_char_minimum_12345"
export JWT_EXPIRATION=3600000
./mvnw spring-boot:run
```

All endpoints (summary)
Base URL: http://localhost:8080

- POST /users         — Register new user (public)
- GET  /users         — List users (public)
- POST /auth/login    — Login and receive JWT (public)
- GET  /tasks         — List tasks (authenticated). Query params: `status`, `keyword`
- GET  /tasks/{id}    — Get task by id (authenticated)
- POST /tasks         — Create task (authenticated)
- PATCH /tasks/{id}/status — Update task status (authenticated)
- DELETE /tasks/{id}  — Delete task (authenticated)

API endpoints (examples)
Full examples and responses below.

1) Register user — POST /users
- Public endpoint (no auth)

Request
```bash
curl -s -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com","password":"secret"}'
```

Response (201 Created)
```json
{
  "id": 1,
  "name": "Alice",
  "email": "alice@example.com"
}
```

2) Login — POST /auth/login
- Returns a JWT token string (short string). Use this token for authenticated endpoints.

Request
```bash
curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","password":"secret"}'
```

Response (200 OK)
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... (JWT token string)
```

3) Create a task — POST /tasks
- Requires Authorization: Bearer <JWT>

Request
```bash
curl -s -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"title":"Buy milk","description":"Buy 2 liters","userId":1}'
```

Response (201 Created)
```json
{
  "id": 1,
  "title": "Buy milk",
  "status": "TODO",
  "description": "Buy 2 liters",
  "userId": 1
}
```

4) List tasks — GET /tasks
- Optional query params: `status` and `keyword` (both optional)

Request
```bash
curl -s -X GET "http://localhost:8080/tasks?status=TODO&keyword=milk" \
  -H "Authorization: Bearer $TOKEN"
```

Response (200 OK)
```json
[
  {
	"id": 1,
	"title": "Buy milk",
	"status": "TODO",
	"description": "Buy 2 liters",
	"userId": 1
  }
]
```

5) Get task by id — GET /tasks/{id}

Request
```bash
curl -s -X GET http://localhost:8080/tasks/1 -H "Authorization: Bearer $TOKEN"
```

Response (200 OK)
```json
{
  "id": 1,
  "title": "Buy milk",
  "status": "TODO",
  "description": "Buy 2 liters",
  "userId": 1
}
```

6) Update task status — PATCH /tasks/{id}/status

Request
```bash
curl -s -X PATCH http://localhost:8080/tasks/1/status \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"status":"DONE"}'
```

Response (200 OK)
```json
{
  "id": 1,
  "title": "Buy milk",
  "status": "DONE",
  "description": "Buy 2 liters",
  "userId": 1
}
```

7) Delete a task — DELETE /tasks/{id}

Request
```bash
curl -s -X DELETE http://localhost:8080/tasks/1 -H "Authorization: Bearer $TOKEN"
```

Response
- 204 No Content (empty body)

Notes about authentication and headers
- The app expects the Authorization header: `Authorization: Bearer <token>`
- The `/auth/login` and `/users` endpoints are public; all other endpoints require a valid JWT.

Troubleshooting
- If Flyway does not run on startup, check logs for errors and confirm DB connection via `SPRING_DATASOURCE_URL`.
- To inspect applied migrations run this SQL against your DB:

```sql
SELECT installed_rank, version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;
```

Advanced: run the app packaged

```bash
./mvnw clean package -DskipTests
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```

Where to look next in the code
- Migrations: `src/main/resources/db/migration` (V1__..., V2__...)
- Controllers: `src/main/java/com/lucas/taskmanager/controller`
- JWT config: `src/main/java/com/lucas/taskmanager/service/JwtService.java`

Running tests
-------------

Unit and slice tests are included under `src/test/java`. The project uses JUnit 5 and Mockito. Tests that exercise controllers use Spring's `@WebMvcTest` and MockMvc so they do not require a running database.

Run all tests:

```bash
./mvnw test
# or: mvn test
```

Run a single test class (example):

```bash
./mvnw -Dtest=TaskServiceTest test
```

Run tests from IDE: import the project and run the JUnit configuration for a class or the whole test suite.

If a test needs a database, the test scope includes H2; however most existing tests mock repositories and services.
