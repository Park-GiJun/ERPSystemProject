// src/stores/projectDetailStore.js
import { defineStore } from 'pinia';
import { projectApi } from '@/api/projectAPI';

export const useProjectDetailStore = defineStore('projectDetail', {  // 이름을 'projectDetail'로 변경
  state: () => ({
    currentProject: null,
    team: [],
    tasks: [],
    analytics: null,
    loading: false,
    error: null
  }),

  actions: {
    async fetchProjectDetail(id) {
      this.loading = true;
      try {
        const { data } = await projectApi.getProjectDetail(id);
        this.currentProject = data;
      } catch (error) {
        this.error = error.response?.data?.message || 'Failed to load project details';
        throw error;
      } finally {
        this.loading = false;
      }
    },

    async fetchProjectTeam(id) {
      try {
        const { data } = await projectApi.getProjectTeam(id);
        this.team = data;
      } catch (error) {
        this.error = error.response?.data?.message || 'Failed to load team members';
        throw error;
      }
    },

    async fetchProjectTasks(id) {
      try {
        const { data } = await projectApi.getProjectTasks(id);
        this.tasks = data;
      } catch (error) {
        this.error = error.response?.data?.message || 'Failed to load tasks';
        throw error;
      }
    },

    async fetchProjectAnalytics(id) {
      try {
        const { data } = await projectApi.getProjectAnalytics(id);
        this.analytics = data;
      } catch (error) {
        this.error = error.response?.data?.message || 'Failed to load analytics';
        throw error;
      }
    }
  }
});
