<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">조직도</h1>

    <!-- 팀 계층 구조 표시 -->
    <div class="space-y-4">
      <template v-for="team in teams.content" :key="team.id">
        <TeamTreeItem
          :team="team"
          :depth="0"
        />
      </template>
    </div>

    <!-- 페이지네이션 -->
    <div v-if="teams.totalPages > 1" class="mt-6 flex justify-center">
      <nav class="flex items-center gap-2">
        <button
          :disabled="teams.first"
          @click="changePage(currentPage - 1)"
          class="px-3 py-1 rounded border"
          :class="{ 'opacity-50 cursor-not-allowed': teams.first }"
        >
          이전
        </button>

        <span class="px-3 py-1">
          {{ currentPage + 1 }} / {{ teams.totalPages }}
        </span>

        <button
          :disabled="teams.last"
          @click="changePage(currentPage + 1)"
          class="px-3 py-1 rounded border"
          :class="{ 'opacity-50 cursor-not-allowed': teams.last }"
        >
          다음
        </button>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { teamApi } from '@/api/teamManagement'
import TeamTreeItem from './TeamTreeItem.vue'

const teams = ref({
  content: [],
  totalElements: 0,
  totalPages: 0,
  number: 0,
  size: 10,
  first: true,
  last: true
})

const currentPage = ref(0)
const pageSize = ref(10)

const loadTeams = async (page = 0) => {
  try {
    teams.value = await teamApi.getAllTeams(page, pageSize.value)
    currentPage.value = page
  } catch (error) {
    console.error('Failed to load teams:', error)
  }
}

const changePage = (page) => {
  loadTeams(page)
}

onMounted(() => {
  loadTeams()
})
</script>
