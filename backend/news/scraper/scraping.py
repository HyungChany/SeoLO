import requests, redis, json
from bs4 import BeautifulSoup

def scrape():
    news_data = []
    conn = redis.StrictRedis.from_url('redis://k10c104.p.ssafy.io:6380/0')

    url = 'https://search.daum.net/search?w=news&nil_search=btn&DA=STC&enc=utf8&cluster=y&cluster_page=1&q=%EC%95%88%EC%A0%84&p=1&sort=accuracy'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')

    articles_list = soup.find('ul', class_='c-list-basic')

    articles = articles_list.find_all('li')

    cnt = 0
    for article in articles:
        if article.has_attr('data-docid'):
            cnt += 1
            thumbnail_tag = article.find('div', class_='item-thumb')
            thumbnail = thumbnail_tag.find('img')['data-original-src'] if thumbnail_tag and thumbnail_tag.find('img') else None
            
            source_tag = article.find('span', class_='txt_info')
            source = source_tag.text.strip() if source_tag else None
            
            content_div = article.find('div', class_='item-title')
            title_tag = content_div.find('a') if content_div else None
            title = title_tag.text.strip() if title_tag else None
            link = title_tag['href'] if title_tag and 'href' in title_tag.attrs else None

            preview_tag = article.find('div', class_='item-contents')
            preview = preview_tag.find('p').text.strip() if preview_tag and preview_tag.find('p') else None

            date_tag = article.find('span', class_='gem-subinfo').find('span') if article.find('span', class_='gem-subinfo') else None
            date = date_tag.text.strip() if date_tag else None
            
            news_data.append({
                'title': title,
                'preview': preview,
                'author': source,
                'date': date,
                'thum': thumbnail,
                'link': link
            })
            if cnt > 4:
                break
    
    conn.set('news', json.dumps(news_data, ensure_ascii=False))
    conn.expire('news', 10800)
    # print(json.loads(conn.get('news').decode('utf-8')))

scrape()
