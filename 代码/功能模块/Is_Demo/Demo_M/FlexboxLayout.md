# FlexboxLayout #

## 添加依赖 ##

	dependencies {
    	...	
		compile 'com.google.android:flexbox:0.2.3'
	}  

## 最简单的使用 ##

### 通过XML ###

	<com.google.android.flexbox.FlexboxLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:app="http://schemas.android.com/apk/res-auto"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    app:flexWrap="wrap"
	    app:alignItems="stretch"
	    app:alignContent="stretch" >

	    <TextView
	        android:id="@+id/textview1"
	        android:layout_width="120dp"
	        android:layout_height="80dp"
	        app:layout_flexBasisPercent="50%"
	        />
	
	    <TextView
	        android:id="@+id/textview2"
	        android:layout_width="80dp"
	        android:layout_height="80dp"
	        app:layout_alignSelf="center"
	        />
	
	    <TextView
	        android:id="@+id/textview3"
	        android:layout_width="160dp"
	        android:layout_height="80dp"
	        app:layout_alignSelf="flex_end"
	        />
	</com.google.android.flexbox.FlexboxLayout>

### 通过代码 ###

	FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flexbox_layout);
	flexboxLayout.setFlexDirection(FlexboxLayout.FLEX_DIRECTION_COLUMN);
	
	View view = flexboxLayout.getChildAt(0);
	FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) view.getLayoutParams();
	lp.order = -1;
	lp.flexGrow = 2;
	view.setLayoutParams(lp);  


## 父元素的属性 ##

### FlexDirection ###
子元素的排列方向，元素的排列方向为主轴的方向，该属性有四种取值，不同取值对应不同的主副轴  

![](http://img.blog.csdn.net/20160823193924415?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  

- row: 默认，水平方向，起点在左边  
- rowrow_reverse: 水平方向，起点在右边  
- column: 垂直方法，起点在上  
- column_reverse:垂直方法，起点在下  

![](https://github.com/google/flexbox-layout/raw/master/assets/flex-direction.gif)  

### flexWrap ###
是否换行，默认为noWrap
	
- noWrap(默认) 不换行
- wrap  换行
- wrap_reverse 表示副轴反转  


![](https://github.com/google/flexbox-layout/raw/master/assets/flex-wrap.gif)  

### justifyContent ###
表示控件沿主轴对齐方向  

- flex_start (default) 默认左对齐 
- flex_end 右对齐 
- center 居中对齐 
- space_between 两端对齐，模块间隔等距 
- space_around 每个项目两侧的间隔相等。所以，项目之间的间隔比项目与边框的间隔大一倍。  

![](https://github.com/google/flexbox-layout/blob/master/assets/justify-content.gif)  

### alignItems ###
表示元素在副轴上的对齐方向（针对单行）

- stretch (default) 默认，如果项目未设置高度或设为auto，将占满整个容器的高度。 
- flex_start 副轴轴顶部对齐 
- flex_end 副轴轴底部对齐 
- center 副轴轴居中对齐 
- baseline 项目的第一行文字的底部对齐  

![](https://github.com/google/flexbox-layout/raw/master/assets/align-items.gif)  

### alignContent ###
表示控件在副轴上的对齐方向（针对多行元素）  

![](https://github.com/google/flexbox-layout/raw/master/assets/align-content.gif)  


## 子元素属性 ##
### layout_order(integer) ###
是用该属性克控制子View的排列顺序，负值在前，正值在后，默认为1  

![](https://github.com/google/flexbox-layout/raw/master/assets/layout_order.gif)  

### layout_flexGrow (float) ###
定义项目的放大比例，默认为0，即如果存在剩余空间，也不放大。跟 LinearLayout 中的weight属性一样。  

![](https://github.com/google/flexbox-layout/raw/master/assets/layout_flexGrow.gif)  

### layout_flexShrink (float) ###
定义了项目的缩小比例，默认为1，即如果空间不足，该项目将缩小。  

如果所有项目的 layout_flexShrink 属性都为1，当空间不足时，都将等比例缩小。如果一个项目的flex-shrink属性为0，其他项目都为1，则空间不足时，前者不缩小。  

负值对该属性无效。  


![](https://github.com/google/flexbox-layout/raw/master/assets/layout_flexShrink.gif)  

### layout_alignSelf ###

layout_alignSelf 属性允许单个子元素有与其他子元素不一样的对齐方式，可覆盖 alignItems 属性。默认值为auto，表示继承父元素的 alignItems 属性，如果没有父元素，则等同于stretch。  

- auto (default)
- flex_start
- flex_end
- center
- baseline
- stretch  

该属性可能取6个值，除了auto，其他都与align-items属性完全一致。  

![](https://github.com/google/flexbox-layout/raw/master/assets/layout_alignSelf.gif)  

### layout_flexBasisPercent ###
定义了在分配多余空间之前，子元素占据的main size主轴空间，浏览器根据这个属性，计算主轴是否有多余空间。它的默认值为auto，即子元素的本来大小。  

FlexboxLyaout可轻松代替[FlowLayout](http://blog.csdn.net/lmj623565791/article/details/38352503)  

![](http://img.blog.csdn.net/20140802223809500?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbG1qNjIzNTY1Nzkx/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  

## 不同之处 ##

跟传统的CSS中的Flexbox布局有些不同的是：

1. 没有 flex-flow 属性
2. 没有 flex 属性
3. layout_flexBasisPercent 属性即为CSS中 flexbox 中的 flexBasis 属性
4. 不支持 min-width 和 min-height 两个属性

## 其他 ##
> 参考  
> [http://blog.csdn.net/u012702547/article/details/52293593](http://blog.csdn.net/u012702547/article/details/52293593)  
> [http://blog.csdn.net/qq_16131393/article/details/51860680](http://blog.csdn.net/qq_16131393/article/details/51860680)  
> [http://www.oschina.net/news/73442/google-flexbox-layout](http://www.oschina.net/news/73442/google-flexbox-layout)  