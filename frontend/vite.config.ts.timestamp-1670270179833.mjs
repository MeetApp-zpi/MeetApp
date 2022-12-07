// vite.config.ts
import { defineConfig } from 'file:///D:/dev/MeetApp/frontend/node_modules/vite/dist/node/index.js';
import { svelte } from 'file:///D:/dev/MeetApp/frontend/node_modules/@sveltejs/vite-plugin-svelte/dist/index.js';
var vite_config_default = defineConfig({
    server: {
        proxy: {
            '/api': {
                target: 'http://meetapp.eastus.cloudapp.azure.com:8080',
                changeOrigin: true,
                secure: false,
                ws: true
            },
            '/oauth2/authorization/google': {
                target: 'http://meetapp.eastus.cloudapp.azure.com:8080',
                changeOrigin: true,
                secure: false,
                ws: true
            }
        }
    },
    plugins: [svelte()]
});
export { vite_config_default as default };
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxkZXZcXFxcTWVldEFwcFxcXFxmcm9udGVuZFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRDpcXFxcZGV2XFxcXE1lZXRBcHBcXFxcZnJvbnRlbmRcXFxcdml0ZS5jb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0Q6L2Rldi9NZWV0QXBwL2Zyb250ZW5kL3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcclxuaW1wb3J0IHsgc3ZlbHRlIH0gZnJvbSAnQHN2ZWx0ZWpzL3ZpdGUtcGx1Z2luLXN2ZWx0ZSdcclxuXHJcbi8vIGh0dHBzOi8vdml0ZWpzLmRldi9jb25maWcvXHJcbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XHJcbiAgc2VydmVyOiB7XHJcbiAgICBwcm94eToge1xyXG4gICAgICBcIi9hcGlcIjoge1xyXG4gICAgICAgIHRhcmdldDogXCJodHRwOi8vbG9jYWxob3N0OjgwODBcIixcclxuICAgICAgICBjaGFuZ2VPcmlnaW46IHRydWUsXHJcbiAgICAgICAgc2VjdXJlOiBmYWxzZSxcclxuICAgICAgICB3czogdHJ1ZVxyXG4gICAgICB9LFxyXG4gICAgICBcIi9vYXV0aDIvYXV0aG9yaXphdGlvbi9nb29nbGVcIjoge1xyXG4gICAgICAgIHRhcmdldDogXCJodHRwOi8vbG9jYWxob3N0OjgwODBcIixcclxuICAgICAgICBjaGFuZ2VPcmlnaW46IHRydWUsXHJcbiAgICAgICAgc2VjdXJlOiBmYWxzZSxcclxuICAgICAgICB3czogdHJ1ZVxyXG4gICAgICB9XHJcbiAgICAgIC8vIFwiL3dlYnNvY2tldHNcIjoge1xyXG4gICAgICAvLyAgIHRhcmdldDogXCJodHRwOi8vbG9jYWxob3N0OjgwODBcIixcclxuICAgICAgLy8gICB3czogdHJ1ZSxcclxuICAgICAgLy8gICBjaGFuZ2VPcmlnaW46IHRydWVcclxuICAgICAgLy8gfVxyXG4gICAgfSxcclxuICB9LFxyXG4gIHBsdWdpbnM6IFtzdmVsdGUoKV1cclxufSlcclxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUErUCxTQUFTLG9CQUFvQjtBQUM1UixTQUFTLGNBQWM7QUFHdkIsSUFBTyxzQkFBUSxhQUFhO0FBQUEsRUFDMUIsUUFBUTtBQUFBLElBQ04sT0FBTztBQUFBLE1BQ0wsUUFBUTtBQUFBLFFBQ04sUUFBUTtBQUFBLFFBQ1IsY0FBYztBQUFBLFFBQ2QsUUFBUTtBQUFBLFFBQ1IsSUFBSTtBQUFBLE1BQ047QUFBQSxNQUNBLGdDQUFnQztBQUFBLFFBQzlCLFFBQVE7QUFBQSxRQUNSLGNBQWM7QUFBQSxRQUNkLFFBQVE7QUFBQSxRQUNSLElBQUk7QUFBQSxNQUNOO0FBQUEsSUFNRjtBQUFBLEVBQ0Y7QUFBQSxFQUNBLFNBQVMsQ0FBQyxPQUFPLENBQUM7QUFDcEIsQ0FBQzsiLAogICJuYW1lcyI6IFtdCn0K
