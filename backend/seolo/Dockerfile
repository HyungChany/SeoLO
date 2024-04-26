# 1단계: Gradle을 이용해 빌드
FROM gradle:8.5.0-jdk17-alpine AS build

WORKDIR /app

# Gradle 설정 파일들을 Docker 이미지 안으로 복사
COPY gradlew .
COPY gradle gradle
# 프로젝트의 소스 코드와 빌드 파일 복사
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
ARG SPRING_PROFILES_ACTIVE
RUN ./gradlew bootJar -Pprofile=${SPRING_PROFILES_ACTIVE}

# 2단계: Java Runtime 이미지 안에 빌드 결과물을 넣어 실행
FROM azul/zulu-openjdk:17

WORKDIR /app

# 빌드 결과물을 이전 단계에서 복사
COPY --from=build /app/build/libs/*.jar /app.jar

# 서버 실행
ENTRYPOINT ["java","-jar", "-Djava.security.egd=file:/dev/./urandom","/app.jar"]
