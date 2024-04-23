from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
import json
# from apscheduler.schedulers.background import BackgroundScheduler

class scratch_news(APIView):
    def get(self, request):
        context = {
            "test"
        }
        return Response(context, status=status.HTTP_200_OK)

# sched = BackgroundScheduler()

# @sched.scheduled_job('cron',year='해',month='월',day='일',hour='시', minute = '분',second='초' ,name = 'schedulerName')
# def schedulerF():
#     ...

# sched.start()