SECRET_KEY = 'django-insecure-^tp1k7xy*-axtcnhqze2yo&waew$el7!6oka6f$&1hk=(##_t+'

from news.settings import BASE_DIR

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': BASE_DIR / 'db.sqlite3',
    }
}