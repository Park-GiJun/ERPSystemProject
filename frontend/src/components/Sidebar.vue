<!-- src/components/Sidebar.vue -->
<template>
  <div class="h-screen flex">
    <div class="w-64 bg-gray-900 text-white flex flex-col">
      <!-- Company Logo -->
      <div class="p-4 border-b border-gray-800">
        <h1 class="text-xl font-bold">ERP System</h1>
      </div>

      <!-- Navigation Menu -->
      <nav class="flex-1 overflow-y-auto">
        <div class="px-4 py-2 text-xs font-semibold text-gray-400 uppercase">Main</div>
        <ul class="mt-2">
          <!-- Dashboard -->
          <li>
            <router-link
              to="/dashboard"
              class="flex items-center px-4 py-3 text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
              :class="{ 'bg-gray-800 text-white': isCurrentRoute('/dashboard') }"
            >
              <LayoutDashboard class="w-5 h-5 mr-3" />
              Dashboard
            </router-link>
          </li>

          <!-- Projects with submenu -->
          <li>
            <div>
              <button
                @click="toggleProjectMenu"
                class="w-full flex items-center px-4 py-3 text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                :class="{ 'bg-gray-800 text-white': isProjectRoute }"
              >
                <Briefcase class="w-5 h-5 mr-3" />
                <span class="flex-1 text-left">Projects</span>
                <ChevronDown
                  class="w-4 h-4 transition-transform duration-200"
                  :class="{ 'rotate-180': isProjectMenuOpen }"
                />
              </button>

              <!-- Project Submenu -->
              <div v-show="isProjectMenuOpen || isProjectRoute" class="bg-gray-850">
                <router-link
                  to="/projects/list"
                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/projects/list') }"
                >
                  <ListTodo class="w-4 h-4 mr-2" />
                  Project List
                </router-link>
                <router-link
                  to="/projects/milestones"
                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/projects/milestones') }"
                >
                  <Milestone class="w-4 h-4 mr-2" />
                  Milestones
                </router-link>
                <router-link
                  to="/projects/dashboard"
                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/projects/dashboard') }"
                >
                  <BarChart class="w-4 h-4 mr-2" />
                  Project Dashboard
                </router-link>
                <router-link
                  to="/projects/settings"
                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/projects/settings') }"
                >
                  <Settings class="w-4 h-4 mr-2" />
                  Settings
                </router-link>
              </div>
            </div>
          </li>

          <!-- HR with submenu -->
          <li>
            <div>
              <button
                @click="toggleHRMenu"
                class="w-full flex items-center px-4 py-3 text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                :class="{ 'bg-gray-800 text-white': isHRRoute }"
              >
                <Users class="w-5 h-5 mr-3" />
                <span class="flex-1 text-left">Human Resources</span>
                <ChevronDown
                  class="w-4 h-4 transition-transform duration-200"
                  :class="{ 'rotate-180': isHRMenuOpen }"
                />
              </button>

              <!-- HR Submenu -->
              <div v-show="isHRMenuOpen || isHRRoute" class="bg-gray-850">
                <router-link
                  to="/hr/users"
                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/hr/users') }"
                >
                  User List
                </router-link>
<!--                <router-link-->
<!--                  to="/hr/users/register"-->
<!--                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"-->
<!--                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/hr/users/register') }"-->
<!--                >-->
<!--                  Register User-->
<!--                </router-link>-->
                <router-link
                  to="/hr/users/teamList"
                  class="flex items-center pl-12 py-2 text-sm text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
                  :class="{ 'bg-gray-800 text-white': isCurrentRoute('/hr/users/teamList') }"
                >
                  Team List
                </router-link>
              </div>
            </div>
          </li>

          <!-- Other menu items... -->
          <li>
            <router-link
              to="/calendar"
              class="flex items-center px-4 py-3 text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
              :class="{ 'bg-gray-800 text-white': isCurrentRoute('/calendar') }"
            >
              <Calendar class="w-5 h-5 mr-3" />
              Calendar
            </router-link>
          </li>
        </ul>

        <!-- Management Menu -->
        <div class="px-4 py-2 mt-4 text-xs font-semibold text-gray-400 uppercase">Management</div>
        <ul class="mt-2">
          <li v-for="(item, index) in managementMenuItems" :key="index">
            <router-link
              :to="item.path"
              class="flex items-center px-4 py-3 text-gray-300 hover:bg-gray-800 hover:text-white transition-colors"
              :class="{ 'bg-gray-800 text-white': isCurrentRoute(item.path) }"
            >
              <component :is="item.icon" class="w-5 h-5 mr-3" />
              {{ item.title }}
            </router-link>
          </li>
        </ul>
      </nav>

      <!-- User Profile -->
      <div class="p-4 border-t border-gray-800">
        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <div class="w-8 h-8 rounded-full bg-gray-600 flex items-center justify-center">
              <span class="text-sm">{{ userInitials }}</span>
            </div>
            <div class="ml-3">
              <p class="text-sm font-medium">{{ authStore.username }}</p>
              <p class="text-xs text-gray-400">{{ userRole }}</p>
            </div>
          </div>
          <button
            @click="handleLogout"
            class="p-2 rounded hover:bg-gray-800"
          >
            <LogOut class="w-5 h-5 text-gray-400" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import {
  LayoutDashboard,
  Users,
  Building2,
  FileSpreadsheet,
  Calendar,
  MessagesSquare,
  Settings,
  BarChart,
  FileText,
  Briefcase,
  LogOut,
  ChevronDown,
  ListTodo,
  Milestone
} from 'lucide-vue-next'

const route = useRoute()
const authStore = useAuthStore()

const isHRMenuOpen = ref(false)
const isProjectMenuOpen = ref(false)

const toggleHRMenu = () => {
  isHRMenuOpen.value = !isHRMenuOpen.value
}

const toggleProjectMenu = () => {
  isProjectMenuOpen.value = !isProjectMenuOpen.value
}

// HR 메뉴가 활성화되었는지 확인
const isHRRoute = computed(() => {
  return route.path.startsWith('/hr')
})

// 프로젝트 메뉴가 활성화되었는지 확인
const isProjectRoute = computed(() => {
  return route.path.startsWith('/projects')
})

const userRole = computed(() => {
  const role = authStore.role || ''
  return role.includes('ADMIN') ? 'System Admin' : 'Employee'
})

const userInitials = computed(() => {
  const username = authStore.username || ''
  return username.substring(0, 2).toUpperCase()
})

const managementMenuItems = [
  { title: 'Documents', path: '/documents', icon: FileText },
  { title: 'Company', path: '/company', icon: Building2 },
  { title: 'Reports', path: '/reports', icon: FileSpreadsheet },
  { title: 'Messages', path: '/messages', icon: MessagesSquare },
  { title: 'Settings', path: '/settings', icon: Settings },
]

const isCurrentRoute = (path) => {
  return route.path === path
}

const handleLogout = () => {
  authStore.logout()
}
</script>

<style scoped>
.bg-gray-850 {
  background-color: rgba(26, 32, 44, 1);
}
</style>
