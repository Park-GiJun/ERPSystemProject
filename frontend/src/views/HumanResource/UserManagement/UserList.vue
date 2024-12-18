<!-- src/views/HumanResource/UserManagement/UserList.vue -->
<template>
  <div>
    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center min-h-[400px]">
      <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent"></div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="bg-red-50 p-4 rounded-md text-red-700">
      {{ error }}
    </div>

    <!-- Content -->
    <div v-else>
      <!-- Users Table -->
      <div class="bg-white shadow-sm rounded-lg overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
          <tr>
            <th class="table-header">Username</th>
            <th class="table-header">Name</th>
            <th class="table-header">Email</th>
            <th class="table-header">Role</th>
            <th class="table-header">Status</th>
            <th class="table-header">Last Login</th>
            <th class="table-header">Actions</th>
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
                  {{ formatRole(user.role) }}
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
                  class="icon-button"
                  :title="user.enabled ? 'Deactivate' : 'Activate'"
                >
                  <Power class="w-5 h-5" />
                </button>
                <button
                  @click="resetPassword(user)"
                  class="icon-button"
                  title="Reset Password"
                >
                  <Key class="w-5 h-5" />
                </button>
              </div>
            </td>
          </tr>
          <!-- Empty State -->
          <tr v-if="users.length === 0">
            <td colspan="7" class="px-6 py-8 text-center text-gray-500">
              No users found
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <div class="mt-4 flex items-center justify-between">
        <div class="text-sm text-gray-700">
          Showing {{ startIndex + 1 }} to {{ endIndex }} of {{ totalElements }} results
        </div>
        <div class="flex space-x-2">
          <button
            @click="changePage(currentPage - 1)"
            class="pagination-button"
            :disabled="currentPage === 0"
          >
            Previous
          </button>
          <button
            v-for="pageNum in displayedPages"
            :key="pageNum"
            @click="changePage(pageNum)"
            class="pagination-button"
            :class="{ 'bg-blue-50 text-blue-600': pageNum === currentPage }"
          >
            {{ pageNum + 1 }}
          </button>
          <button
            @click="changePage(currentPage + 1)"
            class="pagination-button"
            :disabled="currentPage >= totalPages - 1"
          >
            Next
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Plus, Power, Key } from 'lucide-vue-next'
import { userManagementApi } from '@/api/userManagement'

const users = ref([])
const isLoading = ref(false)
const error = ref(null)

// 페이지네이션 상태
const currentPage = ref(0)
const pageSize = ref(10)
const totalElements = ref(0)
const totalPages = ref(0)

// 페이지네이션 계산
const startIndex = computed(() => currentPage.value * pageSize.value)
const endIndex = computed(() => Math.min(startIndex.value + pageSize.value, totalElements.value))

// 표시할 페이지 번호 계산
const displayedPages = computed(() => {
  const pages = []
  const totalPagesNum = totalPages.value
  const current = currentPage.value

  let start = Math.max(0, current - 2)
  let end = Math.min(totalPagesNum - 1, start + 4)

  if (end - start < 4) {
    start = Math.max(0, end - 4)
  }

  for (let i = start; i <= end; i++) {
    pages.push(i)
  }

  return pages
})

const fetchUsers = async (page = 0) => {
  isLoading.value = true
  error.value = null

  try {
    const response = await userManagementApi.getUsers(page, pageSize.value)
    users.value = response.content
    totalElements.value = response.totalElements
    totalPages.value = response.totalPages
    currentPage.value = response.number
  } catch (err) {
    error.value = err.message
  } finally {
    isLoading.value = false
  }
}

const changePage = (page) => {
  if (page >= 0 && page < totalPages.value) {
    fetchUsers(page)
  }
}

const toggleUserStatus = async (user) => {
  try {
    await userManagementApi.toggleUserStatus(user.id)
    await fetchUsers(currentPage.value)
  } catch (error) {
    alert(error.message)
  }
}

const resetPassword = async (user) => {
  if (!confirm(`Are you sure you want to reset the password for ${user.username}?`)) return

  try {
    await userManagementApi.resetPassword(user.id)
    alert('Password has been reset successfully')
  } catch (error) {
    alert(error.message)
  }
}

const formatDate = (date) => {
  if (!date) return 'Never'
  return new Date(date).toLocaleString()
}

const formatRole = (role) => {
  return role === 'ROLE_ADMIN' ? 'Admin' : 'User'
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

.icon-button {
  @apply text-gray-400 hover:text-gray-500 focus:outline-none p-1 rounded-full hover:bg-gray-100;
}

.pagination-button {
  @apply px-3 py-1 border rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed;
}
</style>
