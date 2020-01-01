# TTKun

# 目录
1. [软件架构设计介绍](#1%E8%BD%AF%E4%BB%B6%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1%E4%BB%8B%E7%BB%8D)
2. [使用帮助](#2%E4%BD%BF%E7%94%A8%E5%B8%AE%E5%8A%A9)

# 1.软件架构设计介绍

## 1.1 设计简介
    * context包简单的模拟了spring的IOC跟DI。
    * crawler包实现具体的爬虫功能：通过解析自定义的爬虫规则，执行相应的操作。

## 1.2 爬虫执行流程介绍
    1. 通过读取配置文件的crawler.url，自动获取规则文件。
    2. 通过fastjson解析规则文件转成实体类。
    3. JsonRuleAction通过解析的json实体，通过委派模式调用相应的action类执行相应的操作。

# 2.使用帮助

## 2.1 下载
选择最新版本根据自己电脑的系统环境选择相应的压缩包下：[下载地址](https://gitee.com/natsuki_kining/TTKun/releases)。

## 2.2 解压
此工具为免安装版，解压后就可以用，不需要安装。

## 2.3 编写自己的自定义规则
1. 根据`rule.json`里定义的规则和自己想爬取数据，编写自己的自定义规则.
2. 文件命名为网站的uri加上`.json`
3. 写好后放到rule文件夹里。

## 2.4 修改配置文件
1. 打开`application.properties`，
2. 修改`application.properties`  
    2.1 修改`crawler.url`的值为自己想爬取的网页地址。  
    2.2 修改`crawler.name`的值为自己下载文件文件的名称。  
    2.3 修改`crawler.save.path`的值为自己下载文件保存的地址。  
    2.4 `crawler.chapter.start`为从此章节开始下载，值为标题的内容。      
    2.5 `crawler.chapter` 为只下载此章节的内容，值为标题的内容。  
    2.6 `crawler.rule.path` 为规则文件的地址，默认为当前文件夹下的rule文件夹。  
    2.7 `download.use.multithreading.enable`默认为true，如果为true则开启多线程模式，根据`download.manga.thread.pool.size`,`download.chapter.thread.pool.size`里的配置，下载的速度会比单线程高出很多。
