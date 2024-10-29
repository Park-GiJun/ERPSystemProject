<template>
  <div class="h-full w-64 bg-white border-r border-gray-200">
    <div class="flex flex-col h-full">
      <!-- Logo -->
      <div class="flex items-center justify-center h-16 border-b border-gray-200">
        <h1 class="text-xl font-bold text-primary">ERP System</h1>
      </div>

      <!-- Navigation -->
      <nav class="flex-1 overflow-y-auto">
        <div class="px-2 py-4 space-y-1">
          <router-link
            v-for="item in menuItems"
            :key="item.path"
            :to="item.path"
            class="flex items-center px-4 py-2 text-sm font-medium rounded-lg"
            :class="[
              route.path === item.path
                ? 'bg-primary text-white'
                : 'text-gray-600 hover:bg-gray-50'
            ]"
          >
            <component :is="item.icon" class="w-5 h-5 mr-3" />
            {{ item.name }}
          </router-link>
        </div>
      </nav>

      <!-- User Profile -->
      <div class="border-t border-gray-200 p-4">
        <div class="flex items-center">
          <div class="flex-shrink-0">
            <span class="inline-block h-8 w-8 rounded-full bg-gray-200"></span>
          </div>
          <div class="ml-3">
            <p class="text-sm font-medium text-gray-700">{{ username }}</p>
            <button
              @click="handleLogout"
              class="text-sm text-gray-500 hover:text-gray-700"
            >
              로그아웃
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import {
  HomeIcon,
  UserGroupIcon,
  DocumentTextIcon,
  CogIcon,
} from '@heroicons/vue/24/outline'

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const username = computed(() => authStore.user?.username)

const menuItems = [
  {
    name: '대시보드',
    path: '/dashboard',
    icon: HomeIcon,
  },
  {
    name: '사원관리',
    path: '/employees',
    icon: UserGroupIcon,
  },
  {
    name: '문서관리',
    path: '/documents',
    icon: DocumentTextIcon,
  },
  {
    name: '설정',
    path: '/settings',
    icon: CogIcon,
  },
]

const handleLogout = async () => {
  await authStore.logout()
  router.push('/login')
}
</script>
