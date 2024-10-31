// src/services/projectService.js
import axios from 'axios';

const API_URL = '/api/projects';

export const projectService = {
  async createProject(projectData) {
    // 날짜 데이터 포맷팅
    const formattedData = {
      ...projectData,
      startDate: projectData.startDate,
      endDate: projectData.endDate,
      memberIds: projectData.members.map(member => member.id),
      technologies: projectData.technologies.map(tech => ({
        category: tech.category,
        technologyName: tech.name,
        version: tech.version
      }))
    };

    const response = await axios.post(API_URL, formattedData);
    return response.data;
  },

  async getMembers() {
    const response = await axios.get('/api/members');
    return response.data;
  },
};
