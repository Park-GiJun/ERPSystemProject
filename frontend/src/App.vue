<!-- src/App.vue -->
<template>
  <router-view v-if="authStore.isInitialized"></router-view>
  <div v-else class="min-h-screen flex items-center justify-center">
    <div class="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent"></div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useAuthStore } from './stores/auth';

const authStore = useAuthStore();

onMounted(async () => {
  if (!authStore.isInitialized) {
    await authStore.initializeAuth();
  }
});
</script>
