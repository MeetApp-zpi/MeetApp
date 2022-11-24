import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
        ws: true
      },
      "/oauth2/authorization/google": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
        ws: true
      }
    },
  },
  plugins: [svelte()]
})
