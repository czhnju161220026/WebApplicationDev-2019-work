# coding=utf-8
from bs4 import BeautifulSoup
import requests
import time
from tencent_news import tencent_news
from util import export_to_xml, export_to_DB


# 获取科技类新闻
def get_tech_news(header, url):
    result = requests.get(url, headers=header, timeout=20)
    content = result.content
    soup = BeautifulSoup(content, features='lxml', from_encoding=result.encoding)
    # 热点新闻
    titles_elements = soup.find_all('h2', attrs={'class': 'tit'})
    infos_elements = soup.find_all('div', attrs={'class': 'info'})
    hrefs_elements = soup.find_all('a', attrs={'class': 'focus-item'})
    titles = []
    dates = []
    hrefs = []
    news = []
    for title in titles_elements:
        titles.append(title.text)
    for info in infos_elements:
        dates.append(info.contents[2].text.split(' ')[0])
    for href in hrefs_elements:
        hrefs.append(href['href'])
    # 解析每一个新闻连接，读取
    for i in range(len(titles)):
        if 'html' in hrefs[i]:
            new = tencent_news(title=titles[i], date=dates[i], url=hrefs[i], category='科技')
            news.append(new)
    return news


# 获取腾讯体育新闻
def get_sports_news(header, url):
    result = requests.get(url, headers=header, timeout=20)
    content = result.content
    soup = BeautifulSoup(content, features='lxml', from_encoding=result.encoding)
    elements = soup.find_all('a', attrs={'class': 'newsv'})
    news = []
    for element in elements:
        title = element.text
        href = element['href']
        # 日期包含在超链接中
        tokens = href.split('/')
        if tokens[3] == 'a':
            raw = tokens[4]
            date = raw[0:4] + '-' + raw[4:6] + '-' + raw[6:8]
            # print(title + " " + date + " " + href)
            news.append(tencent_news(title=title, date=date, url=href, category='体育'))
    return news


def get_tencent():
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'
    }
    # 科技新闻
    url_tech1 = "http://new.qq.com/ch/tech"
    url_tech2 = 'https://new.qq.com/ch/digi/'
    url_tech3 = 'https://new.qq.com/ch/kepu/'

    # 体育新闻
    url_sport = 'https://sports.qq.com/'
    # 腾讯的经济新闻似乎有反爬虫机制，很难爬，所以经济新闻主要看新浪
    # 加入暂停，防止访问速度过快，导致被封IP
    tech_news1 = get_tech_news(headers, url_tech1)
    time.sleep(1)
    tech_news2 = get_tech_news(headers, url_tech2)
    time.sleep(1)
    tech_news3 = get_tech_news(headers, url_tech3)
    time.sleep(1)
    sport_news = get_sports_news(headers, url_sport)
    # 对新闻进行一个合并
    res = list(set(tech_news1).union(set(tech_news2).union(set(tech_news3).union(sport_news))))
    for new in res:
        new.get_content()
        time.sleep(0.5)
    fileName = str(int(time.time())) + '.xml'

    export_to_xml('tencent/'+fileName, res)
    print("新闻已经写入 tencent/%s" % fileName)
    count = export_to_DB(res)
    print("%d条新闻已经写入 Database" % count)

