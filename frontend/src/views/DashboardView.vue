<template>
  <div class="container mx-auto px-4 py-6">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <!-- 통계 카드 -->
      <div v-for="(stat, index) in stats"
           :key="index"
           class="bg-white rounded-lg shadow p-6">
        <h3 class="text-gray-500 text-sm font-medium">{{ stat.title }}</h3>
        <p class="mt-2 text-3xl font-semibold text-gray-900">{{ stat.value }}</p>
        <div class="mt-2 flex items-center text-sm">
          <span :class="stat.trend > 0 ? 'text-green-600' : 'text-red-600'">
            {{ stat.trend > 0 ? '↑' : '↓' }} {{ Math.abs(stat.trend) }}%
          </span>
          <span class="text-gray-500 ml-2">지난주 대비</span>
        </div>
      </div>
    </div>

    <!-- 최근 활동 -->
    <div class="mt-8 bg-white rounded-lg shadow">
      <div class="px-6 py-4 border-b border-gray-200">
        <h2 class="text-lg font-medium text-gray-900">최근 활동</h2>
      </div>
      <div class="p-6">
        <div class="flow-root">
          <ul class="-mb-8">
            <li v-for="(activity, index) in recentActivities"
                :key="index"
                class="pb-8">
              <div class="relative">
                <span class="absolute top-4 left-4 -ml-px h-full w-0.5 bg-gray-200"
                      aria-hidden="true"
                      v-if="index !== recentActivities.length - 1">
                </span>
                <div class="relative flex space-x-3">
                  <div>
                    <span class="h-8 w-8 rounded-full bg-blue-500 flex items-center justify-center ring-8 ring-white">
                      <!-- 아이콘 혹은 이니셜 -->
                      <span class="text-white text-sm">{{ activity.initial }}</span>
                    </span>
                  </div>
                  <div class="flex-1 min-w-0">
                    <div>
                      <div class="text-sm">
                        <span class="font-medium text-gray-900">
                          {{ activity.user }}
                        </span>
                        <span class="text-gray-500 ml-2">
                          {{ activity.action }}
                        </span>
                      </div>
                      <div class="mt-1 text-sm text-gray-500">
                        {{ activity.time }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const stats = ref([
  {
    title: '전체 사원 수',
    value: '128명',
    trend: 12
  },
  {
    title: '이번 달 매출',
    value: '₩1.2M',
    trend: 2.5
  },
  {
    title: '진행 중인 프로젝트',
    value: '24개',
    trend: -5
  },
  {
    title: '미처리 요청',
    value: '12건',
    trend: 8
  }
])

const recentActivities = ref([
  {
    user: '김지훈',
    initial: '김',
    action: '새로운 프로젝트를 생성했습니다.',
    time: '5분 전'
  },
  {
    user: '이서연',
    initial: '이',
    action: '휴가 신청을 제출했습니다.',
    time: '2시간 전'
  },
  {
    user: '박민수',
    initial: '박',
    action: '보고서를 업로드했습니다.',
    time: '5시간 전'
  }
])
</script>
