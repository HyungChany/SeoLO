from django.urls import path
from .views import scratch_news

urlpatterns = [
    path('', scratch_news.as_view()),
]