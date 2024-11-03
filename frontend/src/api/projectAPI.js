// src/api/projectAPI.js
import axios from '@/plugins/axios';

export const projectApi = {
  // 프로젝트 상세 정보 조회
  getProjectDetail(id) {
    return axios.get(`/api/projects/${id}`);
  },

  // 프로젝트 팀원 목록 조회
  getProjectTeam(id) {
    return axios.get(`/api/projects/${id}/team`);
  },

  // 프로젝트 작업 목록 조회
  getProjectTasks(id) {
    return axios.get(`/api/projects/${id}/tasks`);
  },

  // 프로젝트 분석 데이터 조회
  getProjectAnalytics(id) {
    return axios.get(`/api/projects/${id}/analytics`);
  }
};
