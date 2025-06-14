import React, { useState } from "react";
import { Link } from "react-router-dom";
import Footer from "./Footer";
import "../styles/Login.css";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch("http://localhost:6060/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
      
      if (res.ok) {
        const data = await res.json();
        alert("Login berhasil!");
        // Lakukan redirect atau simpan token di sini jika perlu
        console.log("Login response:", data);
      } else {
        const errorData = await res.json();
        console.error("Login error:", errorData);
        alert(`Login gagal: ${errorData.message || 'Username atau password salah'}`);
      }
    } catch (error) {
      console.error("Network error:", error);
      alert("Terjadi kesalahan koneksi. Pastikan backend berjalan di port 6060.");
    }
  };

  return (
    <div className="login-page">
      <div className="login-header">Booqu</div>
      <div className="login-card">
        <div className="login-logo">B</div>
        <form className="login-form" onSubmit={handleSubmit}>          <input
            className="login-input"
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            className="login-input"
            type="password"
            placeholder="Kata Sandi"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button className="login-btn" type="submit">
            Masuk
          </button>
        </form>
        <div className="login-register-link">
          Belum punya akun? <Link to="/register">Daftar di sini</Link>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default Login;