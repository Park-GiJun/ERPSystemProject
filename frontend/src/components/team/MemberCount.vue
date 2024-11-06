<!-- src/components/team/MemberCount.vue -->
<template>
  <span class="px-2 py-1 text-sm bg-blue-100 text-blue-800 rounded-full">
    {{ count }} members
  </span>
</template>

<script setup>
defineProps({
  count: {
    type: Number,
    required: true
  }
})
</script>
