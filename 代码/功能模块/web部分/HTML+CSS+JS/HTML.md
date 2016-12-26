# HTML #
<h1>标题</h1>
<h2>标题</h2>
<h3>标题</h3>
<p>段落</p>
<div>相对于容器，可把一些独立的逻辑划分出来</div>
<img src="http://p2.so.qhimg.com/t01517ff8ca5e6185ac.jpg"  href="超链接" alt="下载失败时的替换文本" title ="提示文本，图像可以是GIF，PNG，JPEG格式的图像文件">
<em>斜体</em>
<br/>换行:xhtml1.0写法(主流)
<br>换行:html4.01写法
</br>空标签
<strong>粗体</strong>  
<span>标签是没有语义的，它的作用就是为了设置单独的样式用的。<span>  
<q>引用文本(短文本)，默认添加双引号，语义：引用别人的话</q>
<blockquote>引用文本(长文本)</blockquote>
空&nbsp;&nbsp;&nbsp;格  
xhtml1.0版本水平横线(主流) <hr/>
html4.01版本水平横线 <hr>
<s>文字 横线 打折 这个 叫做 物理字体</s>
<address>联系地址信息</address>
<code>代码</code>
<pre>
	多行代码  
	多行代码
</pre>
<ul>
<li>列表1</li>
<li>列表2</li>
</ul>

<ol>
   <li>序号 </li>
   <li>序号</li>
   <li>序号</li>
</ol>

<a href="www.baidu.com" title="点击进入">超链接</a>  
<a href="www.baidu.com" title="点击进入" target="_blank">超链接，新浏览器窗口打开</a>
 ![a标签还可用作链接Email地址](http://img.mukewang.com/52da52200001e00e07930061.jpg)  

##HTML文件基本结构  

	<html>
	<head>
		<title></title>
	</head>
	<body>
		内容
	</body>
	</html>

##lable标签
label标签不会向用户呈现任何特殊效果，它的作用是为鼠标用户改进了可用性。如果你在 label 标签内点击文本，就会触发此控件。就是说，当用户单击选中该label标签时，浏览器就会自动将焦点转到和标签相关的表单控件上（就自动选中和该label标签相关连的表单控件上）。  
<lable>lable</lable>

##head标签
	<head>
    	<title>...</title>
    	<meta>
    	<link>
    	<style>...</style>
    	<script>...</script>
	</head>

###title标签
网页标题

	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>认识head标签</title>
    </head>

### 使用表单标签，与用户交互 ###
表单(form)是可以把浏览者输入的数据传送到服务器端，这样服务器端程序就可以处理表单传过来的数据。

	<form method="传送方式" action="服务器文件">  

form ：form标签是成对出现的  
action ：浏览者输入的数据被传送到的地方,比如一个PHP页面(save.php)。    
method ：数据传送的方式（get/post）。  
type：  
当type="text"时，输入框为文本输入框;  
当type="password"时, 输入框为密码输入框。  
name：为文本框命名，以备后台程序ASP 、PHP使用  
value：为文本输入框设置默认值。(一般起到提示作用)  

  
**注意:**  
1、所有表单控件（文本框、文本域、按钮、单选框、复选框等）都必须放在<form></form>标签之间（否则用户输入的信息可提交不到服务器上哦！）。  
2、method:post/get的区别这一部分内容属于后端程序员考虑的问题。感兴趣的小伙伴可以查看本小节的wiki，里面有详细介绍。

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>表单标签</title>
</head>
<body>
<form method="post" action="save.php">
<label for="username">用户名:</label>
<input type="text"  name="username" id="username" value="" />
<label for="pass">密码:</label>
<input type="password"  name="pass" id="pass" value="" />    
<input type="submit" value="确定"  name="submit" />
<input type="reset" value="重置" name="reset" />
</form>  
</body>
</html>  

注意这两个属性可用css样式的width和height来代替：col用width、row用height来代替。

<textarea cols="50" rows="10">文本域，支持多行文本输入</textarea>

### 单选框、复选框 ###
1、type:

   当 type="radio" 时，控件为单选框

   当 type="checkbox" 时，控件为复选框

2、value：提交数据到服务器的值（后台程序PHP使用）

3、name：为控件命名，以备后台程序 ASP、PHP 使用

4、checked：当设置 checked="checked" 时，该选项被默认选中

注意:同一组的单选按钮，name 取值一定要一致，比如上面例子为同一个名称“radioLove”，这样同一组的单选按钮才可以起到单选的作用。

### 下拉列表框 ###
<form action="save.php" method="post" >
    <label>爱好:</label>
    <select>
      <option value="看书">看书</option>
      <option value="旅游">旅游</option>
      <option value="默认选择" selected="selected">运动</option>
      <option value="购物">购物</option>
    </select>
</form>

在select标签中设置multiple="multiple"属性，就可以实现多选功能
### 提交按钮 ###
当用户需要提交表单信息到服务器时，需要用到提交按钮。
<form  method="post" action="save.php">
    <label for="myName">姓名：</label>
    <input type="text" value=" "/>
    <input type="submit" value="提交"/>
</form>
  
type：只有当type值设置为submit时，按钮才有提交作用

value：按钮上显示的文字

### 重置按钮 ###
使输入框恢复到初始状态。
<form action="save.php" method="post" >
    <lable>姓名:</lable>
    <input type="text" value=" "/>
    <input type="reset" value="重置"  />
</form>

type：只有当type值设置为reset时，按钮才有重置作用

value：按钮上显示的文字


### 表格 ###
<table summary="表格简介文本">
<caption>标题文本</caption>
<tbody>
<tr>
<th>班级</th>
<th>学生数</th>
<th>平均成绩</th>
</tr>
<tr>
<td>一班</td>
<td>30</td>
<td>89</td>
</tr>
<tr>
<td>二班</td>
<td>35</td>
<td>85</td>
</tr>
<tr>
<td>三班</td>
<td>32</td>
<td>80</td>
</tr>
</tbody>