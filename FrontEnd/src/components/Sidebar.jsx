import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar = ({ isOpen, onToggle }) => {
  return (
    <div
      className={`fixed left-0 top-0 h-full bg-[#77B5CD] text-gray-700 shadow-lg transition-all duration-300 z-50 ${
        isOpen ? 'w-60' : 'w-0 overflow-hidden'
      }`}
    >
      <div className="bg-[#186B8B] text-white text-2xl font-bold p-4 text-center">Booqu</div>

      <nav className="space-y-0 font-bold">
        <Link
          to="/dashboard"
          className="block bg-[#4FA1C1] p-4 border-b border-white/20 text-white"
        >
          Dashboard
        </Link>
        <Link
          to="/on-loan"
          className="block p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
        >
          On Loan
        </Link>
        <Link
          to="/discover"
          className="block p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
        >
          Discover
        </Link>
        <Link
          to="/library"
          className="block p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
        >
          My Library
        </Link>
        <Link
          to="/waiting-list"
          className="block p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
        >
          Reservasi
        </Link>
        <Link
          to="/setting"
          className="block p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
        >
          Setting
        </Link>
        <Link
          to="/login"
          className="block p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer transition-colors"
        >
          Log out
        </Link>
      </nav>
    </div>
  );
};

export default Sidebar;
