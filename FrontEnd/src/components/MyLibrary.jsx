import React, { useState } from "react";
import Sidebar from "./Sidebar";
import Footer from "./Footer";
import { FaBars } from "react-icons/fa";

const MyLibrary = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const books = [
    {
      title: "The Selfish Gene",
      author: "Richard Dawkins",
      image: "/buku/richard.jpg",
      lastRead: "2024-10-12",
    },
    {
      title: "It Ends With Us",
      author: "Colleen Hoover",
      image: "/buku/it_end.jpg",
      lastRead: "2024-08-21",
    },
    {
      title: "Atomic Habits",
      author: "James Clear",
      image: "/buku/atomic.jpg",
      lastRead: "2024-09-05",
    },
    {
      title: "Gone Girl",
      author: "Gillian Flynn",
      image: "/buku/gilian.jpg",
      lastRead: "2024-11-02",
    },
    {
      title: "The Psychology of Money",
      author: "Morgan Housel",
      image: "/buku/money.jpg",
      lastRead: "2024-12-15",
    },
    {
      title: "The Fault in Our Stars",
      author: "Matt Haig",
      image: "/buku/the_fault.jpg",
      lastRead: "2024-07-01",
    },
  ];

  const handleBookClick = (book) => {
    alert(`Kamu memilih buku "${book.title}" oleh ${book.author}`);
  };

  return (
    <div className="flex flex-col min-h-screen bg-white text-[#333] font-serif">
      <div className="flex flex-1">
        {/* Sidebar */}
        <Sidebar isOpen={isSidebarOpen} onToggle={() => setIsSidebarOpen(!isSidebarOpen)} />

        {/* Main Content */}
        <div className={`flex-1 transition-all duration-300 ${isSidebarOpen ? "ml-60" : "ml-0"}`}>
          {/* Header */}
          <div className="bg-white shadow-md p-4 flex items-center justify-between sticky top-0 z-40 border-b">
            <FaBars className="text-2xl text-gray-600 cursor-pointer" onClick={() => setIsSidebarOpen(!isSidebarOpen)} />
            <h1 className="text-xl font-bold text-[#186B8B] font-serif">My Library</h1>
            <div></div>
          </div>

          {/* Book Grid */}
          <div className="p-6 bg-[#F4FAFC] min-h-screen">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
              {books.map((book, index) => (
                <div
                  key={index}
                  className="bg-white rounded-xl shadow-md p-3 hover:shadow-lg transition cursor-pointer hover:bg-gray-50 flex flex-col items-center"
                  onClick={() => handleBookClick(book)}
                >
                  <img
                    src={book.image}
                    alt={book.title}
                    className="w-full h-[260px] object-contain rounded-md"
                  />
                  <div className="text-center mt-3 font-serif">
                    <h3 className="text-lg font-semibold text-[#186B8B]">{book.title}</h3>
                    <p className="text-gray-600 text-sm italic">by {book.author}</p>
                    <p className="text-gray-500 text-sm">Last read: {book.lastRead}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default MyLibrary;
