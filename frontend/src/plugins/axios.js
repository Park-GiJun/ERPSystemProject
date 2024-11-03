// src/plugins/axios.js
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

// 현재 환경과 모드 로깅
console.log("=== Axios Instance Configuration ===");
console.log("Current Environment:", import.meta.env.VITE_APP_ENV || "not specified");
console.log("Current Mode:", import.meta.env.MODE || "not specified");

const baseURL = import.meta.env.VITE_API_URL;

// baseURL 로깅
console.log("Axios Base URL:", baseURL);

// axios 인스턴스 생성
const instance = axios.create({
  baseURL: baseURL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': '*/*'
  }
});

// 요청 인터셉터
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    // 요청 정보 로깅
    console.log('Starting Request:', {
      environment: import.meta.env.VITE_APP_ENV || "not specified",
      url: config.url,
      method: config.method,
      headers: config.headers,
      data: config.data,
      params: config.params
    });

    return config;
  },
  (error) => {
    console.error('Request Error:', error);
    return Promise.reject(error);
  }
);

// 응답 인터셉터
instance.interceptors.response.use(
  (response) => {
    // 응답 정보 로깅
    console.log('Response:', {
      environment: import.meta.env.VITE_APP_ENV || "not specified",
      url: response.config.url,
      status: response.status,
      data: response.data,
      headers: response.headers,
      method: response.config.method
    });

    return response;
  },
  async (error) => {
    const originalRequest = error.config;

    // 401 에러 처리
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      // 로그아웃 처리
      const authStore = useAuthStore();
      await authStore.logout();

      return Promise.reject(error);
    }

    // 그 외 에러 처리 및 에러 로깅
    if (error.response) {
      console.error('API Error:', {
        environment: import.meta.env.VITE_APP_ENV || "not specified",
        url: originalRequest.url,
        status: error.response.status,
        data: error.response.data,
        headers: error.response.headers
      });
    } else {
      console.error('Network Error:', error.message);
    }

    return Promise.reject(error);
  }
);

export default instance;
