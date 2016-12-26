## Android  Drawable Importer ##
Android图片自适应
## Android ButterKnife Zelezny ##
Android视图注入库，一键导入  
需要在grade里添加compile 'com.jakewharton:butterknife:7.0.0'
## Android Parcelable Code Generator ##
![Android Parcelable Code Generator](http://img.blog.csdn.net/20150411092625879?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbWFvc2lkaWFveGlhbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)  

android中实现方式有两种，第一、实现 Serializable接口，这种比较简单，直接声明就好；第二种,实现Parcelable接口，这种方式就比较复杂，往往需要写多些代码去实现，不过效率就比较高，还是值得推荐这种方式。使用android parcelable code generator，可以让你一键实现复杂的序列号接口，超赞！！！ 
## GsonFormat ##
现在大多数服务端api都以json数据格式返回，而客户端需要根据api接口生成相应的实体类，这个插件把这个过程自动化了
![GsonFormat](http://stormzhang.com/image/gson_format.gif)
## LeakCanary ##
强烈推荐，帮助你在开发阶段方便的检测出内存泄露的问题，使用起来更简单方便。
英文不好的这里有雷锋同志翻译的中文版LeakCanary [中文使用说明](http://www.liaohuqiu.net/cn/posts/leak-canary-read-me/)
## material-design-icon ##
material-design图片资源生成器 icon资源都是官方提供
![material-design-icon](https://raw.githubusercontent.com/konifar/android-material-design-icon-generator-plugin/master/docs/capture.gif)  
[传送门](https://github.com/konifar/android-material-design-icon-generator-plugin)
## Genymotion模拟器 ##
Genymotion是测试Android应用程序，使你能够运行Android定制版本的旗舰工具。它是为了VirtualBox内部的执行而创建的，并配备了一整套与虚拟Android环境交互所需的传感器和功能。使用Genymotion能让你在多种虚拟开发设备上测试Android应用程序，并且它的模拟器比默认模拟器要快很多。

# 详情信息 #
[8 个最优秀的 Android Studio 插件](http://www.codeceo.com/article/8-android-studio-plugins.html)  
[AndroidStudio实用插件汇总](http://www.open-open.com/lib/view/open1437833191209.html)  
[强烈推荐android studio用的几个插件](http://blog.csdn.net/liang5630/article/details/46366901)

## 新发现 201604 ##
###  ###
[Lifecycle-Sorter](https://github.com/armandAkop/Lifecycle-Sorter)  
可以根据Activity或者fragment的生命周期对其生命周期方法位置进行先后排序， 快捷键Ctrl + alt + K  

### JsonOnlineViewer ###
可实现直接在android studio中调试接口数据，可以选择请求类型，自定义请求头及请求体，json数据格式化后展示，配合着Gsonformat会不会不错呢？  
![](http://static.open-open.com/news/uploadImg/20151009/20151009221132_47.gif)  

### folding-plugin ###
[folding-plugin](https://github.com/dmytrodanylyk/folding-plugin)  
可以给资源文件分组，并且不移动文件，也不会创建文件夹：Android File Grouping Plugin  

### dagger-intellij-plugin ###
[dagger-intellij-plugin](https://github.com/square/dagger-intellij-plugin)  
dagger可视化辅助工具。
### CodeGlance ###
[CodeGlance](https://github.com/Vektah/CodeGlance)  
可用于快速定位代码，类似于Sublime编辑器右侧定位视图
dagger可视化辅助工具。
## 现阶段没什么用的，不知道以后会不会更新 ##
### Android  Holo Colors Generator 个人感觉已过时###
[在这下载主题](http://www.android-holo-colors.com/)  
然后复制到项目中，就可以使用该主题了
### Selector自动生成器SelectorChapek 现阶段只能生成Drawable，现阶段感觉没什么用###
根据资源自动生成相应的selector，免得对selector文件复制粘贴了  
[android-selector-chapek](https://github.com/inmite/android-selector-chapek)  
[](https://github.com/konifar/android-material-design-icon-generator-plugin/releases)  

### 大全 ###
[Android插件-知乎](http://www.zhihu.com/question/28527388)  
[AndroidStudio好用的插件](http://www.androidchina.net/4918.html)