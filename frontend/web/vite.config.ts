import react from '@vitejs/plugin-react';
import path from 'path';
import { fileURLToPath } from 'url';
import { defineConfig } from 'vite';
import svgr from 'vite-plugin-svgr';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

export default defineConfig({
  base: './',
  server: {
    port: 5173,
    host: '0.0.0.0',
    hmr: {
      protocol: 'wss',
      host: 'k10c104.p.ssafy.io',
      port: 5173, // 포트 명시적으로 설정
      clientPort: 443 // 브라우저에서 접속하는 포트 (보안 연결)
    },
  },
  plugins: [react(), svgr()],
  resolve: {
    alias: [{ find: '@', replacement: path.resolve(__dirname, 'src') }],
  },
  assetsInclude: ['**/*.jpg', '**/*.png'],
});
