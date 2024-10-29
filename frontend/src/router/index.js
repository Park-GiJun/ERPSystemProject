import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    component: () => import('../layouts/MainLayout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/dashboard'
      },
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/DashboardView.vue'),
        meta: { title: '대시보드' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 라우트 가드
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const authStore = useAuthStore()

  // 루트 경로('/')로 접근하는 경우
  if (to.path === '/') {
    if (token) {
      // 토큰이 있으면 대시보드로
      next('/dashboard')
    } else {
      // 토큰이 없으면 로그인으로
      next('/login')
    }
    return
  }

  // 인증이 필요한 페이지에 접근하는 경우
  if (to.meta.requiresAuth) {
    if (!token) {
      next('/login')
      return
    }
  }

  // 이미 로그인한 상태에서 로그인 페이지에 접근하는 경우
  if (to.path === '/login' && token) {
    next('/dashboard')
    return
  }

  next()
})

export default router
