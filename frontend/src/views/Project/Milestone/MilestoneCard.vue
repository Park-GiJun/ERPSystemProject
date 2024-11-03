<template>
  <div class="p-4 max-w-md mx-auto bg-white rounded-xl shadow-md space-y-4">
    <div class="flex justify-between items-center">
      <div class="text-lg font-semibold">{{ milestone.title }}</div>
      <div class="text-sm text-gray-500">{{ milestone.progress }}%</div>
    </div>
    <div class="text-gray-700 text-sm">
      <strong>프로젝트:</strong> {{ milestone.projectName }}
    </div>
    <div class="text-gray-700 text-sm">
      <strong>설명:</strong> {{ milestone.description }}
    </div>
    <div class="text-gray-500 text-xs">
      <strong>시작일:</strong> {{ milestone.plannedStartDate }} | <strong>종료일:</strong> {{ milestone.plannedEndDate }}
    </div>
    <div class="relative pt-1">
      <div class="overflow-hidden h-2 mb-4 text-xs flex rounded bg-blue-200">
        <div
          :style="{ width: milestone.progress + '%' }"
          class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-blue-500"
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MilestoneCard",
  props: {
    milestone: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>
/* Milestone 카드의 스타일 */
</style>
