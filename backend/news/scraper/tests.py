from django.test import TestCase

# Create your tests here.
import redis, json

conn = redis.StrictRedis(host='127.0.0.1', port=6379, db=0)
# conn.set('test', '{test1: test1, test2: test2}')

# print('test')
# print(conn.get('test'))

news_data = [
    {'title': '제목', 'preview': '본문', 'author': '신문사', 'date': '날짜', 'thum': '썸네일', 'link': 'link'},
    {'title': '제목', 'preview': '본문', 'author': '신문사', 'date': '날짜', 'thum': '썸네일', 'link': 'link'},
    {'title': '제목', 'preview': '본문', 'author': '신문사', 'date': '날짜', 'thum': '썸네일', 'link': 'link'},
    {'title': '제목', 'preview': '본문', 'author': '신문사', 'date': '날짜', 'thum': '썸네일', 'link': 'link'},
    {'title': '제목', 'preview': '본문', 'author': '신문사', 'date': '날짜', 'thum': '썸네일', 'link': 'link'}
]

conn.set('news',json.dumps(news_data, ensure_ascii=False))
print(conn.get('news').decode('utf-8'), type(conn.get('news').decode('utf-8')))