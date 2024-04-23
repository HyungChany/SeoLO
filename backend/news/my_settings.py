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

CACHE_LOCATION = [
    ''
]
