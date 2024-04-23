# 베이스 이미지로 Flutter SDK가 포함된 이미지 사용
FROM cirrusci/flutter:latest AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Flutter 프로젝트의 소스 코드를 이미지에 복사
COPY ./frontend/app /app

# Flutter 의존성 가져오기 및 앱 빌드
RUN flutter pub get
RUN flutter build web

# Python 이미지 사용하여 앱 서빙
FROM python:3.9-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 Flutter 웹 앱 파일 복사
COPY --from=builder /app/build/web /app

# Python HTTP 서버 실행
CMD ["python", "-m", "http.server", "5000"]