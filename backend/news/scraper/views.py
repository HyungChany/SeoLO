from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
import redis, json
from django.conf import settings
from .scraping import scrape

class ScratchNews(APIView):
    def get(self, request):
        conn = redis.StrictRedis.from_url(settings.CACHES["default"]["LOCATION"])

        news_data_redis = conn.get('news')

        if news_data_redis:
            return Response(json.loads(news_data_redis.decode('utf-8')), status=status.HTTP_200_OK)
        else:
            scrape()
            return Response(json.loads(conn.get('news').decode('utf-8')), status=status.HTTP_200_OK)
