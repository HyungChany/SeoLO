from django.core.management.base import BaseCommand
from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.triggers.cron import CronTrigger
from .scraping import scrape

class Command(BaseCommand):
    help = '스크래핑 작업을 실행하도록 합니다.'
    
    def handle(self, *args, **options):
        scheduler = BackgroundScheduler()
        scheduler.start()

        scheduler.add_job(
            scrape,
            trigger=CronTrigger(hour='0,3,6,9,12,15,18,21'),  # 매 3시간마다 실행
            id='scraping_job',
            name='Run scraping task every 3 hours',
            replace_existing=True,
        )

        try:
            while True:
                pass
        except KeyboardInterrupt:
            # Ctrl+C가 눌렸을 때 스케줄러 종료
            scheduler.shutdown()
            