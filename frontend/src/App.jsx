import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Tutor from './pages/Tutor';
import LearningPath from './pages/LearningPath';
import Practice from './pages/Practice';
import ProtectedRoute from './components/ProtectedRoute';
import Layout from './components/layout/Layout';

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      <Route
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/tutor" element={<Tutor />} />
        <Route path="/learn" element={<LearningPath />} />
        <Route path="/practice" element={<Practice />} />
      </Route>

      {/* Default redirect */}
      <Route path="/" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}

export default App;
