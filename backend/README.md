# English For IT - Backend Service

The backend service for the English for IT application, built with **Spring Boot 3.2**. It handles authentication, data persistence, and integration with AI LLMs via Spring AI.

## 🏗 Architecture

*   **Framework**: Spring Boot 3.2 (Java 17)
*   **Security**: OAuth2 Resource Server (JWT) with RSA key signing.
*   **Database**: PostgreSQL 15 (Production/Dev), H2 (Integration Tests).
*   **AI Integration**: Spring AI (OpenAI / OpenRouter).

## 🔌 API Endpoints

### Authentication (`/api/auth`)
*   `POST /register`: Create a new user account.
*   `POST /login`: Authenticate and receive a JWT Bearer token.

### AI Tutor (`/api/tutor`)
*   `POST /chat`: Send a message to the AI tutor and get a response.
    *   *Payload*: `{"message": "Hello", "userId": "uuid"}`
    *   *Requires*: `Authorization: Bearer <token>`

### Learning (`/api/learning`)
*   `GET /path`: Retrieve the user's learning path.
*   `GET /status`: Get current progress metrics.

## 🔐 Security Configuration

The application uses **RSA Key Pairs** to sign and verify JWT tokens.
*   Keys are located in `src/main/resources/certs/`.
*   During tests, keys are generated automatically or loaded from classpath.

To generate new keys manually:
```bash
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

## 🧪 Testing

The project uses `JUnit 5` and `MockMvc` for integration testing.
*   **H2 Database** is used for integration tests to ensure isolation and speed.
*   **Testcontainers** support is available if needed but currently disabled for speed.

```bash
# Run all tests
mvn clean test
```

## 🐳 Docker Deployment

The `Dockerfile` employs a multi-stage build:
1.  **Build Stage**: Maven image compiles code and skips tests.
2.  **Run Stage**: Eclipse Temurin JRE Alpine image runs the jar.

```bash
# Build and Run just the backend (requires external DB)
docker build -t english-backend .
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=... english-backend
```
