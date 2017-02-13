# Android命名规范 #
## 布局文件的命名 ##
布局文件的命名规则使用"前缀_逻辑名"的方式，单词全部小写  

- Activity的布局文件命名为activity_xxx  
- Fragment的布局文件命名为fragment_xxx
- 自定义控件的布局文件命名为view_xxx
- 对话框的布局文件命名为dialog_xxx
- 列表项的布局文件命名为item_xxx  
- include的布局命名为include_xxx  

## 控件ID的命名 ##
布局中控件的id命名使用 "控件缩写_逻辑名"的方式，单词全部小写  

- Text的id命名为tv_xxxx
- ImageView的id命名为img_xxx
- Button的id命名为btn_xxx  

[控件缩写详见此处](http://blog.csdn.net/yfh1985sdq/article/details/17260429)

## 资源文件的命名 ##
资源文件的命名规则使用  ```前缀_模块名_逻辑名称``` 的方式，单词全部小写  

- 按钮的命名以btn作为前缀，例如btn_login.png，当按钮存在多种形态时，需要加上按钮的形态。
	- 例如```btn_login_nornal.png,btn_login_pressed.png```
	- 或者简写为```btn_login_n.png,btn_login_p.png```  
- 图标的命名以ic作为前缀，例如ic_share.png  
- 背景图片的命名以bg作为前缀，例如bg_main.png

## 类的命名 ##
类的命名遵循Java的类命名规范，也就是使用大驼峰命名法，同时需要根据类的具体用途引入Android相关的命名规则  

- Activity类需要以Activity作为后缀，例如MainActivity  
- Fragment类需要以Fragment作为后缀，例如HomeFragment  
- Service类需要以Service作为后缀，例如DownloadService  
- BroadcastReceiver类需要以Receiver作为后缀，例如PushReceiver  
- 工具类需要以Util作为后缀，例如NetworkUtil  
- 自定义的公共基础类以Base开头，例如BaseActivity  
- 单元测试的类以Test作为后缀，例如HashTest  


## 补充部分 ##


### 多使用花括号 ###

例如if、else、for、do、while等语句要和花括号一起使用，即使只有一条语句或者是空的，可要加上花括号。

错误做法，**容易在代码维护阶段引入bug**  

	if(isLogin)
		login();
	else
		unlogin();  

正确做法  

	if(isLogin){
		login();
	}else{
		unlogin();
	}  

### 列字符个数的限制 ###
为保证代码良好的可阅读性，超过Android Studio 竖线的代码 需换行。  

![](http://p1.bpimg.com/567571/a0779c5ebe8e2db7.png)  

### 空白的使用 ###
不要把所有的代码都写在一起，需要按照逻辑进行分组，语句的不同逻辑分组之间使用空行

### switch语句 ###
如果连续两个case之间明确不需要加break语句，建议添加//fall through 注解，方便代码维护者的理解，同时一定要实现default语句。一个符合规范的switch语句如下:  

	switch (input) {
        case 1:
        case 2:
			prepareOneOrTwo();
			//fall through
        case 3:
            handleOneTwoOrThree();
            break;
        default:
            handleLargeNumber(input);
    }  

### JavaDoc ###
标准的javaDoc常见的标记和含义如下。标准的javaDoc注解有助于Java文档的生成。

	/**
	 * Javadoc常见标记
	 *
	 * @param 方法参数的说明
	 * @author 模块的作者
	 * @version 模块的版本号
	 * @return 对方法返回值的说明
	 * @throws 方法抛出异常的描述
	 * @see 参考转向
	 * @deprecated 标记是否过时
	 */  

## 其他 ##
###Material Design颜色规范  

	<!--App主色调-->
    <color name="colorPrimary">#47aa39</color>
    <!--App主色调 (暗色)-->
    <color name="colorPrimaryDark">#689F38</color>
    <!--App主色调 (亮色)-->
    <color name="colorPrimaryLight">#DCEDC8</color>
    <!--App着重色-->
    <color name="colorAccent">#448AFF</color>
    <!--主文字-->
    <color name="colorPrimaryText">#212121</color>
    <!--次级文字-->
    <color name="colorSecondaryText">#727272</color>
    <!--hint文字-->
    <color name="colorHintText">d5d5d5</color>
    <!--风格线-->
    <color name="colorDivider">#B6B6B6</color>  

###Material Disign字体规范

	<!--超大号字体-->
	<dimen name="textSize_large_V32">32dp</dimen>
	<dimen name="textSize_large_V45">45dp</dimen>
	<dimen name="textSize_large_V56">56dp</dimen>
	<dimen name="textSize_large_V112">112dp</dimen>
	
	<!--大标题-->
	<dimen name="textSize_mainHead_V24">24dp</dimen>
	<dimen name="textSize_mainHead_V22">22dp</dimen>
	
	<!--appbar文字-->
	<dimen name="textSize_appBar_V20">20dp</dimen>
	
	<!--小标题-->
	<dimen name="textSize_subHead_V18">18dp</dimen>
	<dimen name="textSize_subHead_V16">16dp</dimen>
	
	<!--正文/按钮文字-->
	<dimen name="textSize_body_V14">14dp</dimen>
	
	<!--小号提示-->
	<dimen name="textSize_tip_V12">12dp</dimen>  

### String.xml、styles.xml等
根据模块进行分类  

	<resources>
	    <!--登陆-->
	    <string name="title_back">返回</string>
	    <string name="title_reg">注册</string>
	    <string name="login">登录</string>
	    <string name="login_page">登录页面</string>
	
	    <!-- Error View -->
	    <string name="copy_success">复制成功</string>
	    <string name="error_view_no_data">暂无内容</string>
	    <string name="error_view_loading">加载中…</string>
	    <string name="tip_network_error">没有可用的网络</string>
	
	    <!--LoadingDialog-->
	    <string name="dialog_loading">正在加载</string>
	    <string name="dialog_please_wait">请稍等...</string>
	
	    <!--BottomNavigationBar-->
	    <string name="home_page">主页</string>
	    <string name="music">音乐</string>
	    <string name="setting">设置</string>
	    <string name="device">设备</string>
	    <string name="scene">场景</string>
	    <string name="room">房间</string>
	
	    <!--MainActivity-->
	    <string name="click_again_return">再按一次退出程序</string>
	    <string name="add_new_device">添加新设备</string>
	</resources>



    

	  









