<!-- src/components/team/TeamMembers.vue -->
<template>
  <div class="space-y-2">
    <div class="flex items-center">
      <button
        @click="isVisible = !isVisible"
        class="flex items-center text-sm text-gray-600 hover:text-gray-800"
      >
        <Users class="w-4 h-4 mr-1" />
        <span>{{ isVisible ? 'Hide' : 'Show' }} Members</span>
        <ChevronDown
          :class="{'rotate-180': isVisible}"
          class="w-4 h-4 ml-1 transition-transform"
        />
      </button>
    </div>

    <div v-if="isVisible" class="grid grid-cols-1 md:grid-cols-2 gap-2 pl-4">
      <MemberCard
        v-for="member in members"
        :key="member.memberId"
        :member="member"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Users, ChevronDown } from 'lucide-vue-next'
import MemberCard from '@/components/team/MemberCard.vue'

defineProps({
  members: {
    type: Array,
    required: true
  }
})

const isVisible = ref(false)
</script>
