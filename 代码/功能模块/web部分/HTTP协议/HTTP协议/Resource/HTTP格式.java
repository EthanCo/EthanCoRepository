第一部分 HTTP 请求
请求完整格式 包括三个部分 ： 请求行、头信息、请求体 

	1、请求行：请求中第一行信息  ---- 包括三个部分 请求方式、资源路径、HTTP协议版本
		例如： GET / HTTP/1.1 
		HTTP请求方式有几种 ： POST、GET、HEAD、OPTIONS、DELETE、TRACE、PUT
		常用两种： GET 、 POST 

		客户端网页哪些是GET方式请求？ 哪些是 POST 请求
		GET方式 ：1、用户直接在浏览器上手动输入url地址  2、<a href="url"></a>  3、<form method="get" > *form表单默认提交方式就是get 
		POST方式： 1、<form method="post" >

		GET提交方式和POST提交方式区别 ？
		Get url地址后携带数据有大小限制 1K ,提交数据在url上显示 
		* http://localhost/day04/url?username=zhangsan 格式 url?name=value&name=value&name=value... 
		以？分隔url和参数，参数采用键值对格式，多个参数使用& 符合分隔

		POST 数据在请求体中，不在url上显示 ，没有数据大小限制 
		* username=zhangsan 请求体和头信息直接存在一个空行 

	2、头信息： 很多key -value 
		Accept: text/html,image/   ----- 客户端可以接收文件类型 text/html HTML文件 image/ 任意格式图片
		Accept-Charset: ISO-8859-1 ----- 客户端可以识别编码字符集  
		[重要]Accept-Encoding: gzip -----  客户端可以识别压缩数据格式 gzip是一种压缩格式 
		Accept-Language:zh-cn ----- 客户端浏览器语言 
		Host: www.itcast.com:80  ----- 访问服务器地址 
		[重要]If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT ----- 该请求的资源在客户端保存最后访问时间 （缓存有关）
		[重要]Referer: http://www.itcast.com/index.jsp ---- 上一次请求访问页面地址 
		[比较重要]User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0) ---- 客户端浏览器类型版本 
		Connection: close/Keep-Alive    ---- 该次请求连接后，连接是保持还是关闭 1.0 关闭 1.1 保持 
		Date: Tue, 11 Jul 2000 18:23:51 GMT ---- 请求时间 

		/*Referer作用 防止客户盗链数据 ：通过google搜索页面直接下载 其它网站数据 （盗链）*/

		模拟浏览器访问服务器 获得 refererServlet当中信息
		1) URL 连接 ----基于：HTTP协议
		2) Socket 连接 -----基于：TCP协议 


	3、请求体： 存放客户端提交post 请求 数据
		key=value&key=value&key=value ....   
		例如 ： username=sss 


第二部分 HTTP响应
响应数据 分为三个部分 ：状态行、头信息、响应体 
	1、状态行 分为三个部分： HTTP协议版本、状态码、描述信息
		例如: HTTP/1.1 200 OK 
		100～199 Informational  请求信息不足 ，还需要其他信息才能处理
		200～299 Success  请求处理成功
		300～399 Redirection 服务器已经处理完毕，客户端还需要进一步动作
		400～499 Client Error  客户端错误
		500～599 Server Error  服务器端错误

		200 请求处理成功 302 客户端进行重定向 304 客户端访问资源没有被修改后，客户端访问本地缓存 404 访问资源不存在 500 服务器内部出错
		借钱
		200  A 找 B 借钱 B 将钱 借个A 
		302  A 找 B 借钱 B 通知 A 找C 借钱 --- A 找C 借钱
		304  A 找 B 借钱 ， B对A 说 借过了 --- A 使用 之前从B 借的钱
		404  没钱
		505  B 不存在了 

	2、响应头 
		Location: http://www.it315.org/index.jsp ---- 重定向地址 结合 302 一起使用 
		* 重定向 两次请求 两次响应 
		Server:apache tomcat ----- 服务器类型
		[重要]Content-Encoding: gzip ---- 设置响应数据压缩格式   ---- 和请求 Accept-Encoding 对应
		* 如果响应数据经过压缩 传输体积更小，性能更好 

		没有压缩情况下  13605字节  http://localhost/docs/
		如何设置tomcat压缩 
		* 通过设置 tomcat/conf/server.xml 设置响应数据压缩 
		<Connector port="80" protocol="HTTP/1.1" 
					   connectionTimeout="20000" 
					   redirectPort="8443" compressableMimeType="text/html,text/xml,text/plain" compression="on"/>
		* 清除浏览器缓存，重启tomcat 
		4244字节 http://localhost/docs/

		Content-Length: 80  ----- 响应数据长度
		Content-Language: zh-cn  ----- 响应数据语言
		Content-Type: text/html; charset=GB2312  ----- 响应数据类型 
		* 数据类型采用Mime协议规定类型 html文件 ---- text/html、 jpg文件---- image/jpeg
		* 在 tomcat/conf/web.xml 中查看文件mime类型 

		[重要]Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT  ----- 和请求头信息 If-Modified-Since一起使用，[控制服务器缓存]
		* ETag: W/"13397-1184876416000" tomcat生成Etag 13397文件大小 1184876416000文件最后修改时间（精确毫秒）
		* 客户端下次请求时通过If-None-Match 携带刚刚 Etag信息 

		Refresh: 1;url=http://www.it315.org ----- 网页自动跳转 
		Content-Disposition: attachment; filename=aaa.zip ----- 文件下载时指定文件附件名称

		控制该程序在客户端不缓存
		Expires: -1 
		Cache-Control: no-cache  
		Pragma: no-cache   
		* 对于动态程序，经常修改， 禁止浏览器缓存内容
		/*使用三句是因为浏览器兼容性问题
		response.setDateHeader("expires",-1);
		response.setDateHeader("Cache-Control","no-cache ");
		response.setDateHeader("Pragma","no-cache");
		*/

		Connection: close/Keep-Alive   ---- 响应后连接是否关闭
		Date: Tue, 11 Jul 2000 18:23:51 GMT --- 响应时间 

		重点：302结合Location进行重定向 、在tomcat配置gzip压缩 、tomcat缓存策略 、禁止浏览器缓存 

	3、响应体
		通常HTML 文件内容  ---- 如果设置响应体进行gzip压缩，看到gzip压缩内容

		-------------------------------------------------------------------------------------------------
		通过HTTP协议Range头信息 实现断点下载功能 
		在连接服务器目标资源进行下载过程中，传递Range头信息， 指定下载目标资源部分内容 ---- 实现断点下载功能 
		 
		使用URL类 模拟客户端访问 WebRoot/info.txt 下载文件中内容



[HTTP 和 HTML 关系 ？]
	HTTP 通信协议 规定数据传输格式
	HTML 网页设计语言，静态网页数据 
	HTTP 传输格式 、HTML 传输内容

HTTP/1.0、HTTP/1.1 区别？
	1.0 一次 与服务器连接 只能获得一个资源 
	1.1 一次与服务器连接 ，连续获得多个资源 

思考题：
	一个web页面中，使用img标签引用了三幅图片，当客户端访问服务器中的这个web页面时，客户端总共会访问几次服务器，即向服务器发送了几次HTTP请求。
	* 三幅图片地址是否相同 
	如果三幅图片地址都不相同 --- 4次请求
