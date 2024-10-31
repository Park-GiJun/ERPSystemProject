// src/main.js
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import axios from './plugins/axios'
import VueToast from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'
import './style.css'

const app = createApp(App)

app.config.globalProperties.$axios = axios
app.use(createPinia())
app.use(router)
app.use(VueToast, {
  position: 'top-right',
  duration: 3000,
  dismissible: true,
  queue: true
})

app.mount('#app')
