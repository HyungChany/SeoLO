# 1단계: Gradle을 이용해 빌드
FROM gradle:8.5.0-jdk17-alpine AS build

WORKDIR /app

# Gradle 설정 파일들을 Docker 이미지 안으로 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
#COPY fullertingsecretfolder .


# 소스 코드를 Docker 이미지 안으로 복사
COPY src src

RUN chmod +x ./gradlew

#ENV	USE_PROFILE prod

# 빌드 수행
RUN ./gradlew bootJar

# 2단계: Java Runtime 이미지 안에 빌드 결과물을 넣어 실행
FROM azul/zulu-openjdk:17

WORKDIR /app

# 빌드 결과물을 이전 단계에서 복사
COPY --from=build /app/build/libs/*.jar /app/back.jar


# 서버 실행
ENTRYPOINT ["java","-jar", "-Djava.security.egd=file:/dev/./urandom","/app/back.jar"]
