# coding=utf-8
from get_tencent import get_tencent
from get_sina import get_sina
import time

if __name__ == '__main__':
    while(True):
        print("开始爬取新闻")
        get_tencent()
        get_sina()
        print("一轮爬取结束，进入等待")
        time.sleep(3600)
