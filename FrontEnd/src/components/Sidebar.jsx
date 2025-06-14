import React from 'react';

const Sidebar = ({ isOpen, onToggle }) => {
  return (
    <div className={`fixed left-0 top-0 h-full bg-[#77B5CD] text-gray-700 shadow-lg transition-all duration-300 z-50 ${
      isOpen ? 'w-60' : 'w-0 overflow-hidden'
    }`}>
      <div className="bg-[#186B8B] text-white text-2xl font-bold p-4 text-center">Booqu</div>
      
      <nav className="space-y-0">
        <div className="bg-[#4FA1C1] p-4 font-semibold border-b border-white/20 text-white">
          Dashboard
        </div>
        <div className="p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
             onClick={() => alert('Navigasi ke On loan')}>
          On loan
        </div>
        <div className="p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
             onClick={() => alert('Navigasi ke Discover')}>
          Discover
        </div>
        <div className="p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
             onClick={() => alert('Navigasi ke My Library')}>
          My Library
        </div>
        <div className="p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
             onClick={() => alert('Navigasi ke Waiting List')}>
          Reservasi 
        </div>
        <div className="p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer border-b border-white/20 transition-colors"
             onClick={() => alert('Navigasi ke Setting')}>
          Setting
        </div>
        <div className="p-4 hover:bg-[#4FA1C1] hover:text-white cursor-pointer transition-colors"
             onClick={() => alert('Logout')}>
          Log out
        </div>
      </nav>
    </div>
  );
};

export default Sidebar;
