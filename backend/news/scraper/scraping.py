import requests
from bs4 import BeautifulSoup

def scrape_data():
    url = ''
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')
    
    print(soup.title.text)

