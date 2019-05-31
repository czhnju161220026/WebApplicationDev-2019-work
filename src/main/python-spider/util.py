# coding=utf-8
from xml.etree.ElementTree import ElementTree, Element
import pymysql
import pymysql.cursors


def indent(elem, level=0):
    """美化写入文件的内容"""
    i = "\n" + level * "  "
    if len(elem):
        if not elem.text or not elem.text.strip():
            elem.text = i + "  "
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
        for elem in elem:
            indent(elem, level + 1)
        if not elem.tail or not elem.tail.strip():
            elem.tail = i
    else:
        if level and (not elem.tail or not elem.tail.strip()):
            elem.tail = i


def export_to_xml(path, news):
    root = Element('articles')
    tree = ElementTree(root)
    for new in news:
        child = Element('article')
        root.append(child)
        title = Element('title')
        title.text = new.title
        child.append(title)
        date = Element('date')
        date.text = new.date
        child.append(date)
        category = Element('category')
        category.text = new.category
        child.append(category)
        upstream = Element('upstream')
        upstream.text = new.upstream
        child.append(upstream)
        content = Element('content')
        content.text = new.content
        child.append(content)
        root.append(child)
    indent(root, 0)
    tree.write(path, 'UTF-8')


def export_to_DB(news):
    connection = pymysql.connect(host='localhost',
                                 user='webuser',
                                 password='webuser',
                                 db='web_db',
                                 port=3306,
                                 charset='utf8')
    try:
        with connection.cursor() as cursor:
            sql = 'select MAX(NID) from NEWS'
            cursor.execute(sql)
            max_id = 0
            for row in cursor.fetchall():
                max_id = int(row[0])
            count = 0
            for new in news:
                exist = False
                # 去重，看相同标题的新闻是否存在
                sql = 'select * from NEWS where HEADER ="%s" ' % new.title
                # print(new.title)
                cursor.execute(sql)
                if 0 != len(cursor.fetchall()):
                    exist = True
                if not exist:
                    count += 1
                    max_id += 1
                    sql = 'insert into NEWS value(%d,"%s","%s","%s","%s","%s")' % (
                        max_id, new.date, new.title, new.content, new.category, new.upstream)
                    cursor.execute(sql)
            connection.commit()
    finally:
        connection.close()
    #返回新增新闻数目
    return count
