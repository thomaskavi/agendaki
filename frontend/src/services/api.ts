import axios, { type AxiosResponse } from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080', // ajuste conforme seu backend
});

// Intercepta as requisições e adiciona o Authorization se houver token
API.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

interface LoginResponse {
  token: any;
  access_token: string;
  token_type: string;
  expires_in: number;
  scope: string;
}

export function login(username: string, password: string): Promise<AxiosResponse<LoginResponse>> {
  return axios.post('http://localhost:8080/public/login', {
    username,
    password
  });
}

export default API;