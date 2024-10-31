// src/stores/projectStore.js
import { defineStore } from 'pinia'
import axios from '../plugins/axios'

export const useProjectStore = defineStore('project', {
  state: () => ({
    projects: [],
    currentProject: null,
    loading: false,
    error: null
  }),

  actions: {
    async createProject(projectData) {
      this.loading = true;
      this.error = null;
      try {
        const formattedData = {
          name: projectData.name,
          description: projectData.description,
          startDate: projectData.startDate,
          endDate: projectData.endDate,
          contractAmount: projectData.contractAmount,
          requiredMembers: projectData.requiredMembers,
          members: projectData.members.map(member => ({
            memberId: member.id,
            role: member.role
          })),
          technologies: projectData.technologies.map(tech => ({
            category: tech.category,
            technologyName: tech.name,
            version: tech.version || null
          }))
        };

        const response = await axios.post('/api/projects', formattedData);
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || '프로젝트 생성 중 오류가 발생했습니다.';
        throw new Error(this.error);
      } finally {
        this.loading = false;
      }
    },

    async getProjects(page = 0, size = 10, search = '', status = '') {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get('/api/projects', {
          params: {
            page,
            size,
            search,
            status
          }
        });

        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || '프로젝트 목록을 불러오는데 실패했습니다.';
        throw error;
      } finally {
        this.loading = false;
      }
    },

    async getProjectById(id) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get(`/api/projects/${id}`);
        this.currentProject = response.data;
        return response.data;
      } catch (error) {
        this.error = error.response?.data?.message || '프로젝트 정보를 불러오는데 실패했습니다.';
        throw new Error(this.error);
      } finally {
        this.loading = false;
      }
    },


  }
});
