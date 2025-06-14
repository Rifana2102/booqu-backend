import React, { useState } from 'react';
import { FaSearch, FaUserCircle, FaBell, FaBars, FaTimes } from 'react-icons/fa';

const Dashboard = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };
  const top3Books = [
    { title: 'The Selfish Gene', author: 'Richard Dawkins', image: '/buku/richard.jpg' },
    { title: 'It Ends With Us', author: 'Colleen Hoover', image: '/buku/it_end.jpg' },
    { title: 'The Psychology of Money', author: 'Morgan Housel', image: '/buku/money.jpg' },
  ];

  const onLoanBooks = [
    { title: 'Lord of the Rings', author: 'J.R.R. Tolkien', image: '/buku/richard.jpg' },
    { title: 'A Fault in Our Stars', author: 'John Green', image: '/buku/the_fault.jpg' },
  ];
  
  const bukuTerfavorit = [
    { title: 'It Ends With Us', author: 'Colleen Hoover', image: '/buku/it_end.jpg' },
    { title: 'Atomic Habits', author: 'James Clear', image: '/buku/atomic.jpg' },
  ];

  const penulisFavorit = [
    { title: 'The Selfish Gene', author: 'Richard Dawkins', image: '/buku/richard.jpg' },
    { title: 'A Good Girl\'s Guide to Murder', author: 'Holly Jackson', image: '/buku/a_good_girl.jpg' },
  ];

  const categories = [
    { genre: 'Crime', image: '/buku/a_brief.jpg' },
    { genre: 'Romance', image: '/buku/a_good_girl.jpg' },
    { genre: 'Science', image: '/buku/atomic.jpg' },
    { genre: 'Psychology', image: '/buku/richard.jpg' },
    { genre: 'Drama', image: '/buku/the_fault.jpg' },
    { genre: 'Thriller', image: '/buku/gilian.jpg' },
  ];
  return (
    <div className="min-h-screen bg-white font-serif text-[#333]">
      {/* Sidebar */}
      <div className={`fixed left-0 top-0 h-full bg-[#77B5CD] text-gray-700 shadow-lg transition-all duration-300 z-50 ${
        isSidebarOpen ? 'w-60' : 'w-0 overflow-hidden'
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
            Waiting List
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

      {/* Main Content */}
      <div className={`transition-all duration-300 ${isSidebarOpen ? 'ml-60' : 'ml-0'}`}>        {/* Header */}
        <div className="flex justify-between items-center p-4 border-b bg-white">
          <div className="flex items-center gap-4 w-full">
            {/* Hamburger Menu Button */}
            <button 
              onClick={toggleSidebar}
              className="text-2xl text-gray-600 hover:text-gray-800 p-2 rounded-lg hover:bg-gray-100 transition-colors"
            >
              {isSidebarOpen ? <FaTimes /> : <FaBars />}
            </button>
            
            <div className="flex-grow flex items-center border border-gray-400 rounded-full px-4 py-2 bg-white shadow-sm">
              <FaSearch className="text-gray-500 mr-3" />
              <input
                type="text"
                placeholder="Search books, authors, genres..."
                className="outline-none text-lg w-full bg-transparent text-gray-700 font-serif"
              />
            </div>
            <div className="flex items-center gap-2">
              <FaBell className="text-xl text-gray-600 cursor-pointer hover:text-gray-800 transition-colors" 
                     onClick={() => alert('Notifikasi')} />
              <FaUserCircle className="text-3xl text-gray-600 cursor-pointer hover:text-gray-800 transition-colors"
                           onClick={() => alert('Profil User')} />
            </div>
          </div>
        </div>        {/* Your Pocket Library Section */}
        <div className="bg-gradient-to-r from-[#B8E3F0] to-[#7DD3F0] px-8 py-16 flex items-center justify-between relative overflow-hidden">
          {/* Left side with stacked books */}
          <div className="relative flex-shrink-0 ml-8">
            {/* Book stack - positioned like in the image */}
            <div className="relative w-64 h-40">
              {/* Back book */}
              <img src="/buku/richard.jpg" alt="The Selfish Gene" 
                   className="absolute top-2 left-8 w-28 h-40 object-cover shadow-lg transform rotate-12 z-10 cursor-pointer hover:scale-105 transition-transform" 
                   onClick={() => alert('Klik buku: The Selfish Gene')} />
              {/* Middle book */}
              <img src="/buku/it_end.jpg" alt="It Ends With Us" 
                   className="absolute top-0 left-0 w-28 h-40 object-cover shadow-xl transform -rotate-6 z-20 cursor-pointer hover:scale-105 transition-transform" 
                   onClick={() => alert('Klik buku: It Ends With Us')} />
              {/* Front book */}
              <img src="/buku/money.jpg" alt="Psychology of Money" 
                   className="absolute top-4 left-16 w-28 h-40 object-cover shadow-2xl transform rotate-3 z-30 cursor-pointer hover:scale-105 transition-transform" 
                   onClick={() => alert('Klik buku: Psychology of Money')} />
            </div>
          </div>
          
          {/* Center/Right side with text content */}
          <div className="flex-grow text-center pr-8">
            <p className="text-lg tracking-wider text-gray-600 mb-4 font-normal uppercase cursor-pointer hover:text-gray-800 transition-colors"
               onClick={() => alert('Klik YOUR POCKET LIBRARY')}>
              YOUR POCKET LIBRARY
            </p>
            <h1 className="text-6xl font-bold text-white font-serif leading-tight cursor-pointer hover:text-gray-100 transition-colors"
                onClick={() => alert('Klik Pinjam Buku, Gampang!')}>
              Pinjam Buku, Gampang!
            </h1>
          </div>
          
          {/* Floating decorative elements */}
          <div className="absolute top-8 left-40 w-2 h-2 bg-white/40 rounded-full"></div>
          <div className="absolute bottom-12 right-32 w-3 h-3 bg-white/30 rounded-full"></div>
          <div className="absolute top-20 right-20 w-1.5 h-1.5 bg-yellow-300/70 rounded-full"></div>
        </div>{/* Book Category Section */}
        <div className="bg-[#DDECF2] px-8 pt-6 pb-4">
          <div className="bg-white rounded-lg shadow p-8">
            <div className="text-center mb-8">
              <h3 className="text-3xl mb-6 font-bold font-serif text-gray-800">Book category</h3>
              <div className="flex justify-center gap-12 text-gray-500 text-xl font-serif mb-8">
                <span className="hover:text-gray-700 cursor-pointer">Crime</span>
                <span className="hover:text-gray-700 cursor-pointer">Romance</span>
                <span className="hover:text-gray-700 cursor-pointer">Science</span>
                <span className="hover:text-gray-700 cursor-pointer">Psychology</span>
              </div>
            </div>
            
            {/* Book covers in horizontal row */}
            <div className="flex justify-center gap-6 overflow-x-auto pb-4">
              <img src="/buku/money.jpg" alt="Psychology of Money" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/it_end.jpg" alt="It Ends With Us" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/a_brief.jpg" alt="A Brief History" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/a_good_girl.jpg" alt="A Good Girl's Guide" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/atomic.jpg" alt="Atomic Habits" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/richard.jpg" alt="The Selfish Gene" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/the_fault.jpg" alt="The Fault in Our Stars" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
              <img src="/buku/gilian.jpg" alt="Gone Girl" className="w-24 h-36 object-cover rounded-lg shadow-lg hover:shadow-xl transition-shadow cursor-pointer" />
            </div>
          </div>
        </div>

        {/* Bottom Section */}
        <div className="bg-[#DDECF2] px-8 pt-4 pb-10">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {/* On loan */}
            <div className="bg-white p-4 rounded-lg shadow">
              <h4 className="text-lg font-semibold border-b pb-2 mb-3 font-serif">On loan</h4>
              {onLoanBooks.map((book, i) => (
                <div key={i} className="flex gap-3 items-center mb-3">
                  <img src={book.image} alt={book.title} className="w-14 h-20 object-cover rounded" />
                  <div className="text-sm font-serif">
                    <p className="font-bold">{book.title}</p>
                    <p className="text-gray-600 italic">{book.author}</p>
                  </div>
                </div>
              ))}
            </div>

            {/* Buku Terfavorit */}
            <div className="bg-white p-4 rounded-lg shadow">
              <h4 className="text-lg font-semibold border-b pb-2 mb-3 font-serif">Buku Terfavorit</h4>
              {bukuTerfavorit.map((book, i) => (
                <div key={i} className="flex gap-3 items-center mb-3">
                  <img src={book.image} alt={book.title} className="w-14 h-20 object-cover rounded" />
                  <div className="text-sm font-serif">
                    <p className="font-bold">{book.title}</p>
                    <p className="text-gray-600 italic">{book.author}</p>
                  </div>
                </div>
              ))}
            </div>

            {/* Penulis Favorit */}
            <div className="bg-white p-4 rounded-lg shadow">
              <h4 className="text-lg font-semibold border-b pb-2 mb-3 font-serif">Penulis Favorit</h4>
              {penulisFavorit.map((book, i) => (
                <div key={i} className="flex gap-3 items-center mb-3">
                  <img src={book.image} alt={book.title} className="w-14 h-20 object-cover rounded" />
                  <div className="text-sm font-serif">
                    <p className="font-bold">{book.title}</p>
                    <p className="text-gray-600 italic">{book.author}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;