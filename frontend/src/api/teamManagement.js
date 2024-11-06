// src/api/teamManagement.js
import axios from '@/plugins/axios'

/**
 * @typedef {Object} TeamMember
 * @property {number} memberId
 * @property {string} name
 * @property {string} email
 * @property {string} position
 * @property {string} responsibility
 * @property {boolean} isTeamLeader
 */

/**
 * @typedef {Object} Team
 * @property {number} id
 * @property {string} teamName
 * @property {string} description
 * @property {number} teamLevel
 * @property {number|null} parentTeamId
 * @property {string|null} parentTeamName
 * @property {Team[]} children
 * @property {TeamMember[]} members
 */

/**
 * @typedef {Object} PageResponse
 * @property {Team[]} content
 * @property {number} totalElements
 * @property {number} totalPages
 * @property {number} number
 * @property {number} size
 * @property {boolean} first
 * @property {boolean} last
 */

export const teamApi = {
  /**
   * 전체 팀 계층 구조 조회 (페이징)
   * @param {number} page - 페이지 번호
   * @param {number} size - 페이지 크기
   * @returns {Promise<PageResponse>}
   */
  getAllTeams: async (page = 0, size = 10) => {
    try {
      const response = await axios.get("/api/teams", {
        params: { page, size }
      });
      return response.data;
    } catch (error) {
      console.error('Failed to fetch teams:', {
        message: error.message,
        response: error.response?.data,
        status: error.response?.status
      });

      if (error.response) {
        throw new Error(error.response.data.message || '팀 목록을 불러오는데 실패했습니다.');
      }

      throw new Error('서버와 통신하는데 실패했습니다.');
    }
  }
};
