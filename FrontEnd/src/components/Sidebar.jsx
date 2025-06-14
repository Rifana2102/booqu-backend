import React from "react";
import { Link, useLocation } from "react-router-dom";

const Sidebar = ({ isOpen }) => {
  const location = useLocation();
  const currentPath = location.pathname;

  const menuItems = [
    { name: "Dashboard", path: "/dashboard" },
    { name: "On Loan", path: "/on-loan" },
    { name: "Discover", path: "/discover" },
    { name: "My Library", path: "/library" },
    { name: "Reservasi", path: "/reservasi" },
    { name: "Setting", path: "/setting" },
    { name: "Log out", path: "/logout" },
  ];

  return (
    <div
      className={`fixed left-0 top-0 h-full bg-[#77B5CD] text-gray-700 shadow-lg transition-all duration-300 z-50 ${
        isOpen ? "w-60" : "w-0 overflow-hidden"
      }`}
    >
      <div className="bg-[#3F6C88] text-white text-center font-bold text-2xl py-4">
        Booqu
      </div>

      {/* Hapus mt-4 agar lebih rapat ke atas */}
      <nav className="flex flex-col">
        {menuItems.map((item) => (
          <Link
            key={item.path}
            to={item.path}
            className={`block w-full px-6 py-3 font-bold transition-colors duration-200 ${
              currentPath === item.path
                ? "bg-[#6D98B0] text-white"
                : "text-[#2E3A59] hover:bg-[#6D98B0]/60"
            }`}
          >
            {item.name}
          </Link>
        ))}
      </nav>
    </div>
  );
};

export default Sidebar;
