import { useState } from 'react'
import styles from './styles.module.css'
import { login } from '../../services/api'

function LoginPage() {
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')

  async function sendLoginRequest(event: React.FormEvent) {
    event.preventDefault()

    try {
      const response = await login(email, senha)

      const token = response.data.access_token
      localStorage.setItem('token', token)
      console.log('Login bem-sucedido! Token:', token)

      // redirecionar, atualizar estado global etc.

    } catch (error) {
      console.error('Erro ao fazer login:', error)
      alert('Usuário ou senha inválidos')
    }
  }

  return (
    <div className={styles.container}>
      <form onSubmit={sendLoginRequest} className={styles.form}>
        <input
          type="email"
          name="email"
          placeholder="Insira seu email"
          className={styles.input}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="password"
          name="senha"
          placeholder="Insira sua senha"
          className={styles.input}
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        <button type="submit" className={styles.button}>
          Entrar
        </button>
      </form>
    </div>
  )
}

export default LoginPage
