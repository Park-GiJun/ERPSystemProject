// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import MainLayout from '@/layouts/MainLayout.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    // 회원가입 라우트 추가
    path: '/register',
    name: 'Register',
    component: () => import('@/views/HumanResource/UserManagement/Register.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    component: MainLayout,
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/dashboard'
      },
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue')
      },
      // HR Routes
      {
        path: 'hr',
        component: () => import('@/views/HumanResource/HRLayout.vue'),
        children: [
          {
            path: '',
            redirect: '/hr/users'
          },
          {
            path: 'users',
            name: 'UserManagement',
            component: () => import('@/views/HumanResource/UserManagement/UserList.vue')
          },
          {
            path: 'users/register',
            name: 'UserRegister',
            component: () => import('@/views/HumanResource/UserManagement/Register.vue')
          },
          {
            path: 'users/teamList',
            name : 'TeamList',
            component: () => import('@/views/HumanResource/Team/TeamList.vue')
          }
          // {
          //   path: 'attendance',
          //   name: 'Attendance',
          //   component: () => import('@/views/HumanResource/Attendance/AttendanceList.vue')
          // },
          // {
          //   path: 'dashboard',
          //   name: 'HRDashboard',
          //   component: () => import('@/views/HumanResource/Dashboard/HRDashboard.vue')
          // },
          // {
          //   path: 'settings',
          //   name: 'HRSettings',
          //   component: () => import('@/views/HumanResource/Settings/HRSettings.vue')
          // }
        ]
      },
      // Project Routes
      {
        path: 'projects',
        component: () => import('@/views/Project/ProjectLayout.vue'),
        children: [
          {
            path: '',
            redirect: '/projects/list'
          },
          {
            path: 'list',
            name: 'ProjectList',
            component: () => import('@/views/Project/ProjectList.vue')
          },
          {
            path: 'create',
            name: 'ProjectCreate',
            component: () => import('@/views/Project/ProjectCreate.vue')
          },
          {
            path: ':id',
            name: 'ProjectDetail',
            component: () => import('@/views/Project/ProjectDetail.vue')
          },
          {
            path: 'milestones',
            name: 'ProjectMilestones',
            component: () => import('@/views/Project/Milestone/MilestoneList.vue')
          },
          {
            path: 'dashboard',
            name: 'ProjectDashboard',
            component: () => import('@/views/Project/Dashboard/ProjectDashboard.vue')
          },
          {
            path: 'settings',
            name: 'ProjectSettings',
            component: () => import('@/views/Project/Settings/ProjectSettings.vue')
          }
        ]
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guard
router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()

  if (!authStore.isInitialized) {
    await authStore.initializeAuth()
  }

  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const isAuthenticated = authStore.isAuthenticated

  if (requiresAuth && !isAuthenticated) {
    next('/login')
  } else if ((to.path === '/login' || to.path === '/register') && isAuthenticated) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
