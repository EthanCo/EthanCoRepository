# OKHTTP之缓存配置详解 #
把重复请求的数据缓存在本地，并设置超时时间，在规定时间内，客户端不再向远程请求数据，而是直接从本地缓存中取数据。这样一来提高了响应速度，二来节省了网络带宽（也就是节省了钱）。  

## 缓存分类 ##
HTTP请求有客户端和服务端之分。因此缓存也可以分为两个类型 服务端侧和客户端侧。  

### 服务端缓存 ###
服务端缓存又分为代理服务器缓存和反向代理服务器缓存。  常见的CDN(内容分发网络)就是服务器缓存。  

当浏览器重复访问一张图片地址时，CDN会判断这个请求有没有缓存。如果有，直接返回这个缓存的请求回复，而不在需要让请求到达真正的服务地址，这么做的目的是减轻服务端的运算压力。

### 客户端缓存 ###
主要指浏览器(如IE、Chrome等)，当然包括我们的OKHTTPClient。

1. 客户端第一次请求网络时，服务器返回回复信息。
	1. 如果数据正常，客户端缓存在本地的缓存目录。
2. 当客户端再次访问同一个地址时，客户端会检查本地有没有缓存
	1. 如果有缓存，并数据没有过期，直接使用缓存的内容

## 缓存中重要的概念 ##
[HTTP调试工具 Fiddler使用教程](http://jingyan.baidu.com/article/5d6edee221f0b399ebdeec7f.html)  


服务端返回的信息:  
![](http://mmbiz.qpic.cn/mmbiz_png/v1LbPPWiaSt4CR6hH9cwAzVOaHT80Qp5BJiaNwrfb0vPOC39c3FkbuoiaSBjmlr1xM7GPUvrFicYSUTcW42whSG3Sw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1)  

Cache-control是由服务器返回的Response中添加的头信息，它的目的是告诉客户端是从本地读取缓存还是从服务器读取信息。它有不同的值，每一个值有不同的作用。  
	
详见 [OKHTTP之缓存配置详解](http://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650237860&idx=1&sn=d66e75f6f7752ededdcaa3ce780862d3&chksm=88639acbbf1413dd170ba41a67035c62811b489cfc7a405977ae23254205a6b3acb99358b1f2&mpshare=1&scene=1&srcid=1212Ay2nqkC9BBdKP9IOOVWA#rd)

