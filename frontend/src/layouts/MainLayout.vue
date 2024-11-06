<!-- src/layouts/MainLayout.vue -->
<template>
  <div class="flex min-h-screen">
    <Sidebar class="fixed left-0 top-0 h-full" />
    <main class="flex-1 ml-64"> <!-- Sidebar width: 16rem (64) -->
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
</script>
