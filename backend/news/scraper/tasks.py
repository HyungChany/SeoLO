from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.triggers.cron import CronTrigger
from scraping import scrape

def start_scheduler():
    help = "scraping 시간 설정"

    scheduler = BackgroundScheduler()
    scheduler.start()

    scheduler.add_job(
        scrape,
        trigger=CronTrigger(hour='0, 3, 6, 9, 12, 15, 18, 21'),  # 매 초마다 실행
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

if __name__ == "__main__":
    start_scheduler()
