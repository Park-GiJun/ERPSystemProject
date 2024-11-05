<!-- src/views/Login.vue -->
<template>
  <div class="min-h-screen bg-gray-50 flex flex-col justify-center">
    <div class="sm:mx-auto sm:w-full sm:max-w-md">
      <h2 class="text-center text-3xl font-bold tracking-tight text-gray-900">
        ERP System
      </h2>
      <p class="mt-2 text-center text-sm text-gray-600">
        로그인하여 시스템에 접속하세요
      </p>
    </div>

    <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <form class="space-y-6" @submit.prevent="handleLogin">
          <div v-if="error" class="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded relative" role="alert">
            <span class="block sm:inline">{{ error }}</span>
          </div>

          <div>
            <label for="username" class="block text-sm font-medium text-gray-700">아이디</label>
            <div class="mt-1">
              <input
                id="username"
                v-model="username"
                type="text"
                required
                :disabled="isLoading"
                class="input-field"
                autocomplete="username"
              />
            </div>
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-gray-700">비밀번호</label>
            <div class="mt-1">
              <input
                id="password"
                v-model="password"
                type="password"
                required
                :disabled="isLoading"
                class="input-field"
                autocomplete="current-password"
              />
            </div>
          </div>

          <div>
            <button
              type="submit"
              class="btn-primary"
              :disabled="isLoading || !username || !password"
            >
              <span v-if="isLoading" class="inline-flex items-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                로그인 중...
              </span>
              <span v-else>로그인</span>
            </button>
          </div>
        </form>

        <!-- 회원가입 섹션 추가 -->
        <div class="mt-6">
          <div class="relative">
            <div class="absolute inset-0 flex items-center">
              <div class="w-full border-t border-gray-300"></div>
            </div>
            <div class="relative flex justify-center text-sm">
              <span class="px-2 bg-white text-gray-500">또는</span>
            </div>
          </div>

          <div class="mt-6">
            <button
              type="button"
              @click="goToRegister"
              class="btn-secondary"
            >
              새 계정 만들기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'  // 추가
import { useAuthStore } from '@/stores/auth'

const router = useRouter()  // 추가
const authStore = useAuthStore()

const username = ref('')
const password = ref('')
const error = ref('')
const isLoading = ref(false)

const handleLogin = async () => {
  if (isLoading.value) return
  if (!username.value || !password.value) return

  error.value = ''
  isLoading.value = true

  try {
    await authStore.login(username.value, password.value)
  } catch (err) {
    error.value = err.message
  } finally {
    isLoading.value = false
  }
}

// 회원가입 페이지로 이동하는 함수 추가
const goToRegister = () => {
  router.push('/register')  // 경로를 /register로 변경
}
</script>

<style scoped>
.input-field {
  @apply block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-blue-500 sm:text-sm;
}

.btn-primary {
  @apply flex w-full justify-center rounded-md border border-transparent bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed;
}

/* 회원가입 버튼 스타일 추가 */
.btn-secondary {
  @apply flex w-full justify-center rounded-md border border-gray-300 bg-white py-2 px-4 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2;
}
</style>
