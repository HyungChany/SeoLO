# Flutter SDK가 포함된 베이스 이미지 사용
FROM cirrusci/flutter:latest AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Flutter 프로젝트의 소스 코드를 이미지에 복사
COPY ./frontend/app /app

# Flutter 의존성 가져오기
RUN flutter pub get

# Flutter 앱을 웹용으로 빌드
RUN flutter build web