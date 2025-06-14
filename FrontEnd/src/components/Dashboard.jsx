import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Dashboard.css";

function Dashboard() {
  const [searchTerm, setSearchTerm] = useState("");
  const [currentUser, setCurrentUser] = useState("Non Guest");
  const [selectedCategory, setSelectedCategory] = useState("Crime");
  const navigate = useNavigate();

  // Cek apakah user sudah login
  useEffect(() => {
    const userData = localStorage.getItem('user');
    if (!userData) {
      navigate('/login');
    } else {
      try {
        const user = JSON.parse(userData);
        setCurrentUser(user.username || "Non Guest");
      } catch (error) {
        console.error("Error parsing user data:", error);
        navigate('/login');
      }
    }
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/login');
  };

  // Data buku untuk grid - 8 buku sesuai gambar
  const allBooks = [
    { id: 1, image: "1GG.jpg", alt: "Book 1" },
    { id: 2, image: "2IEWS.jpg", alt: "Book 2" },
    { id: 3, image: "3TFIOS.jpg", alt: "Book 3" },
    { id: 4, image: "4TPOM.jpg", alt: "Book 4" },
    { id: 5, image: "5AH.jpg", alt: "Book 5" },
    { id: 6, image: "6BHOT.jpg", alt: "Book 6" },
    { id: 7, image: "7TSG.jpg", alt: "Book 7" },
    { id: 8, image: "8AGGGTM.jpg", alt: "Book 8" }
  ];

  // Data untuk section bawah - sesuai gambar
  const onLoanBooks = [
    { id: 1, image: "1GG.jpg", title: "" }
  ];

  const bukuTerfavorit = [
    { id: 1, image: "3TFIOS.jpg", title: "It" }
  ];

  const penulisFavorit = [
    { id: 1, image: "7TSG.jpg", title: "" }
  ];

  const getImageUrl = (imageName) => {
    return `http://localhost:6060/uploads/books/images/${imageName}`;
  };

  const handleCategoryClick = (category) => {
    setSelectedCategory(category);
  };

  return (
    <div className="dashboard">
      {/* Sidebar */}
      <div className="sidebar">
        <div className="logo">Booqu</div>
        <nav className="nav-menu">
          <div className="nav-item active">
            Dashboard
          </div>
          <div className="nav-item">
            On loan
          </div>
          <div className="nav-item">
            Discover
          </div>
          <div className="nav-item">
            My Library
          </div>
          <div className="nav-item">
            Waiting List
          </div>
          <div className="nav-item">
            Setting
          </div>
          <div className="nav-item logout" onClick={handleLogout}>
            Log out
          </div>
        </nav>
      </div>

      {/* Main Content */}
      <div className="main-content">
        {/* Header */}
        <div className="header">
          <div className="search-container">
            <span className="search-icon">🔍</span>
            <input
              type="text"
              placeholder="Search"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="search-input"
            />
          </div>
          <div className="user-info">
            <span className="notification-icon">🔔</span>
            <div className="user-avatar">
              <span>Ise</span>
            </div>
          </div>
        </div>

        {/* Welcome Section */}
        <div className="welcome-section">
          <div className="welcome-content">
            <div className="welcome-text">
              <h1>YOUR POCKET LIBRARY</h1>
              <h2>Pinjam Buku, Gampang!</h2>
            </div>
            <div className="welcome-image">
              <img src={getImageUrl("book-illustration.png")} alt="Book illustration" />
            </div>
          </div>
        </div>

        {/* Book Categories */}
        <div className="categories-section">
          <h3>Book category</h3>
          <div className="category-tabs">
            <button className="category-tab active">Crime</button>
            <button className="category-tab">Romance</button>
            <button className="category-tab">Science</button>
            <button className="category-tab">Psychology</button>
          </div>
          
          <div className="books-grid">
            {allBooks.map(book => (
              <div key={book.id} className="book-card">
                <img 
                  src={getImageUrl(book.image)} 
                  alt={book.alt}
                  className="book-cover"
                />
              </div>
            ))}
          </div>
        </div>

        {/* Bottom Sections */}
        <div className="bottom-sections">
          {/* On Loan */}
          <div className="section">
            <h4>On loan</h4>
            <div className="books-row">
              {onLoanBooks.map(book => (
                <div key={book.id} className="book-item">
                  <img 
                    src={getImageUrl(book.image)} 
                    alt={book.title}
                    className="book-thumbnail"
                  />
                  {book.title && <p className="book-title">{book.title}</p>}
                </div>
              ))}
            </div>
          </div>

          {/* Buku Terfavorit */}
          <div className="section">
            <h4>Buku Terfavorit</h4>
            <div className="books-row">
              {bukuTerfavorit.map(book => (
                <div key={book.id} className="book-item">
                  <img 
                    src={getImageUrl(book.image)} 
                    alt={book.title}
                    className="book-thumbnail"
                  />
                  {book.title && <p className="book-title">{book.title}</p>}
                </div>
              ))}
            </div>
          </div>

          {/* Penulis Favorit */}
          <div className="section">
            <h4>Penulis Favorit</h4>
            <div className="books-row">
              {penulisFavorit.map(book => (
                <div key={book.id} className="book-item">
                  <img 
                    src={getImageUrl(book.image)} 
                    alt={book.title}
                    className="book-thumbnail"
                  />
                  {book.title && <p className="book-title">{book.title}</p>}
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
