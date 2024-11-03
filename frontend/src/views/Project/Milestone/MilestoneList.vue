<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">마일스톤 대시보드</h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-6">
      <MilestoneCard
        v-for="milestone in milestones"
        :key="milestone.id"
        :milestone="milestone"
      />
    </div>
  </div>
</template>

<script>
import MilestoneCard from "./MilestoneCard.vue";

export default {
  name: "MilestoneDashboard",
  components: {
    MilestoneCard,
  },
  data() {
    return {
      milestones: [
        {
          id: 1,
          title: "기획 완료",
          projectName: "프로젝트 A",
          description: "프로젝트 초기 기획 작업",
          progress: 100,
          plannedStartDate: "2024-01-01",
          plannedEndDate: "2024-01-10",
        },
        {
          id: 2,
          title: "디자인 완료",
          projectName: "프로젝트 B",
          description: "UI/UX 디자인 완료",
          progress: 80,
          plannedStartDate: "2024-01-11",
          plannedEndDate: "2024-01-20",
        },
        {
          id: 3,
          title: "개발 진행 중",
          projectName: "프로젝트 C",
          description: "백엔드 API 개발",
          progress: 45,
          plannedStartDate: "2024-01-21",
          plannedEndDate: "2024-01-30",
        },
      ],
    };
  },
};
</script>

<style scoped>
/* Dashboard의 스타일 */
</style>
