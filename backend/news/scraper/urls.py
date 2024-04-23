from django.urls import path
from .views import ScratchNews

urlpatterns = [
    path('', ScratchNews.as_view()),
]
