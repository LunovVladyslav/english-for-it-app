# English For IT - Frontend Client

The frontend client for the English for IT application, built with **React 18** and **Vite**. It features a modern, responsive design and real-time interaction with the backend API.

## 🛠 Tech Stack

*   **React 18**: UI Library.
*   **Vite**: Build tool and dev server.
*   **React Router 6 (HashRouter)**: for client-side routing.
*   **Axios**: HTTP Client with interceptors for JWT injection.
*   **Vanilla CSS**: Custom design system using CSS variables.

## ✨ New Features

### Responsive Design
*   **Mobile Sidebar**: Collapsible drawer navigation for small screens.
*   **Adaptive Layout**: Grid systems that stack vertically on mobile devices.

### Components
*   **DocumentUpload**: Interface for uploading PDFs to the AI Tutor.
*   **FeedbackForm**: Floating feedback widget with categorized input (Bug, Feature).
*   **Dashboard**: Real-time display of XP, Level, and Streak.

## 📂 Project Structure

*   `src/api/`: Axios instance configuration.
*   `src/components/`: Reusable UI components.
    *   `layout/`: Sidebar, Header, Layout wrapper.
    *   `ui/`: Buttons, Inputs.
    *   `DocumentUpload.jsx`: RAG file uploader.
    *   `FeedbackForm.jsx`: User feedback modal.
*   `src/context/`: React Context (AuthContext) with **Demo Mode** logic.
*   `src/pages/`: Application views (Login, Dashboard, Tutor, etc.).
*   `src/styles/`: Global CSS variables and reset.

## 📜 Scripts

| Script | Description |
| :--- | :--- |
| `npm run dev` | Start development server on port 5173. |
| `npm run build` | Build for production (output to `dist/`). |
| `npm run preview` | Preview production build locally. |
| `npm run deploy` | Deploy `dist/` folder to GitHub Pages. |

## 🎨 Design System

We use a variable-based CSS system located in `src/styles/variables.css`.
*   **Colors**: `var(--color-primary)`, `var(--bg-app)`, etc.
*   **Spacing**: `var(--spacing-md)`, `var(--radius-lg)`.

## 🚀 Deployment (GitHub Pages)

The app is configured to deploy to GitHub Pages.
1.  **Base Path**: Configured as `/english-for-it-app/` in `vite.config.js`.
2.  **Router**: Uses `HashRouter` to support static hosting routing.

```bash
npm run deploy
```
