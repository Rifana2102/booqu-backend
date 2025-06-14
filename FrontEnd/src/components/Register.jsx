import React, { useState } from 'react';

const Register = () => {
    const [formData, setFormData] = useState({
        name: '',
        username: '',
        email: '',
        password: ''
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Form Submitted", formData);
        // Bisa ditambahkan request ke backend di sini
    };

    return (
        <div className="flex flex-col min-h-screen bg-white font-serif">
            {/* Header */}
            <header className="p-4 text-3xl font-bold text-[#77B5CD]">Booqu</header>

            {/* Main Content */}
            <main className="flex-grow flex items-center justify-center">
                <div className="bg-white p-8 rounded-lg shadow-md w-80 text-[#77B5CD] flex flex-col items-center">
                    {/* Logo */}
                    <div className="bg-[#77B5CD] text-white rounded-full w-40 h-40 flex items-center justify-center mb-8">
                        <span className="text-8xl font-bold" style={{ fontFamily: 'Habibi, serif' }}>
                            B
                        </span>
                    </div>

                    {/* Form */}
                    <form onSubmit={handleSubmit} className="w-full space-y-4">
                        <input
                            type="text"
                            name="name"
                            placeholder="Nama Lengkap"
                            value={formData.name}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border rounded shadow-sm focus:outline-none focus:ring-2 focus:ring-[#77B5CD] focus:border-[#77B5CD]"
                            required
                        />
                        <input
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={formData.username}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border rounded shadow-sm focus:outline-none focus:ring-2 focus:ring-[#77B5CD] focus:border-[#77B5CD]"
                            required
                        />
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border rounded shadow-sm focus:outline-none focus:ring-2 focus:ring-[#77B5CD] focus:border-[#77B5CD]"
                            required
                        />
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border rounded shadow-sm focus:outline-none focus:ring-2 focus:ring-[#77B5CD] focus:border-[#77B5CD]"
                            required
                        />
                        <button
                            type="submit"
                            className="w-full py-3 bg-[#77B5CD] text-white text-center rounded-md font-medium hover:bg-[#7ea2b5] transition"
                        >
                            Buat Akun
                        </button>

                        
                    </form>

                    {/* Login Link */}
                    <p className="text-sm text-center mt-4 font-serif">
                        Sudah punya akun?{' '}
                        <a
                            href="/login"
                            className="underline text-[#8ab0c8] hover:text-[#6d91a4] transition"
                        >
                            Masuk disini
                        </a>
                    </p>
                </div>
            </main>

            {/* Footer */}
            <footer className="text-center py-4 text-sm text-[#8ab0c8]">
                &copy; {new Date().getFullYear()} Booqu. Semua hak dilindungi.
            </footer>
        </div>
    );
};

export default Register;
