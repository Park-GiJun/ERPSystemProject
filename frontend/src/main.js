// main.js
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import axios from './plugins/axios';
import VueToast from 'vue-toast-notification';
import 'vue-toast-notification/dist/theme-sugar.css';
import './style.css';

const app = createApp(App);
const pinia = createPinia();  // Pinia 인스턴스를 변수에 할당

app.config.globalProperties.$axios = axios;
app.use(pinia);  // 명시적으로 Pinia 인스턴스를 등록
app.use(router);
app.use(VueToast, {
  position: 'top-right',
  duration: 3000,
  dismissible: true,
  queue: true
});

app.mount('#app');
