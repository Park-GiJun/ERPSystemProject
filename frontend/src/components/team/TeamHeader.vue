<!-- src/components/team/TeamHeader.vue -->
<template>
  <div class="flex items-center space-x-2">
    <button
      v-if="team.children?.length"
      @click="$emit('toggle')"
      class="text-gray-500 hover:text-gray-700"
    >
      <component
        :is="isExpanded ? 'ChevronDown' : 'ChevronRight'"
        class="w-5 h-5"
      />
    </button>

    <div>
      <h3 class="text-lg font-semibold">{{ team.teamName }}</h3>
      <div class="flex items-center text-sm text-gray-500 mt-1">
        <span>Level {{ team.teamLevel }}</span>
        <span v-if="team.parentTeamName" class="ml-2">
          · {{ team.parentTeamName }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ChevronDown, ChevronRight } from 'lucide-vue-next'

defineProps({
  team: {
    type: Object,
    required: true
  },
  isExpanded: {
    type: Boolean,
    default: false
  }
})

defineEmits(['toggle'])
</script>
