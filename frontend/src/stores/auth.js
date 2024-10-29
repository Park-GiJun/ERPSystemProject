// stores/auth.js
import { defineStore } from 'pinia'
import axios from '../plugins/axios'
import router from '../router'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
    user: null,
    isInitialized: false
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
  },

  actions: {
    async initializeAuth() {
      const token = localStorage.getItem('token')
      if (token) {
        try {
          // 토큰 유효성 검증을 위한 API 호출
          const response = await axios.get('/api/auth/validate-token', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
          if (response.data.valid) {
            this.token = token
            this.user = response.data.user
          } else {
            this.logout()
          }
        } catch (error) {
          this.logout()
        }
      }
      this.isInitialized = true
    },

    async login(username, password) {
      try {
        const response = await axios.post('/api/auth/login', {
          username,
          password,
        })
        this.token = response.data.token
        this.user = {
          username: response.data.username
        }
        localStorage.setItem('token', this.token)
        router.push('/dashboard')
      } catch (error) {
        throw new Error(error.response?.data?.message || '로그인에 실패했습니다.')
      }
    },

    async logout() {
      try {
        await axios.post('/api/auth/logout')
      } catch (error) {
        console.error('Logout failed:', error)
      } finally {
        this.token = null
        this.user = null
        localStorage.removeItem('token')
        router.push('/login')
      }
    }
  }
})
