<!-- src/views/Project/ProjectCreate.vue -->
<template>
  <div class="p-6">
    <div class="mb-6">
      <h2 class="text-lg font-semibold text-gray-900">새 프로젝트 생성</h2>
      <p class="mt-1 text-sm text-gray-500">프로젝트의 기본 정보를 입력해주세요.</p>
    </div>

    <!-- Step Indicator -->
    <div class="mb-8">
      <div class="flex items-center justify-between">
        <div v-for="(step, index) in steps" :key="step.id" class="flex-1 relative">
          <div
            class="h-1 bg-gray-200"
            :class="{
              'bg-blue-500': currentStep > index,
              'bg-blue-200': currentStep === index
            }"
          ></div>
          <div class="absolute top-4 w-full">
            <div class="flex items-center justify-center">
              <div
                class="w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium"
                :class="{
                  'bg-blue-500 text-white': currentStep > index,
                  'bg-blue-200 text-blue-800': currentStep === index,
                  'bg-gray-200 text-gray-400': currentStep < index
                }"
              >
                {{ index + 1 }}
              </div>
            </div>
            <div class="text-xs text-center mt-2 text-gray-500">{{ step.name }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Form Steps -->
    <form @submit.prevent="handleSubmit" class="mt-12">
      <!-- Step 1: Basic Information -->
      <div v-if="currentStep === 0" class="space-y-6">
        <div class="grid grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700">프로젝트명 *</label>
            <input
              v-model="form.name"
              type="text"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              required
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700">계약금액 *</label>
            <div class="mt-1 relative rounded-md shadow-sm">
              <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
                <span class="text-gray-500 sm:text-sm">₩</span>
              </div>
              <input
                v-model="form.contractAmount"
                type="number"
                class="block w-full rounded-md border-gray-300 pl-7 focus:border-blue-500 focus:ring-blue-500"
                required
              />
            </div>
          </div>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">설명</label>
          <textarea
            v-model="form.description"
            rows="4"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          ></textarea>
        </div>

        <div class="grid grid-cols-2 gap-6">
          <div>
            <label class="block text-sm font-medium text-gray-700">시작일 *</label>
            <input
              v-model="form.startDate"
              type="date"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              required
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700">종료일 *</label>
            <input
              v-model="form.endDate"
              type="date"
              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              required
            />
          </div>
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700">필요 인원 *</label>
          <input
            v-model="form.requiredMembers"
            type="number"
            min="1"
            class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
            required
          />
        </div>
      </div>

      <!-- Step 2: Team Members -->
      <div v-if="currentStep === 1" class="space-y-6">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-medium text-gray-900">팀 구성</h3>
          <button
            type="button"
            @click="openMemberSelect"
            class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700"
          >
            <UserPlus class="w-5 h-5 mr-2" />
            멤버 추가
          </button>
        </div>

        <!-- Selected Members List -->
        <div v-if="form.members.length > 0" class="bg-white shadow rounded-lg">
          <ul class="divide-y divide-gray-200">
            <li v-for="member in form.members" :key="member.id" class="px-4 py-4 flex items-center justify-between">
              <div class="flex items-center">
                <div class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center">
                  <span class="text-sm font-medium text-gray-600">
                    {{ member.name.substring(0, 2).toUpperCase() }}
                  </span>
                </div>
                <div class="ml-3">
                  <p class="text-sm font-medium text-gray-900">{{ member.name }}</p>
                  <p class="text-sm text-gray-500">{{ member.email }}</p>
                </div>
              </div>
              <div class="flex items-center space-x-4">
                <select
                  v-model="member.role"
                  class="rounded-md border-gray-300 text-sm focus:border-blue-500 focus:ring-blue-500"
                >
                  <option v-for="role in projectRoles" :key="role.value" :value="role.value">
                    {{ role.label }}
                  </option>
                </select>
                <button
                  @click="removeMember(member)"
                  type="button"
                  class="text-red-600 hover:text-red-900"
                >
                  <Trash2 class="w-5 h-5" />
                </button>
              </div>
            </li>
          </ul>
        </div>
        <div v-else class="text-center py-12 bg-gray-50 rounded-lg border-2 border-dashed border-gray-300">
          <UserPlus class="mx-auto w-12 h-12 text-gray-400" />
          <h3 class="mt-2 text-sm font-medium text-gray-900">팀원이 없습니다</h3>
          <p class="mt-1 text-sm text-gray-500">새로운 팀원을 추가해주세요.</p>
        </div>
      </div>

      <!-- Step 3: Technology Stack -->
      <div v-if="currentStep === 2" class="space-y-6">
        <div v-for="category in technologyCategories" :key="category.value" class="space-y-4">
          <h3 class="text-lg font-medium text-gray-900">{{ category.label }}</h3>
          <div class="space-y-4">
            <div class="flex flex-wrap gap-3">
              <div
                v-for="tech in form.technologies.filter(t => t.category === category.value)"
                :key="tech.id"
                class="inline-flex items-center bg-blue-50 px-3 py-1.5 rounded-full"
              >
                <span class="text-sm font-medium text-blue-800">{{ tech.name }}</span>
                <button
                  type="button"
                  @click="removeTechnology(tech)"
                  class="ml-2 text-blue-400 hover:text-blue-600"
                >
                  <X class="w-4 h-4" />
                </button>
              </div>
              <button
                type="button"
                @click="openTechSelect(category.value)"
                class="inline-flex items-center px-3 py-1.5 border border-gray-300 rounded-full text-sm font-medium text-gray-700 hover:bg-gray-50"
              >
                <Plus class="w-4 h-4 mr-1" />
                추가
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Navigation Buttons -->
      <div class="mt-8 flex justify-between pt-5">
        <button
          type="button"
          @click="prevStep"
          class="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
          :disabled="currentStep === 0"
        >
          <ChevronLeft class="w-5 h-5 mr-2" />
          이전
        </button>
        <button
          v-if="currentStep < steps.length - 1"
          type="button"
          @click="nextStep"
          class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700"
        >
          다음
          <ChevronRight class="w-5 h-5 ml-2" />
        </button>
        <button
          v-else
          type="submit"
          class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700"
        >
          <Save class="w-5 h-5 mr-2" />
          프로젝트 생성
        </button>
      </div>
    </form>
  </div>


  <!-- 로딩 인디케이터 추가 -->
  <div v-if="loading" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
    <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent"></div>
  </div>

  <!-- 멤버 선택 다이얼로그 -->
  <dialog ref="memberDialog" class="fixed inset-0 z-50 bg-transparent p-0 m-0">
    <div class="flex items-center justify-center min-h-screen p-4">
      <div class="fixed inset-0 bg-black/30" @click="closeMemberDialog"></div>

      <div class="relative bg-white rounded-lg shadow-xl w-full max-w-2xl p-6">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-lg font-medium">팀원 선택</h3>
          <button
            @click="showMemberDialog = false"
            class="text-gray-400 hover:text-gray-500"
          >
            <X class="w-5 h-5" />
          </button>
        </div>

        <!-- 로딩 상태 -->
        <div v-if="isLoading" class="flex justify-center py-8">
          <div class="animate-spin rounded-full h-8 w-8 border-2 border-blue-500 border-t-transparent"></div>
        </div>

        <!-- 에러 메시지 -->
        <div v-else-if="error" class="p-4 bg-red-50 text-red-600 rounded-md">
          {{ error }}
        </div>

        <!-- 멤버 목록 -->
        <div v-else class="max-h-96 overflow-y-auto">
          <div
            v-for="member in filteredMembers"
            :key="member.id"
            class="flex items-center p-3 hover:bg-gray-50 rounded-md"
          >
            <input
              type="checkbox"
              :value="member"
              v-model="selectedMembers"
              class="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
            />
            <div class="ml-3 flex-1">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-medium text-gray-900">{{ member.name }}</p>
                  <p class="text-sm text-gray-500">{{ member.email }}</p>
                </div>
                <span
                  class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
                  :class="{
                    'bg-green-100 text-green-800': member.enabled,
                    'bg-gray-100 text-gray-800': !member.enabled
                  }"
                >
                  {{ member.enabled ? '활성' : '비활성' }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- 페이지네이션 -->
        <div class="mt-4 flex items-center justify-between border-t pt-4">
          <div class="flex items-center text-sm text-gray-500">
            총 {{ totalElements }} 명의 멤버
          </div>
          <div class="flex justify-center space-x-2">
            <button
              v-for="page in totalPages"
              :key="page"
              @click="handlePageChange(page - 1)"
              class="px-3 py-1 rounded-md"
              :class="{
                'bg-blue-600 text-white': currentPage === page - 1,
                'bg-gray-100 text-gray-600 hover:bg-gray-200': currentPage !== page - 1
              }"
            >
              {{ page }}
            </button>
          </div>
        </div>

        <!-- 액션 버튼 -->
        <div class="mt-4 flex justify-end space-x-3">
          <!-- X 버튼 수정 -->
          <button
            @click="closeMemberDialog"
            class="text-gray-400 hover:text-gray-500"
          >
            <X class="w-5 h-5" />
          </button>

          <!-- 취소 버튼 수정 -->
          <button
            @click="closeMemberDialog"
            class="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50"
          >
            취소
          </button>
          <button
            @click="saveMemberSelection"
            class="px-4 py-2 bg-blue-600 text-white rounded-md text-sm font-medium hover:bg-blue-700"
            :disabled="selectedMembers.length === 0"
          >
            {{ selectedMembers.length }}명 추가
          </button>
        </div>
      </div>
    </div>
  </dialog>

  <!-- 에러 메시지 표시 -->
  <div v-if="error" class="mb-4 p-4 bg-red-50 border border-red-200 rounded-md">
    <p class="text-sm text-red-600">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, getCurrentInstance } from 'vue';
import { useRouter } from 'vue-router';
import { useProjectStore } from '@/stores/projectStore';
import { storeToRefs } from 'pinia';
import axios from '@/plugins/axios'
import {
  UserPlus, Trash2, ChevronLeft, ChevronRight,
  Plus, X, Save
} from 'lucide-vue-next';

const router = useRouter();
const projectStore = useProjectStore();
const { loading, error, members } = storeToRefs(projectStore);
const { proxy } = getCurrentInstance();

// Steps configuration
const steps = [
  { id: 'basic', name: '기본 정보' },
  { id: 'team', name: '팀 구성' },
  { id: 'tech', name: '기술 스택' }
];

// Project roles
const projectRoles = [
  { value: 'PM', label: '프로젝트 매니저' },
  { value: 'TEAM_LEAD', label: '팀 리더' },
  { value: 'DEVELOPER', label: '개발자' },
  { value: 'DESIGNER', label: '디자이너' },
  { value: 'QA', label: '품질 관리자' }
];

// Technology categories
const technologyCategories = [
  { value: 'FRONTEND', label: '프론트엔드' },
  { value: 'BACKEND', label: '백엔드' },
  { value: 'DATABASE', label: '데이터베이스' },
  { value: 'DEVOPS', label: '데브옵스' }
];

const currentStep = ref(0);
const form = ref({
  name: '',
  description: '',
  startDate: '',
  endDate: '',
  contractAmount: '',
  requiredMembers: 1,
  members: [],
  technologies: []
});

// 멤버 선택 관련 상태
const showMemberDialog = ref(false);
const selectedMembers = ref([]);
const availableMembers = ref([]);
const isLoading = ref(false);
const pageSize = ref(10);
const totalElements = ref(0);
const totalPages = ref(0);
const currentPage = ref(0);
const memberDialog = ref(null);

// 기술 스택 선택 상태
const showTechDialog = ref(false);
const currentTechCategory = ref(null);

// 단계별 유효성 검사
const canProceedToNextStep = computed(() => {
  if (currentStep.value === 0) {
    return form.value.name && form.value.startDate && form.value.endDate && form.value.contractAmount;
  }
  if (currentStep.value === 1) {
    return form.value.members.length > 0;
  }
  return true;
});

// 선택된 멤버 필터링 (이미 프로젝트에 추가된 멤버 제외)
const filteredMembers = computed(() => {
  return availableMembers.value.filter(member =>
    !form.value.members.some(selected => selected.id === member.id)
  );
});

/**
 * 멤버 목록을 가져옵니다
 * @param {number} [page=0] 페이지 번호
 */
const fetchMembers = async (page = 0) => {
  isLoading.value = true;
  error.value = null;

  try {
    const response = await axios.get('/api/members', {
      params: {
        page,
        size: pageSize.value
      }
    });

    availableMembers.value = response.data.content;
    totalElements.value = response.data.totalElements;
    totalPages.value = response.data.totalPages;
    currentPage.value = response.data.number;
  } catch (err) {
    console.error('Failed to fetch members:', err);
    error.value = '멤버 목록을 불러오는데 실패했습니다. 다시 시도해주세요.';
    if (err.response?.status === 401) {
      proxy.$toast.error('인증이 필요합니다. 다시 로그인해주세요.');
      router.push('/login');
    } else {
      proxy.$toast.error(error.value);
    }
  } finally {
    isLoading.value = false;
  }
};

// 페이지 변경 처리
const handlePageChange = (page) => {
  fetchMembers(page);
};

// 멤버 선택 다이얼로그 열기
const openMemberSelect = async () => {
  await fetchMembers();
  memberDialog.value?.showModal();
  showMemberDialog.value = true;
};

// 멤버 선택 다이얼로그 닫기
const closeMemberDialog = () => {
  memberDialog.value?.close();
  showMemberDialog.value = false;
  selectedMembers.value = [];
};

// 멤버 선택 저장 수정
const saveMemberSelection = () => {
  selectedMembers.value.forEach(member => {
    if (!form.value.members.some(selected => selected.id === member.id)) {
      form.value.members.push({
        ...member,
        role: 'DEVELOPER' // 기본 역할
      });
    }
  });
  selectedMembers.value = [];
  closeMemberDialog();
};

// 멤버 제거
const removeMember = (member) => {
  form.value.members = form.value.members.filter(m => m.id !== member.id);
};

// 기술 스택 관련 메서드
const openTechSelect = (category) => {
  currentTechCategory.value = category;
  showTechDialog.value = true;
};

const addTechnology = (tech) => {
  form.value.technologies.push({
    id: Date.now(), // 임시 ID
    category: currentTechCategory.value,
    name: tech.name,
    version: tech.version
  });
  showTechDialog.value = false;
};

const removeTechnology = (tech) => {
  form.value.technologies = form.value.technologies.filter(t => t.id !== tech.id);
};

// 폼 유효성 검사
const validateForm = () => {
  // 기본 정보 검사
  if (!form.value.name || !form.value.startDate || !form.value.endDate || !form.value.contractAmount) {
    proxy.$toast.warning('필수 정보를 모두 입력해주세요.');
    return false;
  }

  // 시작일/종료일 검사
  const startDate = new Date(form.value.startDate);
  const endDate = new Date(form.value.endDate);
  if (startDate >= endDate) {
    proxy.$toast.warning('종료일은 시작일보다 이후여야 합니다.');
    return false;
  }

  // 팀 멤버 검사
  if (form.value.members.length === 0) {
    proxy.$toast.warning('최소 1명 이상의 팀원을 추가해주세요.');
    return false;
  }

  // 프로젝트 매니저 검사
  const hasPM = form.value.members.some(member => member.role === 'PM');
  if (!hasPM) {
    proxy.$toast.warning('프로젝트 매니저를 지정해주세요.');
    return false;
  }

  return true;
};

// 단계 이동 메서드
const nextStep = () => {
  if (!canProceedToNextStep.value) {
    proxy.$toast.warning('현재 단계의 필수 정보를 모두 입력해주세요.');
    return;
  }
  if (currentStep.value < steps.length - 1) {
    currentStep.value++;
  }
};

const prevStep = () => {
  if (currentStep.value > 0) {
    currentStep.value--;
  }
};

// 폼 제출
const handleSubmit = async () => {
  try {
    if (!validateForm()) {
      return;
    }

    await projectStore.createProject(form.value);
    proxy.$toast.success('프로젝트가 성공적으로 생성되었습니다.');
    await router.push('/projects/list');
  } catch (error) {
    proxy.$toast.error(error.message || '프로젝트 생성 중 오류가 발생했습니다.');
  }
};

// 페이지 로드 시 실행
onMounted(async () => {
  try {
    await fetchMembers();
  } catch (error) {
    proxy.$toast.error('멤버 목록을 불러오는데 실패했습니다.');
  }
});
</script>
<style scoped>
dialog {
  @apply m-0 p-0 bg-transparent;
}

dialog::backdrop {
  @apply bg-transparent;
}

dialog[open] {
  @apply flex items-center justify-center;
}

/* Firefox 대응 */
@-moz-document url-prefix() {
  dialog {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>
