<!-- src/components/team/MemberCard.vue -->
<template>
  <div class="flex items-center p-2 bg-gray-50 rounded">
    <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center mr-2">
      <span class="text-sm font-medium text-blue-800">
        {{ member.name.charAt(0).toUpperCase() }}
      </span>
    </div>

    <div class="flex-1">
      <div class="text-sm font-medium">{{ member.name }}</div>
      <div class="text-xs text-gray-500">{{ member.position }}</div>
    </div>

    <span
      v-if="member.isTeamLeader"
      class="px-2 py-1 text-xs bg-green-100 text-green-800 rounded-full"
    >
      Team Leader
    </span>
  </div>
</template>

<script setup>
defineProps({
  member: {
    type: Object,
    required: true
  }
})
</script>
