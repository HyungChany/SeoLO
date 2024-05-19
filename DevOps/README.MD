# 📁인프라 디렉토리 구조
```
📦DevOps
```

## Jenkins
✅ 배포 자동화를 위한 Jenkins 설정 <br>
✅ 젠킨스 컨테이너에 도커 설치 및 권한 수정한 Dockerfile 구성 <br>
✅ 9000번 포트 및 볼륨매핑이 설정된 도커 컴포즈로 컨테이너 실행 <br>
✅ 프론트, 백엔드 디렉토리 내 각자 DockerFile로 이미지 자동 빌드

```
┣ 📂jenkins
┃ ┣ 📜docker-compose.yml
┃ ┣ 📜Dockerfile
┃ ┣ 📜Jenkinsfile
```


## Nginx
✅ TLS 인증서 볼륨매핑 구성을 통해 HTTPS 환경 설정 <br>
✅ 같은 디렉토리 내 nginx.conf 볼륨 매핑을 통해 Nginx 설정 편의 제고 <br>
✅ HTTP 2.0 / SSE / 리버스 프록시 등 설정
```
┣ 📂nginx
┃ ┣ 📜docker-compose.yml
┃ ┗ 📜nginx.conf
```

## 전체 배포
✅ 웹 프론트, 스프링 메인 백엔드, 뉴스 장고 백엔드 서버 컴포즈로 단일 구성 <br>
✅ Nginx 서빙을 통한 프론트 Node 서버 배포 <br>
✅ 젠킨스로 빌드된 각 서버의 이미지를 이용하여 배포
```
┗ 📜docker-compose.yml
```
