<!-- src/views/Project/ProjectLayout.vue -->
<template>
  <div class="h-full p-8">
    <!-- Header Section -->
    <div class="mb-6">
      <h1 class="text-2xl font-bold text-gray-900">프로젝트 관리</h1>
      <div class="flex items-center justify-between mt-4">
        <div class="flex items-center space-x-4">
          <!-- Navigation Tabs -->
          <nav class="flex space-x-4">
            <router-link
              v-for="tab in navigationTabs"
              :key="tab.name"
              :to="tab.path"
              class="px-3 py-2 text-sm font-medium rounded-md"
              :class="[
                isCurrentPath(tab.path)
                  ? 'bg-blue-100 text-blue-700'
                  : 'text-gray-500 hover:text-gray-700'
              ]"
            >
              <component :is="tab.icon" class="w-5 h-5 mr-2 inline-block" />
              {{ tab.name }}
            </router-link>
          </nav>
        </div>
        <!-- Action Buttons -->
        <div class="flex items-center space-x-3">
          <router-link
            v-if="isCurrentPath('/projects/list')"
            to="/projects/create"
            class="inline-flex items-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700"
          >
            <PlusCircle class="w-5 h-5 mr-2" />
            새 프로젝트
          </router-link>
        </div>
      </div>
    </div>

    <!-- Main Content Area -->
    <div class="bg-white rounded-lg shadow">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router';
import {
  ListTodo,
  PlusCircle,
  Calendar,
  BarChart,
  Settings
} from 'lucide-vue-next';

const route = useRoute();

const navigationTabs = [
  {
    name: '프로젝트 목록',
    path: '/projects/list',
    icon: ListTodo,
  },
  {
    name: '마일스톤',
    path: '/projects/milestones',
    icon: Calendar,
  },
  {
    name: '프로젝트 현황',
    path: '/projects/dashboard',
    icon: BarChart,
  },
  {
    name: '설정',
    path: '/projects/settings',
    icon: Settings,
  },
];

const isCurrentPath = (path) => {
  return route.path === path;
};
</script>
