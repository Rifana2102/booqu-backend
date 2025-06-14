// src/components/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [nama, setNama] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    // Validasi login
    if (nama === 'admin' && password === 'admin') {
      alert('Login berhasil!');
      navigate('/dashboard'); // Redirect ke DashboardGuest
    } else {
      alert('Nama atau kata sandi salah');
    }
  };

  return (
    <div className="flex flex-col min-h-screen bg-white font-serif">
      <header className="p-4 text-3xl font-bold text-[#77B5CD]">Booqu</header>

      <main className="flex-grow flex items-center justify-center">
        <div className="bg-white p-8 rounded-lg shadow-md w-80 text-[#77B5CD] flex flex-col items-center">
          <div className="bg-[#77B5CD] text-white rounded-full w-40 h-40 flex items-center justify-center mb-8">
            <span className="text-8xl font-bold" style={{ fontFamily: 'Habibi, serif' }}>
              B
            </span>
          </div>

          <form onSubmit={handleLogin} className="flex flex-col gap-4 w-full">
            <input
              type="text"
              placeholder="Nama"
              value={nama}
              onChange={(e) => setNama(e.target.value)}
              className="px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-[#77B5CD]"
              required
            />
            <input
              type="password"
              placeholder="Kata Sandi"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-[#77B5CD]"
              required
            />
            <button
              type="submit"
              className="bg-[#77B5CD] text-white py-2 rounded-md font-medium hover:bg-[#7ea2b5] transition"
            >
              Masuk
            </button>
          </form>

          <p className="text-sm text-center mt-4 font-serif">
            Belum punya akun?{' '}
            <a href="/register" className="underline text-[#8ab0c8] hover:text-[#6d91a4] transition">
              Daftar di sini
            </a>
          </p>
        </div>
      </main>

      <footer className="text-center py-4 text-sm text-[#8ab0c8]">
        &copy; {new Date().getFullYear()} Booqu. Semua hak dilindungi.
      </footer>
    </div>
  );
}
