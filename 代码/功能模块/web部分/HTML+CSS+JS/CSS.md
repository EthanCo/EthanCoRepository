# CSS #
	font-size:12px; 	<!--字体大小-->
	color:#930; 		<!--颜色-->
	text-align:center;	<!--居中-->

## Style ##
	<style type="text/css">
	table tr td,th{border:1px solid #000;}
	</style>

上述代码是用 css 样式代码（后面章节会详细讲解），为th，td单元格添加粗细为一个像素的黑色边框。

## 类选择器 ##

.类选器名称{css样式代码;}

	第一步：使用合适的标签把要修饰的内容标记起来，如下：
	<span>胆小如鼠</span>
	
	第二步：使用class="类选择器名称"为标签设置一个类，如下：
	<span class="stress">胆小如鼠</span>
	
	第三步：设置类选器css样式，如下：
	.stress{color:red;}/*类前面要加入一个英文圆点*/

### 为一个标签设置多个类选择器
	.stress{
	    color:red;
	}
	.bigsize{
	    font-size:25px;
	}
	<p>到了<span class="stress bigsize">三年级</span>下学期时，我们班上了一节公开课...</p>


### ID选择器 ###
在很多方面，ID选择器都类似于类选择符，但也有一些重要的区别：

1、为标签设置id="ID名称"，而不是class="类名称"。

2、ID选择符的前面是井号（#）号，而不是英文圆点（.）。

### 类和ID选择器的区别 ###

#### 相同点： ####
可以应用于任何元素
#### 不同点： ####
1、ID选择器只能在文档中使用一次。与类选择器不同，在一个HTML文档中，ID选择器只能使用一次，而且仅一次。而类选择器可以使用多次。  
2、可以使用类选择器词列表方法为一个元素同时设置多个样式。我们可以为一个元素同时设多个样式，但只可以用类选择器的方法实现，ID选择器是不可以的（不能使用 ID 词列表）。

### 子选择器 ###
还有一个比较有用的选择器子选择器，即大于符号(>),用于选择指定标签元素的第一代子元素。如右侧代码编辑器中的代码：

	.food>li{border:1px solid red;}

这行代码会使class名为food下的子元素li（水果、蔬菜）加入红色实线边框。

### 包含(后代)选择器 ###
包含选择器，即加入空格,用于选择指定标签元素下的后辈元素。如右侧代码编辑器中的代码：

	.first  span{color:red;}

这行代码会使第一段文字内容中的“胆小如鼠”字体颜色变为红色。

请注意这个选择器与子选择器的区别，子选择器（child selector）仅是指它的直接后代，或者你可以理解为作用于子元素的第一代后代。而后代选择器是作用于所有子后代元素。后代选择器通过空格来进行选择，而子选择器是通过“>”进行选择。

总结：>作用于元素的第一代后代，空格作用于元素的所有后代。

通用选择器



通用选择器是功能最强大的选择器，它使用一个（*）号指定，它的作用是匹配html中所有标签元素，如下使用下面代码使用html中任意标签元素字体颜色全部设置为红色：

	* {color:red;}

### 伪类选择符 ###
更有趣的是伪类选择符，为什么叫做伪类选择符，它允许给html不存在的标签（标签的某种状态）设置样式，比如说我们给html中一个标签元素的鼠标滑过的状态来设置字体颜色：
a:hover{color:red;}

上面一行代码就是为 a 标签鼠标滑过的状态设置字体颜色变红。这样就会使第一段文字内容中的“胆小如鼠”文字加入鼠标滑过字体颜色变为红色特效。

### 边框 ###
	p{border:1px solid red;}
### 字体-微软雅黑 ###
	body{font-family:"Microsoft Yahei";}
### 粗体 ###
	p span{font-weight:bold;}
### 斜体 ###
	p a{font-style:italic;}
### 下划线 ###
	p a{text-decoration:underline;}
### 价格(原价)删除线 ###
	 .oldPrice{text-decoration:line-through;}
### 段落缩进 ###
	p{text-indent:2em;}
### 段落排版--行间距（行高） ###
	p{line-height:1.5em;}
### 中文字间隔、字母间隔设置：###
注意：这个样式使用在英文单词时，是设置字母与字母之间的间距。  

	h1{
    	letter-spacing:50px;
	}
### 单词间距设置 ###
h1{
    word-spacing:50px;
}

### 段落排版--对齐--居中 ###
	h1{
	    text-align:center;
	}

居左
	
	h1{
	    text-align:left;
	}

居右

	h1{
	    text-align:right;
	}


### 流动模型 ###
块状元素会默认会占用一行  
内联元素会在所处的包含元素内从左到右水平分布显示

### 层模型 ###
#### 绝对定位absolute ####
如果想为元素设置层模型中的绝对定位，需要设置position:absolute(表示绝对定位)，这条语句的作用将元素从文档流中拖出来，然后使用left、right、top、bottom属性相对于其最接近的一个具有定位属性的父包含块进行绝对定位。如果不存在这样的包含块，则相对于body元素，即相对于浏览器窗口。

如下面代码可以实现div元素相对于浏览器窗口向右移动100px，向下移动50px。  

	div{
	    width:200px;
	    height:200px;
	    border:2px red solid;
	    position:absolute;
	    left:100px;
	    top:50px;
	}
	<div id="div1"></div>


#### 相对定位relative ####
如果想为元素设置层模型中的相对定位，需要设置position:relative（表示相对定位），它通过left、right、top、bottom属性确定元素在正常文档流中的偏移位置。相对定位完成的过程是首先按static(float)方式生成一个元素(并且元素像层一样浮动了起来)，然后相对于以前的位置移动，移动的方向和幅度由left、right、top、bottom属性确定，偏移前的位置保留不动。  

如下代码实现相对于以前位置向下移动50px，向右移动100px;  

	#div1{
	    width:200px;
	    height:200px;
	    border:2px red solid;
	    position:relative;
	    left:100px;
	    top:50px;
	}
	
	<div id="div1"></div>

![](http://img.mukewang.com/53a00d2b00015c4b06190509.jpg)

什么叫做“偏移前的位置保留不动”呢？

大家可以做一个实验，在右侧代码编辑器的19行div标签的后面加入一个span标签，在标并在span标签中写入一些文字。如下代码：
<body>
    <div id="div1"></div><span>偏移前的位置还保留不动，覆盖不了前面的div没有偏移前的位置</span>
</body>

效果图：
![](http://img.mukewang.com/541a4bfc0001abef05940489.jpg)

从效果图中可以明显的看出，虽然div元素相对于以前的位置产生了偏移，但是div元素以前的位置还是保留着，所以后面的span元素是显示在了div元素以前位置的后面。

#### 固定定位fixed ####
fixed：表示固定定位，与absolute定位类型类似，但它的相对移动的坐标是视图（屏幕内的网页窗口）本身。由于视图本身是固定的，它不会随浏览器窗口的滚动条滚动而变化，除非你在屏幕中移动浏览器窗口的屏幕位置，或改变浏览器窗口的显示大小，因此固定定位的元素会始终位于浏览器窗口内视图的某个位置，不会受文档流动影响，这与background-attachment:fixed;属性功能相同。以下代码可以实现相对于浏览器视图向右移动100px，向下移动50px。并且拖动滚动条时位置固定不变。

	#div1{
    width:200px;
    height:200px;
    border:2px red solid;
    position:fixed;
    left:100px;
    top:50px;
	}
	<p>文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本文本。</p>
	....

### 盒模型简写 ###
1、如果top、right、bottom、left的值相同，如下面代码：
	
	margin:10px 10px 10px 10px;

可缩写为：

	margin:10px;

2、如果top和bottom值相同、left和 right的值相同，如下面代码：

	margin:10px 20px 10px 20px;

可缩写为：

	margin:10px 20px;

3、如果left和right的值相同，如下面代码：

	margin:10px 20px 30px 20px;

可缩写为：

	margin:10px 20px 30px;

注意：padding、border的缩写方法和margin是一致的。

### 颜色值缩写 ###
关于颜色的css样式也是可以缩写的，当你设置的颜色是16进制的色彩值时，如果每两位的值相同，可以缩写一半。

例子1：

	p{color:#000000;}

可以缩写为：

	p{color: #000;}

例子2：

	p{color: #336699;}

可以缩写为：

	p{color: #369;}

### 字体缩写 ###
网页中的字体css样式代码也有他自己的缩写方式，下面是给网页设置字体的代码：

	body{
	    font-style:italic;
	    font-variant:small-caps; 
	    font-weight:bold; 
	    font-size:12px; 
	    line-height:1.5em; 
	    font-family:"宋体",sans-serif;
	}


这么多行的代码其实可以缩写为一句：

	body{
	    font:italic  small-caps  bold  12px/1.5em  "宋体",sans-serif;
	}

注意：

1、使用这一简写方式你至少要指定 font-size 和 font-family 属性，其他的属性(如 font-weight、font-style、font-varient、line-height)如未指定将自动使用默认值。

2、在缩写时 font-size 与 line-height 中间要加入“/”斜扛。

一般情况下因为对于中文网站，英文还是比较少的，所以下面缩写代码比较常用：
	
	body{
	    font:12px/1.5em  "宋体",sans-serif;
	}

只是有字号、行间距、中文字体、英文字体设置。

### 颜色值 ###

在网页中的颜色设置是非常重要，有字体颜色（color）、背景颜色（background-color）、边框颜色（border）等，设置颜色的方法也有很多种：

1、英文命令颜色

前面几个小节中经常用到的就是这种设置方法：

	p{color:red;}

2、RGB颜色

这个与 photoshop 中的 RGB 颜色是一致的，由 R(red)、G(green)、B(blue) 三种颜色的比例来配色。

	p{color:rgb(133,45,200);}

每一项的值可以是 0~255 之间的整数，也可以是 0%~100% 的百分数。如：
p{color:rgb(20%,33%,25%);}

3、十六进制颜色

这种颜色设置方法是现在比较普遍使用的方法，其原理其实也是 RGB 设置，但是其每一项的值由 0-255 变成了十六进制 00-ff。

	p{color:#00ffff;}

### 长度值 ###

长度单位总结一下，目前比较常用到px（像素）、em、% 百分比，要注意其实这三种单位都是相对单位。

1、像素

像素为什么是相对单位呢？因为像素指的是显示器上的小点（CSS规范中假设“90像素=1英寸”）。实际情况是浏览器会使用显示器的实际像素值有关，在目前大多数的设计者都倾向于使用像素（px）作为单位。

2、em 

就是本元素给定字体的 font-size 值，如果元素的 font-size 为 14px ，那么 1em = 14px；如果 font-size 为 18px，那么 1em = 18px。如下代码：

	p{font-size:12px;text-indent:2em;}

上面代码就是可以实现段落首行缩进 24px（也就是两个字体大小的距离）。
下面注意一个特殊情况：

但当给 font-size 设置单位为 em 时，此时计算的标准以 p 的父元素的 font-size 为基础。如下代码：

html:
<p>以这个<span>例子</span>为例。</p>

	css:
	p{font-size:14px}
	span{font-size:0.8em;}

结果 span 中的字体“例子”字体大小就为 11.2px（14 * 0.8 = 11.2px）。

3、百分比

	p{font-size:12px;line-height:130%}

设置行高（行间距）为字体的130%（12 * 1.3 = 15.6px）。

### 水平居中设置-行内元素 ###

	text-align:center;

### 水平居中设置-定宽块状元素 ###

#### 定宽块状元素 ####
	 margin:20px auto;
也可以写成：

	margin-left:auto;
	margin-right:auto;

#### 不定宽块状元素 ####
#####方法一:加入 table 标签  
第一步：为需要设置的居中的元素外面加入一个 table 标签 ( 包括 <tbody>、<tr>、<td> )。

第二步：为这个 table 设置“左右 margin 居中”（这个和定宽块状元素的方法一样）。

举例如下：

html代码：

	<div>
	<table>
	  <tbody>
	    <tr><td>
	    <ul>
	        <li><a href="#">1</a></li>
	        <li><a href="#">2</a></li>
	        <li><a href="#">3</a></li>
	    </ul>
	    </td></tr>
	  </tbody>
	</table>
	</div>
	
	css代码：
	<style>
	table{
	    margin:0 auto;
	}
	
	ul{list-style:none;margin:0;padding:0;}
	li{float:left;display:inline;margin-right:8px;}
	</style>

#####方法二
第二种方法：改变块级元素的 display 为 inline 类型，然后使用 text-align:center 来实现居中效果。如下例子：

html代码：

	<body>
	<div class="container">
	    <ul>
	        <li><a href="#">1</a></li>
	        <li><a href="#">2</a></li>
	        <li><a href="#">3</a></li>
	    </ul>
	</div>
	</body>

css代码：

	<style>
	.container{
	    text-align:center;
	}
	.container ul{
	    list-style:none;
	    margin:0;
	    padding:0;
	    display:inline;
	}
	.container li{
	    margin-right:8px;
	    display:inline;
	}
	</style>

这种方法相比第一种方法的优势是不用增加无语义标签，简化了标签的嵌套深度，但也存在着一些问题：它将块状元素的 display 类型改为 inline，变成了行内元素，所以少了一些功能，比如设定长度值。


#####方法三
方法三：通过给父元素设置 float，然后给父元素设置 position:relative 和 left:50%，子元素设置 position:relative 和 left:-50% 来实现水平居中。

代码如下：

	<body>
	<div class="container">
	    <ul>
	        <li><a href="#">1</a></li>
	        <li><a href="#">2</a></li>
	        <li><a href="#">3</a></li>
	    </ul>
	</div>
	</body>
	
	css代码：
	<style>
	.container{
	    float:left;
	    position:relative;
	    left:50%
	}
	
	.container ul{
	    list-style:none;
	    margin:0;
	    padding:0;
	    
	    position:relative;
	    left:-50%;
	}
	.container li{float:left;display:inline;margin-right:8px;}
	</style>

这种方法可以保留块状元素仍以 display:block 的形式显示，优点不添加无语议表标签，不增加嵌套深度，但它的缺点是设置了 position:relative，带来了一定的副作用。

这三种方法使用得都非常广泛，各有优缺点，具体选用哪种方法，可以视具体情况而定。

### 其他 ###
有一个有趣的现象就是当为元素（不论之前是什么类型元素，display:none 除外）设置以下 2 个句之一：
position : absolute
float : left 或 float:right

元素会自动变为以 display:inline-block 的方式显示，当然就可以设置元素的 width 和 height 了且默认宽度不占满父元素。

### 笔记 ###
- 对于li列表来说，color设置前面的序列(点)，background设置内容的背景，font-color设置内容的字体颜色。  
- odd 奇数，even 偶数
- CSS3中的三种动画:tranform形变动画/transition缓动动画/animation逐帧动画;

