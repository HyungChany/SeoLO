SECRET_KEY = 'django-insecure-^tp1k7xy*-axtcnhqze2yo&waew$el7!6oka6f$&1hk=(##_t+'

from pathlib import Path

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': Path(__file__).resolve().parent / 'db.sqlite3',
    }
}

CORS_ORIGIN_WHITELIST = [
    'https://localhost:3000',
    'https://127.0.0.1:3000'
]





from django.shortcuts import render
from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from selenium import webdriver

# Create your views here.
class scraping_news(APIView):
    def get(self, request):
        driver = webdriver.Chrome('./chromedriver')
        context = driver.get("https://www.google.com/search?q=%EC%95%88%EC%A0%84&sca_esv=369bb2d90aa2509e&biw=1056&bih=1531&tbm=nws&sxsrf=ACQVn083B3go2NAsyXOQURkyE9iTic_K2A%3A1713769222837&ei=BgsmZoveMvTN1e8P-Ymj-AY&udm=&ved=0ahUKEwiL-YPun9WFAxX0ZvUHHfnECG845gEQ4dUDCA4&oq=%EC%95%88%EC%A0%84&gs_lp=Egxnd3Mtd2l6LW5ld3MiBuyViOyghEgAUABYAHAAeACQAQCYAQCgAQCqAQC4AQzIAQCYAgCgAgCYAwCSBwCgBwA&sclient=gws-wiz-news")

        return Response(context, status=status.HTTP_200_OK)
