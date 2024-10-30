<!-- Dashboard.vue -->
<template>
  <div class="flex-1 p-8 bg-gray-100">
    <div class="mb-8">
      <h1 class="text-2xl font-bold text-gray-900">Dashboard</h1>
      <p class="text-gray-600">Welcome back, {{ userName }}</p>
    </div>

    <!-- Stats Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      <div v-for="(stat, index) in stats"
           :key="index"
           class="bg-white p-6 rounded-lg shadow-sm">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm font-medium text-gray-600">{{ stat.title }}</p>
            <p class="text-2xl font-semibold text-gray-900">{{ stat.value }}</p>
          </div>
          <div class="w-12 h-12 bg-gray-100 rounded-full flex items-center justify-center">
            <component :is="stat.icon" class="w-6 h-6 text-gray-700" />
          </div>
        </div>
        <div class="mt-4">
          <p :class="[
            'text-sm',
            stat.trend > 0 ? 'text-green-600' : 'text-red-600'
          ]">
            {{ stat.trend > 0 ? '+' : '' }}{{ stat.trend }}% from previous month
          </p>
        </div>
      </div>
    </div>

    <!-- Charts Grid -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
      <!-- Project Status -->
      <div class="bg-white p-6 rounded-lg shadow-sm">
        <h2 class="text-lg font-semibold mb-4">Project Status</h2>
        <div class="space-y-4">
          <div v-for="(project, index) in projects" :key="index">
            <div class="flex justify-between mb-1">
              <span class="text-sm font-medium text-gray-700">{{ project.name }}</span>
              <span class="text-sm text-gray-600">{{ project.progress }}%</span>
            </div>
            <div class="w-full bg-gray-200 rounded-full h-2">
              <div class="bg-blue-600 h-2 rounded-full"
                   :style="{ width: `${project.progress}%` }">
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Activities -->
      <div class="bg-white p-6 rounded-lg shadow-sm">
        <h2 class="text-lg font-semibold mb-4">Recent Activities</h2>
        <div class="space-y-4">
          <div v-for="(activity, index) in recentActivities"
               :key="index"
               class="flex items-start">
            <div class="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center mr-3">
              <component :is="activity.icon" class="w-4 h-4 text-gray-700" />
            </div>
            <div>
              <p class="text-sm font-medium text-gray-900">{{ activity.title }}</p>
              <p class="text-sm text-gray-600">{{ activity.time }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Calendar and Messages Preview -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Upcoming Events -->
      <div class="col-span-2 bg-white p-6 rounded-lg shadow-sm">
        <h2 class="text-lg font-semibold mb-4">Upcoming Events</h2>
        <div class="space-y-4">
          <div v-for="(event, index) in upcomingEvents"
               :key="index"
               class="flex items-center p-3 hover:bg-gray-50 rounded-lg">
            <div class="w-12 text-center">
              <p class="text-sm font-semibold text-gray-900">{{ event.date }}</p>
              <p class="text-xs text-gray-600">{{ event.month }}</p>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-900">{{ event.title }}</p>
              <p class="text-sm text-gray-600">{{ event.time }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Team Members -->
      <div class="bg-white p-6 rounded-lg shadow-sm">
        <h2 class="text-lg font-semibold mb-4">Team Members</h2>
        <div class="space-y-4">
          <div v-for="(member, index) in teamMembers"
               :key="index"
               class="flex items-center">
            <div class="w-8 h-8 rounded-full bg-gray-200 flex items-center justify-center">
              <span class="text-sm font-medium">{{ member.initials }}</span>
            </div>
            <div class="ml-3">
              <p class="text-sm font-medium text-gray-900">{{ member.name }}</p>
              <p class="text-xs text-gray-600">{{ member.role }}</p>
            </div>
            <div :class="[
              'ml-auto px-2 py-1 rounded-full text-xs font-medium',
              member.status === 'online' ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'
            ]">
              {{ member.status }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import {
  Users,
  Building2,
  Briefcase,
  BarChart3,
  MessageSquare,
  FileText,
  Plus,
  List,
  UserPlus
} from 'lucide-vue-next';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();
const userName = computed(() => authStore.username);



const stats = ref([
  { title: 'Total Projects', value: '45', trend: 12.5, icon: Briefcase },
  { title: 'Active Clients', value: '18', trend: 8.2, icon: Users },
  { title: 'Total Revenue', value: '$845K', trend: 15.3, icon: BarChart3 },
  { title: 'Pending Tasks', value: '24', trend: -4.5, icon: FileText },
]);

const projects = ref([
  { name: 'ERP Implementation', progress: 75 },
  { name: 'Cloud Migration', progress: 45 },
  { name: 'Security Audit', progress: 90 },
  { name: 'Data Center Setup', progress: 30 },
]);

const recentActivities = ref([
  { title: 'New project created', time: '2 hours ago', icon: Plus },
  { title: 'Meeting with client', time: '4 hours ago', icon: Users },
  { title: 'Project milestone completed', time: '1 day ago', icon: Briefcase },
  { title: 'New document uploaded', time: '1 day ago', icon: FileText },
]);

const upcomingEvents = ref([
  { date: '15', month: 'Dec', title: 'Client Meeting', time: '09:00 AM' },
  { date: '18', month: 'Dec', title: 'Team Review', time: '02:00 PM' },
  { date: '20', month: 'Dec', title: 'Project Deadline', time: '11:59 PM' },
]);

const teamMembers = ref([
  { name: 'Jane Cooper', role: 'Project Manager', status: 'online', initials: 'JC' },
  { name: 'Alex Smith', role: 'Developer', status: 'offline', initials: 'AS' },
  { name: 'Sarah Johnson', role: 'Designer', status: 'online', initials: 'SJ' },
  { name: 'Mike Wilson', role: 'DevOps', status: 'online', initials: 'MW' },
]);
</script>
