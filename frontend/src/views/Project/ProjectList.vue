<script setup>
import { ref, onMounted } from 'vue';
import { Search, MoreVertical } from 'lucide-vue-next';
import { useProjectStore } from '@/stores/projectStore';
import { storeToRefs } from 'pinia';
import { formatDate } from '@/utils/dateUtils';
import { getCurrentInstance } from 'vue';

const { proxy } = getCurrentInstance();
const projectStore = useProjectStore();
const { loading, error } = storeToRefs(projectStore);

// 페이지네이션 상태
const pageSize = ref(10);
const currentPage = ref(0);
const totalElements = ref(0);
const totalPages = ref(0);
const projects = ref([]);

// 필터 상태
const searchQuery = ref('');
const statusFilter = ref('');
const projectStatuses = ['PLANNED', 'IN_PROGRESS', 'COMPLETED', 'ON_HOLD', 'CANCELLED'];

const getStatusClass = (status) => {
  const classes = {
    PLANNED: 'bg-gray-100 text-gray-800',
    IN_PROGRESS: 'bg-blue-100 text-blue-800',
    COMPLETED: 'bg-green-100 text-green-800',
    ON_HOLD: 'bg-yellow-100 text-yellow-800',
    CANCELLED: 'bg-red-100 text-red-800'
  };
  return classes[status] || 'bg-gray-100 text-gray-800';
};

// 프로젝트 목록 조회
const fetchProjects = async () => {
  try {
    const response = await projectStore.getProjects(currentPage.value, pageSize.value);
    projects.value = response.content;
    totalElements.value = response.totalElements;
    totalPages.value = response.totalPages;
    currentPage.value = response.number;
  } catch (err) {
    proxy.$toast.error('프로젝트 목록을 불러오는데 실패했습니다.');
  }
};

// 페이지 변경 처리
const handlePageChange = (page) => {
  currentPage.value = page;
  fetchProjects();
};

onMounted(() => {
  fetchProjects();
});

// 프로젝트 메뉴
const openProjectMenu = (project) => {
  // TODO: 프로젝트 메뉴 처리
  console.log('Open menu for project:', project.id);
};
</script>

<template>
  <div class="p-6">
    <!-- Search and Filter Section -->
    <div class="mb-6 flex justify-between items-center">
      <div class="flex-1 max-w-xs">
        <div class="relative">
          <input
            type="text"
            v-model="searchQuery"
            placeholder="프로젝트 검색..."
            class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
          />
          <Search class="absolute left-3 top-2.5 h-5 w-5 text-gray-400" />
        </div>
      </div>
      <div class="flex items-center space-x-4">
        <select
          v-model="statusFilter"
          class="border border-gray-300 rounded-md py-2 pl-3 pr-10 focus:ring-blue-500 focus:border-blue-500"
        >
          <option value="">모든 상태</option>
          <option v-for="status in projectStatuses" :key="status" :value="status">
            {{ status }}
          </option>
        </select>
      </div>
    </div>

    <!-- 로딩 상태 -->
    <div v-if="loading" class="flex justify-center py-8">
      <div class="animate-spin rounded-full h-8 w-8 border-2 border-blue-500 border-t-transparent"></div>
    </div>

    <!-- 에러 메시지 -->
    <div v-else-if="error" class="p-4 mb-4 bg-red-50 text-red-600 rounded-md">
      {{ error }}
    </div>

    <!-- Projects List -->
    <div v-else class="overflow-hidden shadow ring-1 ring-black ring-opacity-5 rounded-lg">
      <table class="min-w-full divide-y divide-gray-300">
        <thead class="bg-gray-50">
        <tr>
          <th class="px-6 py-3 text-left text-sm font-semibold text-gray-900">프로젝트명</th>
          <th class="px-6 py-3 text-left text-sm font-semibold text-gray-900">상태</th>
          <th class="px-6 py-3 text-left text-sm font-semibold text-gray-900">시작일</th>
          <th class="px-6 py-3 text-left text-sm font-semibold text-gray-900">종료일</th>
          <th class="px-6 py-3 text-left text-sm font-semibold text-gray-900">진행률</th>
          <th class="px-6 py-3 text-left text-sm font-semibold text-gray-900">담당자</th>
          <th class="relative px-6 py-3">
            <span class="sr-only">Actions</span>
          </th>
        </tr>
        </thead>
        <tbody class="divide-y divide-gray-200 bg-white">
        <tr v-for="project in projects" :key="project.id">
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="flex items-center">
              <div>
                <router-link
                  :to="`/projects/${project.id}`"
                  class="text-blue-600 hover:text-blue-900"
                >
                  {{ project.name }}
                </router-link>
                <div class="text-sm text-gray-500">{{ project.description }}</div>
              </div>
            </div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
              <span
                class="inline-flex rounded-full px-2 text-xs font-semibold"
                :class="getStatusClass(project.status)"
              >
                {{ project.status }}
              </span>
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
            {{ formatDate(project.startDate) }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
            {{ formatDate(project.endDate) }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="w-full bg-gray-200 rounded-full h-2.5">
              <div
                class="bg-blue-600 h-2.5 rounded-full"
                :style="{ width: `${project.progress}%` }"
              ></div>
            </div>
            <span class="text-sm text-gray-500">{{ project.progress }}%</span>
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
            <div class="flex items-center space-x-2">
              <span>{{ project.manager }}</span>
            </div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
            <button
              @click="openProjectMenu(project)"
              class="text-gray-400 hover:text-gray-500"
            >
              <MoreVertical class="h-5 w-5" />
            </button>
          </td>
        </tr>
        </tbody>
      </table>

      <!-- 데이터가 없는 경우 -->
      <div v-if="projects.length === 0" class="text-center py-12">
        <p class="text-gray-500">프로젝트가 없습니다.</p>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div v-if="totalPages > 1" class="mt-4 flex justify-center">
      <nav class="inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
        <!-- 이전 페이지 -->
        <button
          @click="handlePageChange(currentPage - 1)"
          :disabled="currentPage === 0"
          class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
          :class="{ 'opacity-50 cursor-not-allowed': currentPage === 0 }"
        >
          이전
        </button>

        <!-- 페이지 번호 -->
        <button
          v-for="page in totalPages"
          :key="page"
          @click="handlePageChange(page - 1)"
          :class="[
          'relative inline-flex items-center px-4 py-2 border text-sm font-medium',
          currentPage === page - 1
            ? 'z-10 bg-blue-50 border-blue-500 text-blue-600'
            : 'bg-white border-gray-300 text-gray-500 hover:bg-gray-50'
        ]"
        >
          {{ page }}
        </button>

        <!-- 다음 페이지 -->
        <button
          @click="handlePageChange(currentPage + 1)"
          :disabled="currentPage === totalPages - 1"
          class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
          :class="{ 'opacity-50 cursor-not-allowed': currentPage === totalPages - 1 }"
        >
          다음
        </button>
      </nav>
    </div>
  </div>
</template>
