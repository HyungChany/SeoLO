from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
import json

class scratch_news(APIView):
    def get(self, request):
        context = {
            "test"
        }
        return Response(context, status=status.HTTP_200_OK)