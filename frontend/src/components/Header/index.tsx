// src/components/Header/index.tsx

import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import styles from './styles.module.css';

function Header() {
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login'); // Redireciona para login após sair
  };

  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <Link to="/" className={styles.logo}>
          AgendaKi
        </Link>
        <nav className={styles.nav}>
          <Link to="/" className={styles.navLink}>
            Início
          </Link>
          {isAuthenticated ? (
            <button onClick={handleLogout} className={`${styles.navLink} ${styles.navButton}`}>
              Sair
            </button>
          ) : (
            <Link to="/login" className={`${styles.navLink} ${styles.navButton}`}>
              Login
            </Link>
          )}
          {/* Outros links de navegação podem ser adicionados aqui */}
          {/* Ex: <Link to="/meu-perfil" className={styles.navLink}>Meu Perfil</Link> */}
        </nav>
      </div>
    </header>
  );
}

export default Header;