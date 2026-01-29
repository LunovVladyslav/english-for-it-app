# English for IT App

A modern, AI-powered platform designed to help IT professionals master English for the workplace. This application combines a robust Spring Boot backend with a responsive React frontend to deliver personalized tutoring, roleplay scenarios, and structured learning paths.

## 🚀 Features

*   **AI Tutor (RAG-Enabled)**: Real-time chat interface powered by LLMs (OpenRouter/OpenAI). Supports **Document Uploads** to provide context-aware responses based on your own files (PDFs).
*   **Gamification**: Earn **XP**, level up, and maintain a **Daily Streak** to stay motivated.
*   **Structured Learning Path**: A 30-week curriculum covering meetings, agile methodologies, architecture, and more.
*   **Roleplay Scenarios**: Interactive "choose your own adventure" style practice sessions.
*   **Feedback System**: Built-in feedback form to report bugs or request features.
*   **Responsive Design**: Fully optimized for mobile and desktop devices.
*   **Secure Auth**: JWT-based authentication (Spring Security Resource Server).

## 🛠️ Tech Stack

### Backend
*   **Java 17** & **Spring Boot 3.2**
*   **Spring AI**: For LLM integration and RAG (Retrieval-Augmented Generation).
*   **Spring Security**: OAuth2 Resource Server (JWT) & BCrypt.
*   **Database**: PostgreSQL 15 with **pgvector** extension.
*   **Email**: JavaMailSender (integrated with **MailDev** for local testing).
*   **Build Tool**: Maven.
*   👉 [Read Backend Documentation](backend/README.md)

### Frontend
*   **React 18** & **Vite** (SWC)
*   **Styling**: Vanilla CSS (Premium "Dark Mode" IT Aesthetic).
*   **State Management**: React Context API.
*   **Router**: React Router v6 (HashRouter).
*   👉 [Read Frontend Documentation](frontend/README.md)

## 📋 Prerequisites

*   **Docker** & **Docker Compose** (Recommended for full stack).
*   OR Java 17, Node.js 18+, and PostgreSQL (with pgvector) installed locally.
*   **OpenAI / OpenRouter API Key**: Required for AI features.

## ⚡ Quick Start (Docker)

The easiest way to run the full stack (Backend + Database + MailDev).

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/LunovVladyslav/english-for-it-app.git
    cd english-for-it-app
    ```

2.  **Start Backend & Infrastructure**:
    ```bash
    cd backend
    export OPENAI_API_KEY=your_key_here
    docker-compose up -d --build
    ```
    *   **Backend API**: `http://localhost:8080`
    *   **Database**: `localhost:5432`
    *   **MailDev**: `http://localhost:1080` (Check sent emails here)

3.  **Start Frontend**:
    Open a new terminal:
    ```bash
    cd frontend
    npm install
    npm run dev
    ```
    *   **Frontend UI**: `http://localhost:5173`

4.  **Access the App**:
    Open **http://localhost:5173**, register a new account, or use the **Demo Mode** button on the login screen.

## 🔧 Configuration

### Environment Variables
| Variable | Description | Default |
| :--- | :--- | :--- |
| `OPENAI_API_KEY` | Key for OpenRouter/OpenAI API | Required |
| `SPRING_DATASOURCE_URL` | JDBC URL | `jdbc:postgresql://db:5432/englishforit` |
| `SPRING_MAIL_HOST` | SMTP Host | `localhost` (MailDev) |

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
│   ├── docker-compose.yml # Backend + DB + MailDev orchestration
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
2.  Navigate to "AI Tutor" and upload a PDF document.
3.  Ask questions about the uploaded content.
4.  Check Dashboard for updated XP and Streak.
5.  Send feedback via the floating button and check MailDev (`http://localhost:1080`).
