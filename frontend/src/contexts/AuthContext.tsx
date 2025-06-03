// src/contexts/AuthContext.tsx
import React, { createContext, useState, useEffect, type ReactNode } from 'react';
import type { LoginResponse } from '../services/api'; // Importando a interface
 // Importando a interface

interface AuthContextType {
  isAuthenticated: boolean;
  token: string | null;
  isLoading: boolean;
  login: (data: LoginResponse) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true); // Para verificar token inicial

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      setToken(storedToken);
      // Aqui você pode adicionar uma chamada para validar o token com o backend se necessário
    }
    setIsLoading(false);
  }, []);

  const login = (data: LoginResponse) => {
    const newToken = data.access_token;
    setToken(newToken);
    localStorage.setItem('token', newToken);
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem('token');
    // Aqui você pode querer redirecionar para a página de login ou home
    // Ex: window.location.href = '/login'; (se não estiver usando react-router para isso)
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated: !!token, token, isLoading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};