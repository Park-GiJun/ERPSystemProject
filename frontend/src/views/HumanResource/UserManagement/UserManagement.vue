<!-- src/views/HumanResource/UserManagement.vue -->
<template>
  <div class="p-6">
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-semibold text-gray-900">User Management</h1>
      <button
        @click="router.push('/register')"
        class="btn-primary"
      >
        <Plus class="w-5 h-5 mr-2" />
        Add User
      </button>
    </div>

    <!-- Users Table -->
    <div class="bg-white shadow-sm rounded-lg overflow-hidden">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
        <tr>
          <th scope="col" class="table-header">Username</th>
          <th scope="col" class="table-header">Name</th>
          <th scope="col" class="table-header">Email</th>
          <th scope="col" class="table-header">Role</th>
          <th scope="col" class="table-header">Status</th>
          <th scope="col" class="table-header">Last Login</th>
          <th scope="col" class="table-header">Actions</th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <tr v-for="user in users" :key="user.id">
          <td class="table-cell">{{ user.username }}</td>
          <td class="table-cell">{{ user.name }}</td>
          <td class="table-cell">{{ user.email }}</td>
          <td class="table-cell">
              <span :class="[
                'px-2 py-1 text-xs font-medium rounded-full',
                user.role === 'ROLE_ADMIN' ? 'bg-purple-100 text-purple-800' : 'bg-blue-100 text-blue-800'
              ]">
                {{ user.role === 'ROLE_ADMIN' ? 'Admin' : 'User' }}
              </span>
          </td>
          <td class="table-cell">
              <span :class="[
                'px-2 py-1 text-xs font-medium rounded-full',
                user.enabled ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
              ]">
                {{ user.enabled ? 'Active' : 'Inactive' }}
              </span>
          </td>
          <td class="table-cell">{{ formatDate(user.lastLoginAt) }}</td>
          <td class="table-cell">
            <div class="flex space-x-2">
              <button
                @click="toggleUserStatus(user)"
                class="text-gray-400 hover:text-gray-500"
                :title="user.enabled ? 'Deactivate' : 'Activate'"
              >
                <Power class="w-5 h-5" />
              </button>
              <button
                @click="resetPassword(user)"
                class="text-gray-400 hover:text-gray-500"
                title="Reset Password"
              >
                <Key class="w-5 h-5" />
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Plus, Power, Key } from 'lucide-vue-next'
import axios from '@/plugins/axios'

const router = useRouter()
const users = ref([])

// 사용자 목록 조회
const fetchUsers = async (page = 0) => {
  isLoading.value = true;
  error.value = null;

  try {
    const response = await axios.get<PageResponseDTO<MemberDTO>>('/api/members', {
      params: {
        page,
        size: pageSize.value
      }
    });

    users.value = response.data.content;
    totalElements.value = response.data.totalElements;
    totalPages.value = response.data.totalPages;
    currentPage.value = response.data.number;
  } catch (err) {
    console.error('Failed to fetch users:', err);
    error.value = 'Failed to load users. Please try again later.';
  } finally {
    isLoading.value = false;
  }
};

// 사용자 상태 토글
const toggleUserStatus = async (user) => {
  try {
    await axios.patch(`/api/users/${user.id}/toggle-status`)
    await fetchUsers() // 목록 새로고침
  } catch (error) {
    console.error('Failed to toggle user status:', error)
  }
}

// 비밀번호 초기화
const resetPassword = async (user) => {
  if (!confirm(`Are you sure you want to reset the password for ${user.username}?`)) return

  try {
    await axios.post(`/api/users/${user.id}/reset-password`)
    alert('Password has been reset successfully')
  } catch (error) {
    console.error('Failed to reset password:', error)
    alert('Failed to reset password')
  }
}

// 날짜 포맷팅
const formatDate = (date) => {
  if (!date) return 'Never'
  return new Date(date).toLocaleString()
}

onMounted(() => {
  fetchUsers()
})
</script>

<style scoped>
.table-header {
  @apply px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider;
}

.table-cell {
  @apply px-6 py-4 whitespace-nowrap text-sm text-gray-900;
}

.btn-primary {
  @apply inline-flex items-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500;
}
</style>
