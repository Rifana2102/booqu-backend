import React from 'react';
import { FaSearch, FaUserCircle } from 'react-icons/fa';

const DashboardGuest = () => {
  const top3Books = [
    { title: 'The Selfish Gene', author: 'Richard Dawkins', image: '/buku/richard.jpg' },
    { title: 'It Ends With Us', author: 'Colleen Hoover', image: '/buku/it_end.jpg' },
    { title: 'The Psychology of Money', author: 'Morgan Housel', image: '/buku/money.jpg' },
  ];

  const bukuTerbaru = [
    { title: 'The Selfish Gene', author: 'Richard Dawkins', image: '/buku/richard.jpg' },
    { title: 'The Fault In Our Stars', author: 'John Green', image: '/buku/the_fault.jpg' },
  ];

  const bukuFavorit = [
    { title: 'It Ends With Us', author: 'Colleen Hoover', image: '/buku/it_end.jpg' },
    { title: 'Atomic Habits', author: 'James Clear', image: '/buku/atomic.jpg' },
  ];

  const penulisFavorit = [
    { title: 'The Selfish Gene', author: 'Richard Dawkins', image: '/buku/richard.jpg' },
    { title: 'A Good Girl’s Guide to Murder', author: 'Holly Jackson', image: '/buku/a_good_girl.jpg' },
  ];

  const categories = [
    { genre: 'Crime', image: '/buku/a_brief.jpg' },
    { genre: 'Romance', image: '/buku/a_good_girl.jpg' },
    { genre: 'Science', image: '/buku/atomic.jpg' },
    { genre: 'Psychology', image: '/buku/richard.jpg' },
    { genre: 'Fantasy', image: '/buku/the_fault.jpg' },
    { genre: 'Horror', image: '/buku/gilian.jpg' },
  ];

  return (
    <div className="min-h-screen bg-white font-serif text-[#333]">
      {/* Header */}
      <div className="flex justify-between items-center p-4 border-b bg-white">
        <div className="bg-[#406784] text-white px-6 py-2 rounded-br-3xl text-3xl font-bold cursor-pointer"
          onClick={() => alert('Klik Booqu')}>
          Booqu
        </div>
        <div className="flex items-center gap-4 w-full pl-6">
          <div className="flex-grow flex items-center border border-gray-400 rounded-full px-4 py-2 bg-white shadow-sm">
            <FaSearch className="text-gray-500 mr-3" />
            <input
              type="text"
              placeholder="Search books, authors, genres..."
              className="outline-none text-lg w-full bg-transparent text-gray-700 font-serif"
            />
          </div>
          <FaUserCircle className="text-3xl text-gray-600 cursor-pointer" onClick={() => alert("Klik profil")} />
        </div>
      </div>

      {/* Top 3 Book Section */}
      <div className="bg-[#DDECF2] px-8 py-10 flex flex-col md:flex-row justify-between items-center">
        <div className="max-w-xl mb-8 md:mb-0 pl-4">
          <h2 className="text-5xl font-extrabold mb-2 text-[#1F2D3D] uppercase font-serif">
            TOP 3 BOOK
          </h2>
          <p className="text-lg text-gray-700 leading-relaxed font-serif">
            It's time to refresh your reading list with this week's Top 3 Books, featuring captivating stories,
            groundbreaking ideas, and inspiring perspectives that will keep you hooked from start to finish!
          </p>
        </div>
        <div className="relative h-64 w-[340px]">
          <img
            src={top3Books[2].image}
            className="absolute top-6 right-4 w-28 shadow-lg rotate-3 cursor-pointer"
            alt="3"
            onClick={() => alert(`Klik buku: ${top3Books[2].title}`)}
          />
          <img
            src={top3Books[1].image}
            className="absolute top-3 right-20 w-28 shadow-lg -rotate-2 cursor-pointer"
            alt="2"
            onClick={() => alert(`Klik buku: ${top3Books[1].title}`)}
          />
          <img
            src={top3Books[0].image}
            className="absolute top-0 right-36 w-32 z-10 shadow-2xl cursor-pointer"
            alt="1"
            onClick={() => alert(`Klik buku: ${top3Books[0].title}`)}
          />
        </div>
      </div>

      {/* Book Category Section */}
      <div className="bg-[#DDECF2] px-8 pt-6 pb-4">
        <div className="bg-white rounded-lg shadow p-6">
          <div className="text-center mb-8">
            <h3 className="text-2xl mb-4 font-bold font-serif">Book Category</h3>
            <div className="flex flex-wrap justify-center gap-6 text-gray-500 text-lg font-serif">
              {categories.map((cat, index) => (
                <span key={index} className="cursor-pointer" onClick={() => alert(`Klik kategori: ${cat.genre}`)}>
                  {cat.genre}
                </span>
              ))}
            </div>
          </div>
          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
            {categories.map((cat, idx) => (
              <div key={idx} className="flex justify-center">
                <img
                  src={cat.image}
                  alt={cat.genre}
                  className="w-20 h-28 object-cover rounded-md cursor-pointer"
                  onClick={() => alert(`Klik gambar kategori: ${cat.genre}`)}
                />
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Buku Terbaru, Favorit, Penulis Favorit */}
      <div className="bg-[#DDECF2] px-8 pt-4 pb-10">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {[{ title: "Buku Terbaru", data: bukuTerbaru }, { title: "Buku Terfavorit", data: bukuFavorit }, { title: "Penulis Favorit", data: penulisFavorit }].map((section, sIdx) => (
            <div key={sIdx} className="bg-white p-4 rounded-lg shadow">
              <h4 className="text-lg font-semibold border-b pb-2 mb-3 font-serif">{section.title}</h4>
              {section.data.map((book, i) => (
                <div key={i} className="flex gap-3 items-center mb-3">
                  <img
                    src={book.image}
                    alt={book.title}
                    className="w-14 h-20 object-cover rounded cursor-pointer"
                    onClick={() => alert(`Klik gambar: ${book.title}`)}
                  />
                  <div className="text-sm font-serif">
                    <p className="font-bold cursor-pointer" onClick={() => alert(`Klik judul: ${book.title}`)}>{book.title}</p>
                    <p className="text-gray-600 italic cursor-pointer" onClick={() => alert(`Klik penulis: ${book.author}`)}>{book.author}</p>
                  </div>
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DashboardGuest;
