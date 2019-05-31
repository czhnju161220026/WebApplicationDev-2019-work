## 依赖
强烈推荐使用清华的pip源，可以提升安装速度，只要在后面加上 -i https://pypi.tuna.tsinghua.edu.cn/simple
+ pip3 install beautifulsoup4
+ pip3 install lxml
+ pip3 install pymysql

## 运行说明
1. 在命令行执行python starter.py 
程序会以1一小时一次的频率爬取腾讯新闻和新浪新闻的热点。写入xml，同时存入数据库。
存入数据库时会进行去重处理。 
2. 注意，如果想要中途退出，最好在等待时段退出，否则可能会造成连接无法正常释放
## 欠缺
不能爬取图片