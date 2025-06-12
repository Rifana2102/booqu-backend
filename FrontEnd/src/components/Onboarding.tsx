import React from "react";
import styles from "../styles/Onboarding.module.css";

const Onboarding: React.FC = () => (
  <div className={styles.wrapper}>
    <div className={styles.logo}>B</div>
    <h1 className={styles.title}>Selamat Datang</h1>
    <p className={styles.subtitle}>
      Daftar sekarang dan nikmati fitur lengkap kami
    </p>
    <button className={styles.input}>Buat Akun</button>
    <button className={styles.input}>Masuk</button>
    <button className={styles.button}>Masuk sebagai tamu</button>
  </div>
);

export default Onboarding;