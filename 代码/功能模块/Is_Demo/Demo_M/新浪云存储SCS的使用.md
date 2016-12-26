# 新浪云存储SCS的使用 #
## 步骤 ##


- 需先[新浪云官网](http://www.sinacloud.com/)注册账号
- 在云存储SCS中新建一个bucket
- 上传一些文件，不如图片，txt文档等

### 最简单的访问 ###
将你的bucket和bucket中的文件设置ACL(设置权限)，使匿名用户可以访问

然后再浏览器中输入

https://**你的bucket**.sinacloud.net/文件在bucket中的相对路径

或者

https://sinacloud.net/**你的bucket**/文件在bucket中的相对路径

即可访问，但是这样安全性几乎为0

### 通过HTTP Request Header(Authorization)方式 ###
在控制台中URL签名工具中输入你的Bucket和Object(你文件在bucket中的相对路径)、Time，然后点击签名，即生成签名，然后复制Get方式的URL到浏览器中，即可通过get方式访问。当然也可以通过其他方式访问。

### 通过其他方式签名认证 ###
- 通过URL签名认证方式
- Cookie认证方式

在这里就不详细解释了，需要用到的请看[官方文档](http://www.sinacloud.com/doc/scs/guide/sign)

### 通过SDK生成签名 ###
[官方文档连接](http://open.sinastorage.com/?c=doc&a=sdk&k=android&t=sdk)

通过该SDK，即可在自己的程序中动态生成相关签名后的URL

还有很多其他功能，比如上传下载文件，删除新建bucket等，都在官方文档中有。
