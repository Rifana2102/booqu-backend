import React from "react";
import { useNavigate } from "react-router-dom";
import Footer from "./Footer";
import "../styles/OnBoarding.css";

function OnBoarding() {
  const navigate = useNavigate();

  return (
    <div className="onboard-full-bg">
      <div className="onboard-header">Booqu</div>
      <div className="onboard-content">
        <div className="onboard-logo">B</div>
        <h1 className="onboard-title">Selamat Datang</h1>
        <p className="onboard-subtitle">
          Daftar sekarang dan nikmati fitur lengkap kami
        </p>
        <button className="onboard-btn" onClick={() => navigate("/register")}>
          Buat Akun
        </button>
        <button className="onboard-btn" onClick={() => navigate("/login")}>
          Masuk
        </button>        <button className="onboard-btn-outline" onClick={() => navigate("/dashboard-guest")}>
          Masuk sebagai tamu
        </button>
      </div>
      <Footer />
    </div>
  );
}

export default OnBoarding;