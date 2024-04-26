# 기존 멀티 스테이지 빌드의 builder 단계 제거 (필요에 따라)
FROM node:lts-alpine

WORKDIR /FRONTEND/web

# package.json과 package-lock.json을 먼저 복사
COPY package*.json ./

# 프로젝트 의존성 설치
RUN npm install

# 프로젝트 소스 복사
COPY . .

# Vite를 사용한 프론트엔드 프로젝트 빌드
# Vite 빌드 결과는 /usr/src/app/dist 폴더에 위치
RUN npm run build

# 런타임 이미지 (실제 서버가 필요하지 않기 때문에 작업을 완료하고 종료합니다)
CMD echo "Front-end build complete"