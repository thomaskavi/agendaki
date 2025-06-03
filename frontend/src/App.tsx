// src/App.tsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Outlet, Link } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';

import Header from './components/Header';
import LoginPage from './components/LoginPage';
import HomePage from './components/HomePage';
import ProfessionalDetailPage from './components/ProfessionalDetailPage';
import ProtectedRoute from './components/ProtectedRoute';
import ScheduleAppointmentPage from './components/ScheduleAppointmentPage'; // Importe o novo componente

// Layout principal que inclui o Header
const MainLayout: React.FC = () => (
  <>
    <Header />
    <Outlet /> {/* Rotas filhas serão renderizadas aqui */}
  </>
);

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          {/* Rotas que usam o MainLayout (com Header) */}
          <Route element={<MainLayout />}>
            <Route path="/" element={<HomePage />} />
            {/* Rota de Detalhes do Profissional JÁ ESTÁ PÚBLICA, FORA DO PROTECTED ROUTE */}
            <Route path="/agdk/:slug" element={<ProfessionalDetailPage />} />

            {/* Rotas Protegidas - Agendamento, Perfil do Usuário, etc. */}
            <Route element={<ProtectedRoute />}>
              {/* Nova rota para agendamento. professionalId e serviceId são parâmetros. serviceId é opcional. */}
              <Route path="/agendar/:professionalId/:serviceId?" element={<ScheduleAppointmentPage />} />
              {/* Outras rotas protegidas podem ir aqui, ex: /meu-perfil */}
            </Route>
          </Route>

          {/* Rota de Login não usa o MainLayout (não tem Header padrão ou tem um diferente) */}
          <Route path="/login" element={<LoginPage />} />

          {/* Adicionar uma rota para Not Found (404) */}
          <Route path="*" element={
            <MainLayout_NotFound>
              <div style={{ textAlign: 'center', padding: '50px', fontSize: '1.5rem', color: 'white' }}>
                <h1>404 - Página Não Encontrada</h1>
                <p>A página que você está procurando não existe.</p>
                <Link to="/" style={{ color: '#007bff', textDecoration: 'none' }}>Voltar para Início</Link>
              </div>
            </MainLayout_NotFound>
          } />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

// Layout simples para a página 404 que pode incluir o header se desejado
const MainLayout_NotFound: React.FC<{children: React.ReactNode}> = ({children}) => (
  <>
    <Header /> {/* Ou um header específico para erro */}
    {children}
  </>
);

export default App;