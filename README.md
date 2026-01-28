# English for IT App

A modern, AI-powered platform designed to help IT professionals master English for the workplace. This application combines a robust Spring Boot backend with a responsive React frontend to deliver personalized tutoring, roleplay scenarios, and structured learning paths.

## 🚀 Features

*   **AI Tutor**: Real-time chat interface for practicing English, powered by LLMs (via OpenRouter/OpenAI).
*   **Structured Learning Path**: A 30-week curriculum covering meetings, agile methodologies, architecture, and more.
*   **Roleplay Scenarios**: Interactive "choose your own adventure" style practice sessions.
*   **Progress Tracking**: Dashboard to monitor XP, current level (CEFR), and module completion.
*   **Secure Auth**: JWT-based authentication (Spring Security Resource Server).

## 🛠️ Tech Stack

### Backend
*   **Java 17** & **Spring Boot 3.2**
*   **Spring AI**: For LLM integration.
*   **Spring Security**: OAuth2 Resource Server (JWT) & BCrypt.
*   **Database**: PostgreSQL 15.
*   **Build Tool**: Maven.
*   👉 [Read Backend Documentation](backend/README.md)

### Frontend
*   **React 18** & **Vite** (SWC)
*   **Styling**: Vanilla CSS (Premium "Dark Mode" IT Aesthetic).
*   **State Management**: React Context API.
*   **Router**: React Router v6 (HashRouter).
*   👉 [Read Frontend Documentation](frontend/README.md)

## 📋 Prerequisites

*   **Docker** & **Docker Compose** (Recommended)
*   OR Java 17, Node.js 18+, and PostgreSQL installed locally.
*   **OpenAI / OpenRouter API Key**: Required for AI features.

## ⚡ Quick Start (Docker)

The easiest way to run the full stack (Backend + Database).

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/LunovVladyslav/english-for-it-app.git
    cd english-for-it-app
    ```

2.  **Start Backend & Database**:
    ```bash
    cd backend
    export OPENAI_API_KEY=your_key_here
    docker-compose up --build
    ```
    *   Backend API: `http://localhost:8080`
    *   Database: `localhost:5432`

3.  **Start Frontend**:
    Open a new terminal:
    ```bash
    cd frontend
    npm install
    npm run dev
    ```
    *   Frontend UI: `http://localhost:5173`

4.  **Access the App**:
    Open **http://localhost:5173**, register a new account, and start learning!

## 🔧 Configuration

### Environment Variables
| Variable | Description | Default |
| :--- | :--- | :--- |
| `OPENAI_API_KEY` | Key for OpenRouter/OpenAI API | Required |
| `SPRING_DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://db:5432/englishforit` |

### AI Model Configuration
To change the AI model (default: `openai/gpt-oss-20b:free` via OpenRouter), edit `backend/src/main/resources/application.properties`:
```properties
spring.ai.openai.base-url=https://openrouter.ai/api/v1
spring.ai.openai.chat.options.model=openai/gpt-oss-20b:free
```

## 📂 Project Structure

```
english-for-it-app/
├── backend/            # Spring Boot Application
│   ├── Dockerfile      # Backend container image
│   ├── docker-compose.yml # Backend + DB orchestration
│   └── src/            # Java source code
├── frontend/           # React Application
│   ├── src/            # React components & pages
│   └── vite.config.js  # Vite configuration
└── .gitignore          # Global gitignore
```

## 🧪 Testing

**Backend**:
```bash
cd backend
mvn clean test
```

**Manual Verification**:
1.  Register/Login via Frontend.
2.  Navigate to "AI Tutor" and send a message.
3.  Check Dashboard for user details.
