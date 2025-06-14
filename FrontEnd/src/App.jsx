// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import OnBoarding from './components/OnBoarding';
import Login from './components/Login';
import Register from './components/Register';
import DashboardGuest from './components/DashboardGuest'; // Tambahkan ini

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<OnBoarding />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<DashboardGuest />} /> {/* Tambahkan ini */}
      </Routes>
    </Router>
  );
}

export default App;
