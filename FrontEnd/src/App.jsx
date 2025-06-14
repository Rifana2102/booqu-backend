import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import OnBoarding from "./components/OnBoarding";
import Login from "./components/Login";
import Register from "./components/Register";
import DashboardGuest from "./components/DashboardGuest";
import Dashboard from "./components/Dashboard";
import MyLibrary from "./components/MyLibrary"; // Tambahkan ini

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<OnBoarding />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard-guest" element={<DashboardGuest />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/library" element={<MyLibrary />} /> {/* Ini route MyLibrary */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
