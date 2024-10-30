// plugins/axios.js
import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  }
})

// 요청 인터셉터
instance.interceptors.request.use(
  config => {
    const token = localStorage.getItem('accessToken')
    const tokenType = localStorage.getItem('tokenType')
    if (token && tokenType) {
      config.headers['Authorization'] = `${tokenType} ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 응답 인터셉터
instance.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      // 토큰이 만료되었거나 유효하지 않은 경우
      localStorage.removeItem('accessToken')
      localStorage.removeItem('username')
      localStorage.removeItem('tokenType')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default instance
