// src/services/api.ts
import axios, { AxiosError, type AxiosResponse } from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // Ajuste conforme seu backend

const API = axios.create({
  baseURL: API_BASE_URL,
});

// Intercepta as requisições e adiciona o Authorization se houver token
API.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Tipagem para a resposta do login
export interface LoginResponse {
  access_token: string;
  token_type?: string;
  expires_in?: number;
  scope?: string;
  // Adicione outros campos que sua API de login possa retornar (ex: refresh_token, dados do usuário)
}

// Tipagem para o Professional (baseado nos DTOs fornecidos)
export interface Professional {
  id: bigint;
  name: string;
  email: string;
  phone: string;
  profileImageUrl?: string;
  slug: string;
  profession: string;
}

export interface ServiceOfferedDTO {
  id: bigint;
  name: string;
  description: string;
  price: number;
  // adicione outros campos se necessário
}

export interface ProfessionalPublicDTO extends Professional {
  services: ServiceOfferedDTO[];
}

// NOVO: Tipagem para slots disponíveis
export interface AvailableSlotDTO {
  id: bigint;
  startTime: string; // ISO 8601 string para data e hora (ex: "2025-06-02T10:00:00Z")
  endTime: string;   // ISO 8601 string
  professionalId: bigint;
  // Outros campos se houver (ex: duration, isBooked)
}

// NOVO: DTO para criar um agendamento (o que será enviado para o backend)
export interface CreateAppointmentDTO {
  professionalId: bigint;
  serviceId: bigint;
  appointmentTime: string; // Deve ser a data e hora do slot selecionado (ISO 8601 string)
  // clientId NÃO deve ser enviado do frontend. O backend deve inferir do token JWT.
}

// NOVO: DTO de resposta de um agendamento (o que o backend retorna após criar)
export interface AppointmentDTO {
  id: bigint;
  professionalName: string;
  serviceName: string;
  clientName: string; // Nome do cliente que agendou
  appointmentTime: string; // ISO 8601 string
  status: string; // Ex: PENDING, CONFIRMED, COMPLETED, CANCELED
  // Outros campos do seu AppointmentDTO no backend
}


// Função de Login
export function loginUser(username: string, password: string): Promise<AxiosResponse<LoginResponse>> {
  return axios.post(`${API_BASE_URL}/public/login`, {
    username,
    password
  });
}

// Função para buscar profissionais (para a Home)
export function getProfessionals(page: number = 0, size: number = 5, sort: string = 'name,asc'): Promise<AxiosResponse<{ content: Professional[] }>> {
  return API.get(`/professionals?page=${page}&size=${size}&sort=${sort}`);
}

// Função para buscar detalhes de um profissional por slug - PÚBLICA
export function getProfessionalBySlug(slug: string): Promise<AxiosResponse<ProfessionalPublicDTO>> {
  return API.get(`/professionals/agdk/${slug}`);
}

// NOVA FUNÇÃO: Para buscar detalhes de um profissional por ID - PÚBLICA
// É necessário para a página de agendamento se você não passou o slug.
// ASSUMA QUE SEU BACKEND TEM UMA ROTA PÚBLICA GET /professionals/{id}
export function getProfessionalById(id: bigint): Promise<AxiosResponse<ProfessionalPublicDTO>> {
  return API.get(`/professionals/${id}`);
}

// Lista todos os agendamentos do usuário logado (cliente)
export function getClientSelfAppointments(): Promise<AxiosResponse<AppointmentDTO[]>> {
  return API.get('/appointments/my');
}

// Lista todos os agendamentos recebidos por um profissional autenticado
export function getProfessionalReceivedAppointments(): Promise<AxiosResponse<AppointmentDTO[]>> {
  return API.get('/appointments/received');
}

// NOVA FUNÇÃO: Para criar um agendamento - PROTEGIDA (requer autenticação)
export function createAppointment(data: CreateAppointmentDTO): Promise<AxiosResponse<AppointmentDTO>> {
  return API.post(`/appointments`, data); // Este endpoint requer autenticação no backend
}


// Tratamento de erro do Axios mais genérico
export const getErrorMessage = (error: unknown): string => {
  if (axios.isAxiosError(error)) {
    const axiosError = error as AxiosError<{ message?: string; error?: string; details?: string }>;
    if (axiosError.response?.data) {
      const data = axiosError.response.data;
      return data.message || data.error || data.details || axiosError.message;
    }
    return axiosError.message;
  }
  if (error instanceof Error) {
    return error.message;
  }
  return 'Ocorreu um erro inesperado.';
};

export default API;