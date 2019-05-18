# coding=utf-8
from bs4 import BeautifulSoup
import requests
import time
import datetime
from sina_news import sina_news
from util import export_to_xml, export_to_DB


def get_sina():
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'
    }
    url = "https://finance.sina.com.cn/"
    result = requests.get(url, headers=headers, timeout=20)
    content = result.content
    soup = BeautifulSoup(content, features='lxml', from_encoding='utf8')
    paragraphs = soup.find_all('p', attrs={'data-client': 'throw'})
    news = []
    for paragraph in paragraphs:
        elements = paragraph.findChildren()
        for element in elements:
            href = element['href']
            title = element.text
            date = datetime.datetime.now().strftime('%Y-%m-%d ')
            new = sina_news(title=title, date=date, url=href, category='经济',)
            news.append(new)
    # 不添加太多新闻
    num = len(news)
    if num > 10:
        news = news[0:10]
    for new in news:
        new.get_content()
    fileName = str(int(time.time())) + '.xml'
    export_to_xml('sina/' + fileName, news)
    print("新闻已经写入 sina/%s" % fileName)
    count = export_to_DB(news)
    print("%d条新闻已经写入 Database" % count)
