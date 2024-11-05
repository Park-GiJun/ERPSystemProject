// src/api/userManagement.js
import axios from '@/plugins/axios'

/**
 * @typedef {Object} Member
 * @property {number} id
 * @property {string} username
 * @property {string} name
 * @property {string} email
 * @property {string} role
 * @property {boolean} enabled
 * @property {string|null} lastLoginAt
 * @property {string} createdAt
 * @property {string} updatedAt
 */

/**
 * @typedef {Object} PageResponse
 * @property {Member[]} content
 * @property {number} totalElements
 * @property {number} totalPages
 * @property {number} number
 * @property {boolean} first
 * @property {boolean} last
 */

export const userManagementApi = {
  /**
   * 사용자 목록을 조회합니다.
   * @param {number} page - 페이지 번호
   * @param {number} size - 페이지 크기
   * @returns {Promise<PageResponse>} 페이지네이션된 사용자 목록
   */
  getUsers: async (page = 0, size = 10) => {
    try {
      const response = await axios.get('/api/members', {
        params: { page, size }
      })
      return response.data
    } catch (error) {
      console.error('Failed to fetch users:', error)
      throw new Error('Failed to load users. Please try again later.')
    }
  },

  /**
   * 사용자의 활성화 상태를 토글합니다.
   * @param {number} userId - 사용자 ID
   * @returns {Promise<void>}
   */
  toggleUserStatus: async (userId) => {
    try {
      await axios.patch(`/api/members/${userId}/toggle-status`)
    } catch (error) {
      console.error('Failed to toggle user status:', error)
      throw new Error('Failed to update user status')
    }
  },

  /**
   * 사용자의 비밀번호를 초기화합니다.
   * @param {number} userId - 사용자 ID
   * @returns {Promise<void>}
   */
  resetPassword: async (userId) => {
    try {
      await axios.post(`/api/members/${userId}/reset-password`)
    } catch (error) {
      console.error('Failed to reset password:', error)
      throw new Error('Failed to reset password')
    }
  },

  /**
   * 새로운 사용자를 등록합니다.
   * @param {Object} userData - 사용자 등록 데이터
   * @returns {Promise<Member>} 생성된 사용자 정보
   */
  registerUser: async (userData) => {
    try {
      const response = await axios.post('/api/auth/register', userData)
      return response.data
    } catch (error) {
      console.error('Failed to register user:', error)
      throw new Error('Failed to register user')
    }
  }
}
