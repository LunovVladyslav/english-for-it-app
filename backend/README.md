# English For IT - Backend Service

The backend service for the English for IT application, built with **Spring Boot 3.2**. It handles authentication, data persistence, and integration with AI LLMs via Spring AI.

## 🏗 Architecture

*   **Framework**: Spring Boot 3.2 (Java 17)
*   **Security**: OAuth2 Resource Server (JWT) with RSA key signing.
*   **Database**: PostgreSQL 15 with **pgvector** extension.
*   **AI Integration**: Spring AI (OpenAI / OpenRouter).
*   **Email**: JavaMailSender with MailDev for testing.

## ✨ Key Features

### RAG (Retrieval-Augmented Generation)
*   **Vector Store**: Uses `pgvector` to store document embeddings.
*   **Document Ingestion**: Parses uploaded PDFs and splits text into chunks.
*   **Context Retrieval**: Finds relevant document chunks to augment AI responses.

### Gamification
*   **XP System**: Awards experience points for completing lessons and chatting.
*   **Levels**: Calculates user level based on total XP.
*   **Streaks**: Tracks daily login streaks.

### Feedback System
*   **Email Service**: Sends feedback emails via SMTP (configured for MailDev in dev/docker).

## 🔌 API Endpoints

### Authentication (`/api/auth`)
*   `POST /register`: Create a new user account.
*   `POST /login`: Authenticate and receive a JWT Bearer token.
*   `GET /me`: Get current user details.

### AI Tutor (`/api/tutor`)
*   `POST /chat`: Send a message to the AI tutor.
*   `POST /documents`: Upload PDF documents for RAG context.

### Gamification (`/api/gamification`)
*   `POST /award-xp`: Award XP to the user.

### Feedback (`/api/feedback`)
*   `POST /`: Submit user feedback.

## 🔐 Security Configuration

The application uses **RSA Key Pairs** to sign and verify JWT tokens.
*   Keys are located in `src/main/resources/certs/`.
*   During tests, keys are generated automatically or loaded from classpath.

## 🧪 Testing

The project uses `JUnit 5` and `MockMvc` for integration testing.
*   **Testcontainers** or H2 can be used for DB testing.

```bash
# Run all tests
mvn clean test
```

## 🐳 Docker Deployment

The `docker-compose.yml` orchestrates the full backend environment:

1.  **App**: The Spring Boot application.
2.  **DB**: PostgreSQL with `pgvector` enabled.
3.  **MailDev**: SMTP server and Web UI for email testing.

```bash
# Build and Run
docker-compose up -d --build
```
