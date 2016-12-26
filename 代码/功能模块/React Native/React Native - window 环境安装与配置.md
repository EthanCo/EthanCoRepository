# React Native - window 环境安装与配置 #
接触RN也有10来天了，记录下环境安装上遇到的坑和工具的配置。环境为win10 64位。

## 环境安装 ##
环境安装大体按照[官网](http://reactnative.cn/docs/0.28/getting-started.html#content)上来就行，但有些地方要注意，以下，做下补充。
### 安装Chocolatey ###
[安装教程](http://jingyan.baidu.com/article/642c9d34fd91a1644a46f708.html)  

	请使用管理员身份运行cmd.exe

### python环境变量配置 ###

不配置python环境变量会报以下错误

	gyp ERR! configure error
	gyp ERR! stack Error: Can't find Python executable "python", you can set the PYTHON env variable.
	gyp ERR! stack     at failNoPython (C:\Program Files (x86)\nodejs\node_modules\npm\node_modules\node-gyp\lib\configure.js:401:14)
	gyp ERR! stack     at C:\Program Files (x86)\nodejs\node_modules\npm\node_modules\node-gyp\lib\configure.js:356:11
	gyp ERR! stack     at FSReqWrap.oncomplete (fs.js:117:15)
	gyp ERR! System Windows_NT 10.0.10586
	gyp ERR! command "C:\\Program Files (x86)\\nodejs\\node.exe" "C:\\Program Files (x86)\\nodejs\\node_modules\\npm\\node_modules\\node-gyp\\bin\\node-gyp.js" "rebuild"
	gyp ERR! cwd C:\WINDOWS\system32\AwesomeProject\node_modules\utf-8-validate
	gyp ERR! node -v v6.2.2
	gyp ERR! node-gyp -v v3.3.1
	gyp ERR! not ok
	npm WARN install:utf-8-validate@1.2.1 utf-8-validate@1.2.1 install: `node-gyp rebuild`
	npm WARN install:utf-8-validate@1.2.1 Exit status 1

故进行python环境变量的配置
[python环境变量配置](http://jingyan.baidu.com/article/48206aeafdcf2a216ad6b316.html)  

### node.js安装注意事项 ###

按照官方文档上写的

	choco install nodejs.install  

执行，会安装最新版本，这会导致在后面init项目的时候，出现以下错误

	C:\WINDOWS\system32\myProject\node_modules\bufferutil>if not defined npm_config_node_gyp (node "C:\Program Files (x86)\nodejs\node_modules\npm\bin\node-gyp-bin\\..\..\node_modules\node-gyp\bin\node-gyp.js" rebuild )  else (node "" rebuild )
	MSBUILD : error MSB1009: 项目文件不存在。
	开关: build/binding.sln
	gyp ERR! build error
	gyp ERR! stack Error: `C:\Windows\Microsoft.NET\Framework\v4.0.30319\msbuild.exe` failed with exit code: 1
	gyp ERR! stack     at ChildProcess.onExit (C:\Program Files (x86)\nodejs\node_modules\npm\node_modules\node-gyp\lib\build.js:276:23)
	gyp ERR! stack     at emitTwo (events.js:106:13)
	gyp ERR! stack     at ChildProcess.emit (events.js:191:7)
	gyp ERR! stack     at Process.ChildProcess._handle.onexit (internal/child_process.js:204:12)
	gyp ERR! System Windows_NT 10.0.10586
	gyp ERR! command "C:\\Program Files (x86)\\nodejs\\node.exe" "C:\\Program Files (x86)\\nodejs\\node_modules\\npm\\node_modules\\node-gyp\\bin\\node-gyp.js" "rebuild"
	gyp ERR! cwd C:\WINDOWS\system32\myProject\node_modules\bufferutil
	gyp ERR! node -v v6.2.2
	gyp ERR! node-gyp -v v3.3.1
	gyp ERR! not ok
	npm WARN install:bufferutil@1.2.1 bufferutil@1.2.1 install: `node-gyp rebuild`
	npm WARN install:bufferutil@1.2.1 Exit status 1

后来，发现安装Visual Studio2015后，错误消失。  

其实，我们完全可以去[node.js中文网](http://nodejs.cn/)去下载长期支持版本

![](http://7xrn7f.com1.z0.glb.clouddn.com/16-7-18/74708077.jpg)  

此时，便不用再安装Visual Studio2015了。  

## WebStorm配置 ##
由于Android Studio和WebStorm同源于IntelliJ IDEA，所以使用WebStorm非常顺手，同时，WebStorm又是前端的开发神器，所以，开发工具当然选WebStorm了。  
以下做下RN配置

### 切换JavaScript版本 ###
	
	file->settings->Languages & Frameworks->Java Script language version ->设置为JSX Harmony

![](http://7xrn7f.com1.z0.glb.clouddn.com/16-7-18/4080146.jpg)

### ReactNative 代码智能提醒 ###

	git clone https://github.com/virtoolswebplayer/ReactNative-LiveTemplate  

在WebStorm上安装
	
	file -> import settings -> ReactNative.jar


###使用方法

####通用方法
直接输入组件 或 Api 名称的首字母, 比如想要 View ,只要输入 V自动提示代码里就会看到 View

####StyleSheet属性提示
首先 按下 command + J , 然后输入属性名的 首字母  
如: 输入 f ,会自动提示 fontSize,fontFamily,fontStyle...等等

## 其他 ##
###替换为国内镜像
由于众所周知的网络原因，react-native命令行从npm官方源拖代码时会遇上麻烦。请先将npm仓库源替换为国内镜像

	npm config set registry https://registry.npm.taobao.org --global
	npm config set disturl https://npm.taobao.org/dist --global

### window上reload失败 ###

我只想说，我重启电脑后，就好了。。。

### 虚拟机上找不到menu按键 ###

在cmd中输入

	adb shell input keyevent 82

即相当于按下了一次menu键

### 关于Android设备的连接 ###
要保证adb只连接了一台Android机器 (包括虚拟机和真机)  
可以用 adb devices 进行查看当前连接着adb的机器

还有，RN只支持Android4.1以上的机器