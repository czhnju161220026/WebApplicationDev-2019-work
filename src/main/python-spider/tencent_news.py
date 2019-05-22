# coding=utf-8
from bs4 import BeautifulSoup
import requests


class tencent_news:
    # 构造函数
    def __init__(self, title, date, url, category):
        self.title = title
        self.date = date
        self.url = url
        self.category = category
        self.content = None
        self.upstream = '腾讯新闻'
        self.title = self.title.replace('\'', ' ')
        self.title = self.title.replace('\"', ' ')

    # 比较器，根据title进行去重
    def __eq__(self, other):
        return self.title == other.title

    # hash
    def __hash__(self):
        return self.title.__hash__()

    # 获取内容
    def get_content(self):
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'
        }
        result = requests.get(self.url, headers=headers, timeout=20)
        content = result.content
        soup = BeautifulSoup(content, features='lxml', from_encoding=result.encoding)
        paragraphs = soup.find_all('p', attrs={'class': 'one-p'})
        if len(paragraphs) == 0:
            paragraphs = soup.find_all('p', attrs={'class': 'text'})
        try:
            content = ''
            for paragraph in paragraphs:
                text = paragraph.text
                content += text
            self.content = content
            self.content = self.content.replace('(', '[')
            self.content = self.content.replace(')', ']')
            self.content = self.content.replace('\'', ' ')
            self.content = self.content.replace('\"', ' ')
        except:
            pass

    def __str__(self):
        return self.title + " " + self.date + " " + self.category + " " + self.content[0:15]
