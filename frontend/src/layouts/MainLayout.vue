<template>
  <div class="h-screen flex bg-gray-100">
    <!-- Sidebar -->
    <Sidebar v-if="isAuthenticated" />

    <!-- Main Content -->
    <div class="flex-1 overflow-auto">
      <header class="bg-white shadow h-16 flex items-center px-6">
        <h1 class="text-xl font-semibold text-gray-800">
          {{ currentPageTitle }}
        </h1>
      </header>

      <main class="p-6">
        <router-view></router-view>
      </main>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import Sidebar from '@/components/Sidebar.vue'

const route = useRoute()
const authStore = useAuthStore()

const isAuthenticated = computed(() => authStore.isAuthenticated)
const currentPageTitle = computed(() => route.meta.title || 'Dashboard')
</script>
