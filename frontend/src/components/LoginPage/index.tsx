// src/components/LoginPage/index.tsx
import { useState, type FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './styles.module.css'; // Seu CSS existente
import { loginUser, getErrorMessage } from '../../services/api'; // Usando a função renomeada
import { useAuth } from '../../hooks/useAuth';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const auth = useAuth();

  async function sendLoginRequest(event: FormEvent) {
    event.preventDefault();
    setIsLoading(true);
    setErrorMessage('');

    if (!email || !senha) {
      setErrorMessage('Por favor, preencha todos os campos.');
      setIsLoading(false);
      return;
    }

    try {
      const response = await loginUser(email, senha); // Usando a função da API real
      auth.login(response.data); // Atualiza o contexto de autenticação
      navigate('/'); // Redireciona para a Home após login bem-sucedido
    } catch (error) {
      console.error('Erro ao fazer login:', error);
      setErrorMessage(getErrorMessage(error));
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className={styles.container}>
      <form onSubmit={sendLoginRequest} className={styles.form} noValidate>
        <h1 className={styles.title}>AgendaKi</h1>

        <div className={styles.inputGroup}>
          <label htmlFor="email" className="visually-hidden"> {/* Usando classe global */}
            Email
          </label>
          <input
            id="email"
            type="email"
            name="email"
            placeholder="Insira seu email"
            className={`${styles.input} ${errorMessage && (email === '' || errorMessage.toLowerCase().includes('email') || errorMessage.toLowerCase().includes('credenciais')) ? styles.inputError : ''}`}
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            disabled={isLoading}
            aria-describedby="formError"
            required
            autoComplete="username"
          />
        </div>

        <div className={styles.inputGroup}>
          <label htmlFor="senha" className="visually-hidden">
            Senha
          </label>
          <input
            id="senha"
            type="password"
            name="senha"
            placeholder="Insira sua senha"
            className={`${styles.input} ${errorMessage && (senha === '' || errorMessage.toLowerCase().includes('senha') || errorMessage.toLowerCase().includes('credenciais')) ? styles.inputError : ''}`}
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            disabled={isLoading}
            aria-describedby="formError"
            required
            autoComplete="current-password"
          />
        </div>

        {errorMessage && (
          <p id="formError" className={styles.errorMessage} aria-live="assertive">
            {errorMessage}
          </p>
        )}

        <button type="submit" className={styles.button} disabled={isLoading}>
          {isLoading ? (
            <>
              <span className={styles.spinner}></span>
              Entrando...
            </>
          ) : (
            'Entrar'
          )}
        </button>
        {/* Links como "Criar conta" ou "Esqueci senha" podem ser adicionados aqui */}
      </form>
    </div>
  );
}

export default LoginPage;