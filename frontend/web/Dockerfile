# Node.js 이미지를 사용
FROM node:lts-alpine

# 작업 디렉토리 설정
WORKDIR /build

# package.json과 package-lock.json을 먼저 복사
COPY package*.json ./

# 프로젝트 의존성 설치
RUN npm install

# 프로젝트 소스 복사
COPY . .

# Vite를 사용한 프론트엔드 프로젝트 빌드
RUN npm run build

# CMD를 제거하거나, Nginx에서 서빙할 준비를 명시적으로 표시
CMD ["echo", "Front-end build complete"]
