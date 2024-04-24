import requests, redis, json
from bs4 import BeautifulSoup

def scrape():

    news_data = []
    conn = redis.StrictRedis.from_url("redis://127.0.0.1:6379")

    url = 'https://search.daum.net/search?w=news&nil_search=btn&DA=STC&enc=utf8&cluster=y&cluster_page=1&q=%EC%95%88%EC%A0%84&p=1&sort=accuracy'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')

    articles_list = soup.find('ul', class_='c-list-basic')

    articles = articles_list.find_all('li')

    for article in articles[:5]:
        thumbnail_tag = article.find('div', class_='item-thumb').find('img')
        thumbnail = thumbnail_tag['data-original-src'] if thumbnail_tag else None
        
        source = article.find('span', class_='txt_info').text.strip()
        
        content_div = article.find('div', class_='item-title')
        title = content_div.find('a').text.strip()
        link = content_div.find('a')['href']

        preview = article.find('div', class_='item-contents').find('p').text.strip()

        date = article.find('span', class_='gem-subinfo').find('span').text.strip()

        # 결과 출력
        # print("제목:", title)
        # print("프리뷰:", preview)
        # print("링크:", link)
        # print("썸네일:", thumbnail)
        # print("시간:", date)
        # print("신문사:", source)
        # print()
        
        news_data.append({'title': title, 'preview': preview, 'author': source, 'date': date, 'thum': thumbnail, 'link': link})
    
    conn.set('news', json.dumps(news_data, ensure_ascii=False))
    conn.expire('news', 10800)
    print(json.loads(conn.get('news').decode('utf-8')))

scrape()
