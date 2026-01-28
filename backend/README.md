# English for IT - Adaptive Learning Platform

An AI-powered adaptive learning platform designed to help IT professionals master English. This application combines a **Skill Knowledge Graph**, **Adaptive Learning State Machine**, and **Generative AI** to provide a personalized curriculum.

## 🚀 Features

*   **Adaptive Curriculum**: A dynamic learning flow (`ASSESS` -> `PRACTICE` -> `EVALUATE` -> `ADAPT`) driven by a Finite State Machine.
*   **Skill Graph**: A Directed Acyclic Graph (DAG) representing language skills (Grammar, Vocabulary, Soft Skills). Progress is tracked per node.
*   **AI Tutor**: Integrated LLM (via Spring AI) that acts as a context-aware tutor, knowing the user's role, native language, and current lesson context.
*   **Hybrid Content Management**:
    *   **Structure**: Database schema managed by **Flyway**.
    *   **Content**: Detailed lessons are stored in Markdown files (`content/`) and synced to the DB on startup.
*   **Comprehensive Profile**: Tracks user's job role, native language, and primary goals to tailor the experience.

## 🛠️ Tech Stack

*   **Language**: Java 17
*   **Framework**: Spring Boot 3.2
*   **Database**: PostgreSQL
*   **Cache**: Redis (for session/state potential)
*   **AI Integration**: Spring AI (OpenAI / Anthropic)
*   **Documentation**: SpringDoc (Swagger UI)
*   **Build Tool**: Maven

## 📋 Prerequisites

*   **Java 17** SDK installed.
*   **Docker** (optional, recommended for DB/Redis).
*   **PostgreSQL** running on port `5432`.
*   **OpenAI API Key** (for the AI Tutor).

## 🏃‍♂️ Getting Started

### 1. Database Setup
Ensure PostgreSQL is running. You can use Docker:
```bash
docker run --name postgres-english -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
```
Create the database:
```sql
CREATE DATABASE english_for_it;
```

### 2. Configuration
Set your OpenAI API Key as an environment variable or in `backend/src/main/resources/application.yml`:
```yaml
export OPENAI_API_KEY=sk-your-key-here
```

### 3. Run the Backend
Navigate to the backend directory and run:
```bash
cd backend
mvn spring-boot:run
```

The application will:
1.  Run Flyway migrations (`db/migration/`) to set up tables.
2.  Run `ContentLoader` to ingest markdown files from `../content` into the database.
3.  Start the server on port `8080`.

### 4. Running Tests
To execute the comprehensive test suite (Unit + Integration):
```bash
mvn test
```
*Note: Docker Desktop must be running for Integration Tests (Testcontainers).*

## 📚 API Documentation

Once the application is running, access the interactive Swagger UI at:
**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

### Key Endpoints
*   `POST /api/learning/start?userId={uuid}` - Start a learning session.
*   `POST /api/tutor/chat` - Chat with the AI Tutor.
*   `POST /api/onboarding/start` - Start AI Assessment.
*   `POST /api/practice/quiz/generate` - Generate AI Quiz.
*   `POST /api/practice/roleplay/start` - Start Roleplay Scenario.

## 📂 Project Structure

*   `backend/`: Spring Boot application source code.
    *   `model/`: JPA Entities (User, SkillNode, Lesson, etc.).
    *   `service/`: Business logic (SkillGraph, StateMachine, ContentIngestion).
    *   `controller/`: REST API endpoints.
    *   `config/`: State Machine and AI configuration.
*   `content/`: Markdown files containing the curriculum (Modules 1-6).
*   `prompts/`: System prompts for the AI Tutor for each module.
