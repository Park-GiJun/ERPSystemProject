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
          <div>
            <label for="username" class="block text-sm font-medium text-gray-700">
              아이디
            </label>
            <div class="mt-1">
              <input
                id="username"
                v-model="username"
                type="text"
                required
                class="input-field"
              />
            </div>
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-gray-700">
              비밀번호
            </label>
            <div class="mt-1">
              <input
                id="password"
                v-model="password"
                type="password"
                required
                class="input-field"
              />
            </div>
          </div>

          <div>
            <button type="submit" class="w-full btn-primary">
              로그인
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const username = ref('')
const password = ref('')

const handleLogin = async () => {
  try {
    await authStore.login(username.value, password.value)
    router.push('/dashboard')
  } catch (error) {
    // 에러 처리
    console.error('Login failed:', error)
  }
}
</script>
