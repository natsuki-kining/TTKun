# TTKun

#### 介绍
TTKun 
JDK1.8

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

# 结构介绍
启动入口：com.natsuki_kining.ttkun.Application

com.natsuki_kining.ttkun.context
简单模拟spring的IOC跟DI，不想引入太多依赖包。

com.natsuki_kining.ttkun.spiders
主程序

* concurrent 处理多线程
* core 核心代码
    * analysis 解析
    * down 下载
        下载文件，第几话第几页、下载多少页等
        管理文件夹、文件名等等io操作
    * rule 自定义解析用的规则
        导入规则
        增删改查
    * url 管理url
* swing 图形界面
