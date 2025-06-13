import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function OnBoarding() {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-[#77B5CD] text-white font-serif flex flex-col relative">
      {/* Header kiri atas */}
      <header className="absolute top-6 left-6 text-3xl font-bold">
        Booqu
      </header>

      {/* Konten utama */}
      <div className="flex-1 flex items-center justify-center px-4">
        <div className="flex flex-col items-center">
          {/* Logo */}
          <div className="bg-white text-[#77B5CD] rounded-full w-48 h-48 flex items-center justify-center mb-10">
            <span className="text-9xl font-bold" style={{ fontFamily: 'Habibi, serif' }}>
              B
            </span>
          </div>

          <h1 className="text-4xl font-bold mb-2">Selamat Datang</h1>
          <p className="mb-6 text-lg text-white text-center">
            Daftar sekarang dan nikmati fitur lengkap kami
          </p>

          <div className="flex flex-col gap-3 w-64">
            <button
              onClick={() => navigate('/register')}
              className="bg-white text-[#77B5CD] py-2 rounded-md font-medium hover:bg-gray-200 transition"
            >
              Buat Akun
            </button>
            <button
              onClick={() => navigate('/login')}
              className="bg-white text-[#77B5CD] py-2 rounded-md font-medium hover:bg-gray-200 transition"
            >
              Masuk
            </button>
            <button
              onClick={() => navigate('/guest')}
              className="border border-white text-white py-2 rounded-md font-medium hover:bg-white hover:text-[#77B5CD] transition"
            >
              Masuk sebagai tamu
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
