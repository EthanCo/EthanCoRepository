# HTTP协议总结 #
## 什么是HTTP协议 ##
超文本传出协议，是对客户端和服务器之间数据传输格式的规范  

[](http://i4.piimg.com/de411b8a708ddf0e.png)  

## TCP和IP协议 ##
IP协议规定了数据传输时的基本单元和格式。  
TCP协议提供了可靠的数据流传输服务的规则和约定。  

> TCP/IP通信协议是一种可靠的网络协议,它在通信的两端各建立一个Socket,从而在通信的两端之间形成网络虚拟链路  
> 一旦建成了虚拟的网络链路,两端的程序就可以通过虚拟链路进行通信  
> Java使用Socket对象来代表两端的通信接口,并通过Socket产生I/O流来进行网络通信  
> IP协议：使两台计算机使用同一种"语言"进行通信,只保证计算机能发送和接收分组数据  
> TCP协议：负责收集信息包,以适当次序放好传送,在接收端收到数据后再将其正确地还原(具有重发机制,保证数据无误)  
> 两者可分开使用,但功能互补,需要联网必须安装,常被称为TCP/IP协议  

## TCP/IP、HTTP协议和Socket的区别 ##
IP协议对应于网络层  
TCP协议对应于传输层  
HTTP协议对应于应用层  

socket是对TCP/IP协议的封装和应用  
TCP/IP解决数据如何在网络中传输  
HTTP协议:基于TCP/IP，解决如何包装数据  

[详情](http://jingyan.baidu.com/article/08b6a591e07ecc14a80922f1.html)

## TCP的三次握手 ##
SYN是TCP/IP建立连接时使用的握手信息。  
ACK为确认信息，表示发来的数据已确认接收无误。
![](http://i4.piimg.com/5c06ed1ed3ce553f.png)  

### 第一次握手 ###
客户端发送syn包(syn=j)到服务器，进入SYN_SEND状态，等待服务器确认。  

### 第二次握手 ###
服务器收到syn包，必须确认客户的SYN(ack=j+1)，同时自己也发送一个SYN包(sy=k)，即SYN+ACK包，同时服务器进入SYN_RECV状态。  

### 第三次握手 ###
客户端收到服务器的SYN+ACK包，厢服务器发送确认包ACK(ack=k+1)，此包发送完毕，客户端和服务器进入ESTABLISHED状态，完成三次握手。  


## HTTP1.0和HTTP1.1区别 ##
HTTP1.0每次请求都需要建立新的TCP连接，连接不能复用  
HTTP1.1新的请求可以在上次请求建立的TCP连接之上发送，连接可以复用，以提高效率。

> HTTP1.1在Request消息头里多了一个Host域，HTTP1.0没有这个域，Host:www.w3.org  
> HTTP1.1增加了OPTIONS，PUT,DELETE,TRACE,CONNECT这些Request方法   

## HTTPS ##
[Https通讯原理](http://jingyan.baidu.com/article/2fb0ba4048e15500f3ec5f7e.html) 

## HTTP工作流程 ##

1. 首先客户机与服务器建立连接
2. 建立连接后，客户机发送一个请求给服务器
3. 服务器接到请求后，给予相应的相应信息  
4. 客户端接收服务器所返回的信息，然后客户机与服务器断开连接  

> 如果在以上过程中的某一步出现错误，那么产生的错误将返回到客户端  

### 访问网址 ###
会通过浏览器根据URL向我们服务器发送一个请求(遵循HTTP协议)，路由器根据这个URL去解析相应的服务器地址信息，然后访问特定的服务器。  
之后，服务器返回给我们一个页面信息。  

## HTTP请求格式 ##
![](http://i4.piimg.com/3683525f07cd2ce7.png)  

![](http://i4.piimg.com/2d3f9276bf830489.png)  

### 请求行 ###
包括三个部分 请求方式、资源路径、HTTP协议版本，例： GET /路径 HTTP/1.1  
### 客户端网页哪些是GET方式请求？ 哪些是POST请求 ###
GET方式 ：

	1、用户直接在浏览器上手动输入url地址  
	2、<a href="url"></a>  
	3、<form method="get" > *form表单默认提交方式就是get 
POST方式： 
	
	<form method="post" >   

### GET提交方式和POST提交方式区别 ###
Get url地址后携带数据有大小限制 1K ,提交数据在url上显示以？分隔url和参数，参数采用键值对格式，多个参数使用& 符合分隔  
POST 数据在请求体中，不在url上显示 ，没有数据大小限制

> username=zhangsan 请求体和头信息之间存在一个空行 

### 头信息： 很多key -value  ###

####Accept: text/html,image/   
客户端可以接收文件类型 text/html HTML文件 image/ 任意格式图片
####Accept-Charset:ISO-8859-1
客户端可以识别编码字符集    
####[重要]Accept-Encoding: gzip
客户端可以识别压缩数据格式  
gzip是一种压缩格式 
####Accept-Language:zh-cn
客户端浏览器语言 
####Host: www.itcast.com:80
访问服务器地址 
####[重要]If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT
该请求的资源在客户端保存最后访问时间 （缓存有关）
####[重要]Referer: http://www.itcast.com/index.jsp
上一次请求访问页面地址 
####[比较重要]User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)
客户端浏览器类型版本 
####Connection: close/Keep-Alive
该次请求连接后，连接是保持还是关闭 1.0 关闭 1.1 保持 
####Date: Tue, 11 Jul 2000 18:23:51 GMT
请求时间 

## 请求 ##
###请求体
 	存放客户端提交post 请求 数据
	key=value&key=value&key=value ....   
	例如 ： username=sss 

###状态行 
分为三个部分： HTTP协议版本、状态码、描述信息  
例如: HTTP/1.1 200 OK   
100～199 Informational  请求信息不足 ，还需要其他信息才能处理  
200～299 Success  请求处理成功  
300～399 Redirection 服务器已经处理完毕，客户端还需要进一步动作  
400～499 Client Error  客户端错误  
500～599 Server Error  服务器端错误  
200 请求处理成功 302 客户端进行重定向 304 客户端访问资源没有被修改后，客户端访问本地缓存   404 访问资源不存在 500 服务器内部出错  
####借钱  
200  A 找 B 借钱 B 将钱 借个A   
302  A 找 B 借钱 B 通知 A 找C 借钱 --- A 找C 借钱  
304  A 找 B 借钱 ， B对A 说 借过了 --- A 使用 之前从B 借的钱  
404  没钱  
505  B 不存在了  

## 响应 ##
### HTTP响应格式 ###
![](http://i4.piimg.com/5f51c477b2288f11.png)
###响应头
####Location: http://www.it315.org/index.jsp
重定向地址 结合 302 一起使用  
* 重定向 两次请求 两次响应  
####Server:apache tomcat

> 服务器类型
####[重要]Content-Encoding: gzip
设置响应数据压缩格式，和请求 Accept-Encoding 对应  

> 如果响应数据经过压缩 传输体积更小，性能更好  

####Content-Length: 80
响应数据长度
####Content-Language: zh-cn  
响应数据语言
####Content-Type: text/html; charset=GB2312  
响应数据类型 
> 数据类型采用Mime协议规定类型 html文件 - text/html、 jpg文件 - image/jpeg  
####[重要]Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT
和请求头信息 If-Modified-Since一起使用，[控制服务器缓存]

> ETag: W/"13397-1184876416000" tomcat生成Etag 13397文件大小 1184876416000文件最后修改时间（精确毫秒）  
> 客户端下次请求时通过If-None-Match 携带刚刚 Etag信息 

####Refresh: 1;url=http://www.it315.org
网页自动跳转 
####Content-Disposition: attachment; filename=aaa.zip
文件下载时指定文件附件名称
####响应体
通常是HTML文件内容  
如果设置响应体进行gzip压缩，看到gzip压缩内容

>通过HTTP协议Range头信息 实现断点下载功能  
>在连接服务器目标资源进行下载过程中，传递Range头信息， 指定下载目标资源部分内容 ---- 实现断点下载功能 

 
