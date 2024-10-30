// stores/auth.js
import { defineStore } from 'pinia'
import axios from '../plugins/axios'
import router from '../router'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || null,
    username: null,
    tokenType: null,
    role:null,
    isInitialized: false
  }),

  getters: {
    isAuthenticated: (state) => !!state.accessToken,
  },

  actions: {
    async initializeAuth() {
      try {
        const token = localStorage.getItem('accessToken')
        if (token) {
          // 토큰 유효성 검증을 위한 API 호출
          const response = await axios.get('/api/auth/validate-token')
          if (response.data.valid) {
            this.accessToken = token
            this.username = localStorage.getItem('username')
            this.tokenType = localStorage.getItem('tokenType')
            this.role = localStorage.getItem('role')
          } else {
            await this.logout()
          }
        }
      } catch (error) {
        console.error('Auth initialization failed:', error)
        await this.logout()
      } finally {
        this.isInitialized = true
      }
    },

    async login(username, password) {
      try {
        const response = await axios.post('/api/auth/login', {
          username,
          password,
        })

        const { accessToken, username: responseUsername, tokenType, role } = response.data

        // 상태 및 로컬 스토리지 업데이트
        this.accessToken = accessToken
        this.username = responseUsername
        this.tokenType = tokenType
        this.role = role

        localStorage.setItem('accessToken', accessToken)
        localStorage.setItem('username', responseUsername)
        localStorage.setItem('tokenType', tokenType)
        localStorage.setItem('role', role)

        console.log('Login successful. Redirecting to dashboard...')

        // 로그인 성공 후 대시보드로 이동
        await router.push('/dashboard')

        return true
      } catch (error) {
        console.error('Login failed:', error)
        throw new Error(error.response?.data?.message || '로그인에 실패했습니다.')
      }
    },

    async logout() {
      try {
        if (this.accessToken) {
          await axios.post('/api/auth/logout')
        }
      } catch (error) {
        console.error('Logout failed:', error)
      } finally {
        // 상태 및 로컬 스토리지 클리어
        this.accessToken = null
        this.username = null
        this.tokenType = null
        this.role = null
        localStorage.removeItem('accessToken')
        localStorage.removeItem('username')
        localStorage.removeItem('tokenType')
        localStorage.removeItem('role')

        await router.push('/login')
      }
    }
  }
})
