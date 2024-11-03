<!-- src/views/Projects/ProjectDetail.vue -->
<template>
  <div class="p-6">
    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center items-center h-64">
      <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent"></div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="bg-red-50 text-red-600 p-4 rounded-lg">
      {{ error }}
    </div>

    <!-- Content -->
    <template v-else-if="project">
      <!-- 프로젝트 헤더 -->
      <div class="mb-6">
        <h1 class="text-2xl font-bold mb-2">{{ project.name }}</h1>
        <div class="flex items-center space-x-4">
          <span class="px-3 py-1 rounded-full" :class="statusClass">
            {{ project.status }}
          </span>
          <span class="text-gray-600">
            <CalendarDays class="inline-block w-4 h-4 mr-1" />
            {{ formatDate(project.startDate) }} - {{ formatDate(project.endDate) }}
          </span>
        </div>
      </div>

      <!-- 프로젝트 개요 카드 -->
      <div class="card mb-6">
        <div class="card-header">
          <h2 class="card-title">Project Overview</h2>
        </div>
        <div class="card-content">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <!-- Progress -->
            <div class="p-4 bg-gray-50 rounded-lg">
              <div class="text-sm text-gray-600">Progress</div>
              <div class="mt-1 flex items-center">
                <div class="flex-1 bg-gray-200 rounded-full h-2">
                  <div class="bg-blue-600 rounded-full h-2"
                       :style="{ width: `${project.progress}%` }" />
                </div>
                <span class="ml-2 text-sm font-medium">{{ project.progress }}%</span>
              </div>
            </div>
            <!-- Contract Amount -->
            <div class="p-4 bg-gray-50 rounded-lg">
              <div class="text-sm text-gray-600">Contract Amount</div>
              <div class="mt-1 text-lg font-semibold">
                ₩{{ formatCurrency(project.contractAmount) }}
              </div>
            </div>
            <!-- Required Members -->
            <div class="p-4 bg-gray-50 rounded-lg">
              <div class="text-sm text-gray-600">Required Members</div>
              <div class="mt-1 text-lg font-semibold flex items-center">
                <Users class="w-5 h-5 mr-2" />
                {{ project.requiredMembers }} people
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 탭 컨텐츠 -->
      <div class="tabs">
        <ul class="tab-list">
          <li v-for="tab in tabs"
              :key="tab.value"
              @click="currentTab = tab.value"
              :class="['tab-item', { active: currentTab === tab.value }]">
            <component :is="tab.icon" class="w-4 h-4 mr-2" />
            {{ tab.label }}
          </li>
        </ul>

        <!-- Information Tab -->
        <div v-if="currentTab === 'info'" class="tab-content">
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">Project Information</h3>
            </div>
            <div class="card-content">
              <div class="prose max-w-none">
                <h3 class="text-lg font-semibold mb-2">Description</h3>
                <p class="text-gray-600">{{ project.description }}</p>

                <!-- Technologies -->
                <h3 class="text-lg font-semibold mt-6 mb-2">Technologies</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div v-for="category in groupedTechnologies" :key="category.name">
                    <h4 class="font-medium mb-2">{{ category.name }}</h4>
                    <div class="flex flex-wrap gap-2">
                      <span v-for="tech in category.items"
                            :key="tech.id"
                            class="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm">
                        {{ tech.technologyName }} {{ tech.version }}
                      </span>
                    </div>
                  </div>
                </div>

                <!-- Milestones -->
                <h3 class="text-lg font-semibold mt-6 mb-2">Milestones</h3>
                <div class="space-y-4">
                  <div v-for="milestone in project.milestones"
                       :key="milestone.id"
                       class="p-4 bg-gray-50 rounded-lg">
                    <div class="flex justify-between items-center mb-2">
                      <span class="font-medium">{{ milestone.title }}</span>
                      <span class="text-sm text-gray-600">
                        {{ formatDate(milestone.dueDate) }}
                      </span>
                    </div>
                    <p class="text-sm text-gray-600">{{ milestone.description }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Team Tab -->
        <div v-if="currentTab === 'team'" class="tab-content">
          <div class="card">
            <div class="card-header">
              <h3 class="card-title">Team Members</h3>
            </div>
            <div class="card-content">
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div v-for="member in project.members"
                     :key="member.id"
                     class="p-4 bg-gray-50 rounded-lg">
                  <div class="flex items-center mb-3">
                    <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center mr-3">
                      {{ member.memberName.charAt(0) }}
                    </div>
                    <div>
                      <div class="font-medium">{{ member.memberName }}</div>
                      <div class="text-sm text-gray-600">{{ member.memberUsername }}</div>
                    </div>
                  </div>
                  <div class="text-sm">
                    <div class="flex justify-between text-gray-600 mb-1">
                      <span>Role:</span>
                      <span class="font-medium">{{ formatRole(member.role) }}</span>
                    </div>
                    <div class="flex justify-between text-gray-600 mb-1">
                      <span>Join Date:</span>
                      <span>{{ formatDate(member.joinDate) }}</span>
                    </div>
                    <div v-if="member.endDate" class="flex justify-between text-gray-600">
                      <span>End Date:</span>
                      <span>{{ formatDate(member.endDate) }}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useProjectStore } from '@/stores/projectStore';
import { useProjectDetailStore } from '@/stores/projectDetailStore';
import {
  CalendarDays,
  Users,
  ListTodo,
  Clock,
  Code
} from 'lucide-vue-next';
import { format } from 'date-fns';

const route = useRoute();
const projectStore = useProjectStore();
const projectDetailStore = useProjectDetailStore();
const currentTab = ref('info');
const loading = ref(false);
const error = ref(null);

const tabs = [
  { value: 'info', label: 'Information', icon: ListTodo },
  { value: 'team', label: 'Team', icon: Users }
];

const project = computed(() => projectDetailStore.currentProject);

const statusClass = computed(() => {
  const statusClasses = {
    'PLANNED': 'bg-yellow-100 text-yellow-800',
    'IN_PROGRESS': 'bg-blue-100 text-blue-800',
    'COMPLETED': 'bg-green-100 text-green-800',
    'ON_HOLD': 'bg-orange-100 text-orange-800',
    'CANCELLED': 'bg-red-100 text-red-800'
  };
  return statusClasses[project.value?.status] || 'bg-gray-100 text-gray-800';
});

const groupedTechnologies = computed(() => {
  if (!project.value?.technologies) return [];

  const groups = {};
  project.value.technologies.forEach(tech => {
    if (!groups[tech.category]) {
      groups[tech.category] = [];
    }
    groups[tech.category].push(tech);
  });

  return Object.entries(groups).map(([name, items]) => ({
    name,
    items
  }));
});

const formatDate = (date) => format(new Date(date), 'yyyy-MM-dd');
const formatCurrency = (amount) =>
  new Intl.NumberFormat('ko-KR', {
    style: 'decimal',
    maximumFractionDigits: 0
  }).format(amount);

const formatRole = (role) => {
  return role.replace('_', ' ').toLowerCase()
    .replace(/\b\w/g, l => l.toUpperCase());
};

const loadProjectData = async () => {
  console.log("projectDetailStore:", projectDetailStore);
  console.log("fetchProjectDetail:", typeof projectDetailStore.fetchProjectDetail);

  if (typeof projectDetailStore.fetchProjectDetail !== 'function') {
    console.error("fetchProjectDetail is not a function.");
    return;
  }

  loading.value = true;
  error.value = null;

  try {
    await projectDetailStore.fetchProjectDetail(route.params.id);
  } catch (err) {
    error.value = 'Failed to load project data';
    console.error(err);
  } finally {
    loading.value = false;
  }
};


onMounted(async () => {
  await loadProjectData();
});
</script>

<style scoped>
.card {
  @apply bg-white rounded-lg shadow-sm;
}

.card-header {
  @apply p-4 border-b border-gray-200;
}

.card-title {
  @apply text-lg font-semibold;
}

.card-content {
  @apply p-4;
}

.tabs {
  @apply w-full;
}

.tab-list {
  @apply flex space-x-2 mb-4 border-b border-gray-200;
}

.tab-item {
  @apply flex items-center px-4 py-2 text-gray-600 cursor-pointer hover:text-blue-600
  border-b-2 border-transparent transition-colors duration-200;
}

.tab-item.active {
  @apply text-blue-600 border-blue-600;
}

.tab-content {
  @apply mt-4;
}
</style>
