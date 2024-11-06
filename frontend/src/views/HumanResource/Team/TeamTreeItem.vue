<!-- src/components/team/TeamTreeItem.vue -->
<template>
  <div class="team-section" :class="{ 'ml-8': depth > 0 }">
    <!-- 팀 카드 -->
    <div class="bg-white rounded-lg border border-gray-200 hover:border-blue-300 transition-colors">
      <!-- 팀 헤더 -->
      <div class="p-4">
        <!-- 팀 기본 정보 -->
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <!-- 확장/축소 아이콘 -->
            <button
              v-if="team.children?.length"
              @click="isExpanded = !isExpanded"
              class="text-gray-400 hover:text-blue-500"
            >
              <ChevronRight v-if="!isExpanded" class="w-5 h-5" />
              <ChevronDown v-else class="w-5 h-5" />
            </button>

            <!-- 팀 이름 및 경로 -->
            <div>
              <h3 class="text-lg font-medium">
                {{ team.teamName }}
                <span class="text-sm text-gray-500 ml-2">
                  {{ team.parentTeamName ? `/ ${team.parentTeamName}` : '' }}
                </span>
              </h3>
            </div>
          </div>

          <!-- 멤버 정보 뱃지 -->
          <button
            v-if="team.members?.length"
            @click="showMembers = !showMembers"
            class="flex items-center gap-2 px-3 py-1.5 bg-gray-50 hover:bg-gray-100 rounded-full"
          >
            <Users class="w-4 h-4 text-gray-600" />
            <span class="text-sm text-gray-600">{{ team.members.length }}</span>
          </button>
        </div>

        <!-- 멤버 리스트 -->
        <div v-if="showMembers && team.members?.length" class="mt-4">
          <div class="grid grid-cols-2 gap-3">
            <div
              v-for="member in team.members"
              :key="member.memberId"
              class="flex items-center p-2 rounded bg-gray-50"
            >
              <!-- 멤버 아바타 -->
              <div
                class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center text-blue-700 font-medium mr-3"
              >
                {{ member.name.charAt(0) }}
              </div>

              <!-- 멤버 정보 -->
              <div class="flex-1">
                <div class="flex items-center gap-2">
                  <span class="font-medium">{{ member.name }}</span>
                  <span
                    v-if="member.isTeamLeader"
                    class="px-1.5 py-0.5 bg-blue-100 text-blue-700 text-xs rounded"
                  >
                    리더
                  </span>
                </div>
                <div class="text-sm text-gray-500">{{ member.position }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 하위 팀 -->
    <div
      v-if="team.children?.length && isExpanded"
      class="mt-4 space-y-4 border-l-2 border-gray-100"
    >
      <TeamTreeItem
        v-for="childTeam in team.children"
        :key="childTeam.id"
        :team="childTeam"
        :depth="depth + 1"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ChevronDown, ChevronRight, Users } from 'lucide-vue-next'

defineProps({
  team: {
    type: Object,
    required: true
  },
  depth: {
    type: Number,
    default: 0
  }
})

const isExpanded = ref(false)
const showMembers = ref(false)
</script>

<style scoped>
.team-section {
  position: relative;
}

.team-section .border-l-2 {
  margin-left: 1.5rem;
}
</style>
