// src/components/ProtectedRoute/index.tsx
import React from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

interface ProtectedRouteProps {
  // Você pode adicionar roles/permissions aqui se necessário
  // roles?: string[];
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = () => {
  const { isAuthenticated, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    // Pode mostrar um spinner global aqui enquanto verifica a autenticação
    return <div>Verificando autenticação...</div>;
  }

  if (!isAuthenticated) {
    // Redireciona para a página de login, guardando a localização atual
    // para que o usuário possa ser redirecionado de volta após o login.
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <Outlet />; // Renderiza o componente filho (rota protegida)
};

export default ProtectedRoute;