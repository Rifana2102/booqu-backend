import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Footer from "./Footer";
import "../styles/Login.css";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
<<<<<<< HEAD
  const navigate = useNavigate();

  // DUMMY DATA UNTUK TESTING DASHBOARD:
  // Username: admin, Password: admin
  // Username: user, Password: user
  // Username: test, Password: test
=======
>>>>>>> 65b692f5d57541a5072be262bfc58333ba344d2e

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Data dummy untuk testing dashboard
    const dummyUsers = [
      { username: "admin", password: "admin", name: "Administrator" },
      { username: "user", password: "user", name: "Regular User" },
      { username: "test", password: "test", name: "Test User" },
    ];

    // Cek dummy login terlebih dahulu
    const dummyUser = dummyUsers.find(
      (u) => u.username === username && u.password === password
    );

    if (dummyUser) {
      alert("Login berhasil!");
      // Simpan user data dummy di localStorage
      localStorage.setItem(
        "user",
        JSON.stringify({
          username: dummyUser.username,
          name: dummyUser.name,
          token: "dummy-token-123",
        })
      );
      // Redirect ke dashboard
      navigate("/dashboard");
      return;
    }

    try {
      const res = await fetch("http://localhost:6060/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
<<<<<<< HEAD
      if (res.ok) {
        const data = await res.json();
        alert("Login berhasil!");
        // Simpan token atau user data di localStorage
        localStorage.setItem("user", JSON.stringify(data));
        // Redirect ke dashboard
        navigate("/dashboard");
=======

      if (res.ok) {
        const data = await res.json();
        alert("Login berhasil!");
        // Redirect atau simpan token jika diperlukan
        console.log("Login response:", data);
>>>>>>> 65b692f5d57541a5072be262bfc58333ba344d2e
      } else {
        const errorData = await res.json();
        console.error("Login error:", errorData);
        alert(
          `Login gagal: ${
            errorData.message || "Username atau password salah"
          }`
        );
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
        <form className="login-form" onSubmit={handleSubmit}>
          <input
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
