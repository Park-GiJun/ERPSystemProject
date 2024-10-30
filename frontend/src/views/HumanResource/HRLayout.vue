<template>
  <div class="p-8 bg-gray-50 min-h-screen">
    <!-- HR Section Header -->
    <div class="mb-8">
      <h1 class="text-3xl font-extrabold text-gray-900 tracking-tight">Human Resources</h1>
      <!-- Sub Navigation -->
      <nav class="mt-6">
        <ul class="flex space-x-6 border-b border-gray-300">
          <li v-for="item in menuItems" :key="item.path" class="pb-3">
            <router-link
              :to="item.path"
              class="relative px-4 py-2 text-base font-medium transition-colors duration-300"
              :class="[
                isActive(item.path)
                  ? 'text-blue-600 font-semibold after:absolute after:-bottom-[1px] after:left-1/2 after:-translate-x-1/2 after:w-full after:h-[2px] after:bg-blue-600'
                  : 'text-gray-500 hover:text-blue-500'
              ]"
            >
              {{ item.name }}
            </router-link>
          </li>
        </ul>
      </nav>
    </div>

    <!-- Sub-route content -->
    <div class="bg-white shadow-sm rounded-lg p-6">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()

const menuItems = [
  {
    name: 'User List',
    path: '/hr/users'
  },
  {
    name: 'Register User',
    path: '/hr/users/register'
  },
  // 다른 HR 관련 메뉴 항목들을 여기에 추가할 수 있습니다
]

const isActive = (path) => {
  return route.path === path // 정확히 일치하는 경로를 활성화 상태로 설정
}
</script>
