<!-- src/views/HumanResource/Register.vue -->
<template>
  <div class="min-h-screen bg-gray-50 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="sm:mx-auto sm:w-full sm:max-w-md">
      <h2 class="text-center text-3xl font-bold tracking-tight text-gray-900">
        Create new account
      </h2>
    </div>

    <div class="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
        <form class="space-y-6" @submit.prevent="handleRegister">
          <!-- Alert for errors -->
          <div v-if="error" class="mb-4 p-4 rounded-md bg-red-50 border border-red-200">
            <div class="flex">
              <div class="flex-shrink-0">
                <svg class="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd" />
                </svg>
              </div>
              <div class="ml-3">
                <h3 class="text-sm font-medium text-red-800">
                  Registration Error
                </h3>
                <div class="mt-2 text-sm text-red-700">
                  {{ error }}
                </div>
              </div>
            </div>
          </div>

          <!-- Username -->
          <div>
            <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
            <div class="mt-1">
              <input
                id="username"
                v-model="formData.username"
                type="text"
                required
                class="input-field"
                :class="{ 'border-red-500': v$.username.$error }"
              />
              <p v-if="v$.username.$error" class="mt-1 text-sm text-red-600">
                {{ v$.username.$errors[0].$message }}
              </p>
            </div>
          </div>

          <!-- Password -->
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
            <div class="mt-1">
              <input
                id="password"
                v-model="formData.password"
                type="password"
                required
                class="input-field"
                :class="{ 'border-red-500': v$.password.$error }"
              />
              <p v-if="v$.password.$error" class="mt-1 text-sm text-red-600">
                {{ v$.password.$errors[0].$message }}
              </p>
            </div>
          </div>

          <!-- Name -->
          <div>
            <label for="name" class="block text-sm font-medium text-gray-700">Full Name</label>
            <div class="mt-1">
              <input
                id="name"
                v-model="formData.name"
                type="text"
                required
                class="input-field"
                :class="{ 'border-red-500': v$.name.$error }"
              />
              <p v-if="v$.name.$error" class="mt-1 text-sm text-red-600">
                {{ v$.name.$errors[0].$message }}
              </p>
            </div>
          </div>

          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
            <div class="mt-1">
              <input
                id="email"
                v-model="formData.email"
                type="email"
                required
                class="input-field"
                :class="{ 'border-red-500': v$.email.$error }"
              />
              <p v-if="v$.email.$error" class="mt-1 text-sm text-red-600">
                {{ v$.email.$errors[0].$message }}
              </p>
            </div>
          </div>

          <!-- Role (Only for admin) -->
          <div v-if="isAdmin">
            <label for="role" class="block text-sm font-medium text-gray-700">Role</label>
            <div class="mt-1">
              <select
                id="role"
                v-model="formData.role"
                class="input-field"
                required
              >
                <option value="ROLE_USER">User</option>
                <option value="ROLE_ADMIN">Admin</option>
              </select>
            </div>
          </div>

          <div>
            <button
              type="submit"
              :disabled="isLoading"
              class="w-full btn-primary"
            >
              <span v-if="isLoading" class="inline-flex items-center">
                <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Processing...
              </span>
              <span v-else>Create Account</span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useVuelidate } from '@vuelidate/core'
import { required, email, minLength, helpers } from '@vuelidate/validators'
import axios from '@/plugins/axios'

const router = useRouter()
const authStore = useAuthStore()
const error = ref('')
const isLoading = ref(false)

const isAdmin = computed(() => {
  return authStore.user?.role === 'ROLE_ADMIN'
})

const formData = reactive({
  username: '',
  password: '',
  name: '',
  email: '',
  role: 'ROLE_USER'
})

// Validation rules
const rules = {
  username: {
    required: helpers.withMessage('Username is required', required),
    minLength: helpers.withMessage('Username must be at least 3 characters', minLength(3))
  },
  password: {
    required: helpers.withMessage('Password is required', required),
    minLength: helpers.withMessage('Password must be at least 6 characters', minLength(6))
  },
  name: {
    required: helpers.withMessage('Name is required', required),
    minLength: helpers.withMessage('Name must be at least 2 characters', minLength(2))
  },
  email: {
    required: helpers.withMessage('Email is required', required),
    email: helpers.withMessage('Please enter a valid email address', email)
  }
}

const v$ = useVuelidate(rules, formData)

const handleRegister = async () => {
  try {
    const isValid = await v$.value.$validate()
    if (!isValid) {
      error.value = 'Please check the form for errors'
      return
    }

    isLoading.value = true
    error.value = ''

    const response = await axios.post('/api/auth/register', {
      username: formData.username,
      password: formData.password,
      name: formData.name,
      email: formData.email,
      role: isAdmin.value ? formData.role : 'ROLE_USER'  // 관리자만 역할 지정 가능
    })

    if (response.data) {
      // 성공 메시지 표시
      alert('User registered successfully!')

      // 관리자가 생성한 경우 사용자 목록으로, 그렇지 않으면 로그인 페이지로
      if (isAdmin.value) {
        await router.push('/hr/users')
      } else {
        await router.push('/login')
      }
    }
  } catch (err) {
    console.error('Registration error:', err)
    if (err.response?.data?.message) {
      error.value = err.response.data.message
    } else if (err.response?.status === 409) {
      error.value = 'Username or email already exists'
    } else {
      error.value = 'Failed to create account. Please try again.'
    }
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.input-field {
  @apply block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-blue-500 focus:outline-none focus:ring-blue-500 sm:text-sm;
}

.btn-primary {
  @apply flex w-full justify-center rounded-md border border-transparent bg-blue-600 py-2 px-4 text-sm font-medium text-white shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed;
}
</style>
