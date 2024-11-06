<!-- src/views/HumanResource/HRLayout.vue -->
<template>
  <div class="h-full p-8">
    <!-- Header Section -->
    <div class="mb-6">
      <h1 class="text-2xl font-bold text-gray-900">Human Resources</h1>
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
                isActiveTab(tab.path)
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
            v-if="route.path.startsWith('/hr/users')"
            to="/hr/users/register"
            class="inline-flex items-center px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700"
          >
            <UserPlus class="w-5 h-5 mr-2" />
            Register User
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
  Users,
  UserPlus,
  UsersIcon,
  ClipboardList,
  Settings,
  BarChart3
} from 'lucide-vue-next';

const route = useRoute();

const navigationTabs = [
  {
    name: 'User List',
    path: '/hr/users',
    icon: Users,
  },
  {
    name: 'Team List',
    path: '/hr/users/teamList',  // 수정된 팀 리스트 경로
    icon: UsersIcon,
  }
  // 아직 구현되지 않은 라우트는 주석 처리
  // {
  //   name: 'HR Dashboard',
  //   path: '/hr/dashboard',
  //   icon: BarChart3,
  // },
  // {
  //   name: 'Settings',
  //   path: '/hr/settings',
  //   icon: Settings,
  // }
];

// 현재 경로가 탭의 경로로 시작하는지 확인
const isActiveTab = (path) => {
  return route.path.startsWith(path);
};
</script>
