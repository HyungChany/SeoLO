# 베이스 이미지로 Python 공식 이미지를 사용합니다.
FROM python:3.10

# 환경 변수 설정
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app 

# 의존성 설치
COPY requirements.txt /app

# 최신 버전의 pip를 설치합니다.
RUN /usr/local/bin/python -m pip install --upgrade pip

# 필요한 라이브러리 설치
RUN pip install --no-cache-dir -r requirements.txt

# 소스 코드 복사
COPY ./ /app

# gunicorn 설치
RUN pip install gunicorn

# Python 경로 설정 (my_setting.py 파일이 있는 디렉토리를 포함)
ENV PYTHONPATH=/app/news

# Docker 컨테이너 내에서 실행될 명령을 지정합니다.
CMD ["gunicorn", "--bind", "0.0.0.0:8000", "news.wsgi:application"]