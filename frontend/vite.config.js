import { defineConfig, loadEnv } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig(({ command, mode }) => {
  // 환경 변수를 불러옵니다.
  const env = loadEnv(mode, process.cwd(), '');

  return {
    plugins: [vue()],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src')
      }
    },
    server: {
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true
        }
      }
    },
    build: {
      outDir: 'dist',
      assetsDir: 'assets',
      sourcemap: mode === 'development',
      rollupOptions: {
        output: {
          manualChunks: {
            'vendor': ['vue', 'vue-router', 'pinia', 'axios'],
            'ui': ['@heroicons/vue', 'lucide-vue-next']
          }
        }
      }
    },
    define: {
      __VUE_PROD_DEVTOOLS__: mode === 'development',
      'import.meta.env.VITE_API_URL': JSON.stringify(env.VITE_API_URL), // VITE_API_URL을 직접 정의
      'import.meta.env.VITE_ENV': JSON.stringify(env.VITE_ENV)
    }
  };
});
