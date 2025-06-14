import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Footer from "./Footer";
import "../styles/Register.css";

function Register() {
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, username, email, password }),
      });
      
      if (res.ok) {
        const data = await res.json();
        alert("Registrasi berhasil! Silakan login.");
        navigate("/login");
      } else {
        const errorData = await res.json();
        console.error("Registration error:", errorData);
        alert(`Registrasi gagal: ${errorData.message || 'Unknown error'}`);
      }
    } catch (error) {
      console.error("Network error:", error);
      alert("Terjadi kesalahan koneksi. Pastikan backend berjalan di port 8080.");
    }
  };

  return (
    <div className="register-page">
      <div className="register-header">Booqu</div>
      <div className="register-card">
        <div className="register-logo">B</div>
        <form className="register-form" onSubmit={handleSubmit}>          <input
            className="register-input"
            type="text"
            placeholder="Nama"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <input
            className="register-input"
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            className="register-input"
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            className="register-input"
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="register-btn" type="submit">
            Buat Akun
          </button>
        </form>
        <div className="register-link">
          Sudah memiliki akun? <Link to="/login">Masuk disini</Link>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Register;
