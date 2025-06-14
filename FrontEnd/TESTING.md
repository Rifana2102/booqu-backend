# CARA TEST DASHBOARD BOOQU

## 🚀 Quick Test Login

Untuk melihat dashboard tanpa perlu backend, gunakan akun dummy ini:

### Login Credentials:
```
Username: admin
Password: admin
```

### Alternatif Login:
```
Username: user
Password: user
```

```
Username: test  
Password: test
```

## 📋 Langkah Testing:

1. **Buka aplikasi** di browser: `http://localhost:5174/`

2. **Klik "Login"** atau navigasi ke `/login`

3. **Masukkan kredensial dummy**:
   - Username: `admin`
   - Password: `admin`

4. **Klik Submit** - Akan langsung redirect ke dashboard

5. **Dashboard akan menampilkan**:
   - Sidebar dengan menu navigasi
   - Welcome section "YOUR POCKET LIBRARY - Pinjam Buku, Gampang!"
   - Book categories (Crime, Romance, Science, Psychology)
   - Grid buku-buku dengan gambar dari backend
   - Section "On loan", "Buku Terfavorit", "Penulis Favorit"

## 🖼️ Gambar Buku

Dashboard akan mengambil gambar dari:
`http://localhost:6060/uploads/books/images/`

Jika backend tidak jalan, gambar tidak akan muncul, tapi layout dashboard tetap terlihat.

## 🔧 Troubleshooting

- **Gambar tidak muncul**: Pastikan backend berjalan di port 6060
- **Dashboard tidak muncul**: Cek console browser untuk error
- **Login gagal**: Pastikan menggunakan kredensial dummy yang benar

## 📱 Fitur Dashboard

- ✅ Authentication check
- ✅ User info di header  
- ✅ Search functionality
- ✅ Book categories dengan tabs
- ✅ Responsive design
- ✅ Logout function
- ✅ Modern UI dengan gradient background
