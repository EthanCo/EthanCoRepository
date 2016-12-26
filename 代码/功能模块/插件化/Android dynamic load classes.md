## Android dynamic load classes ##
Android类由DexClassLoader加载  

> dexPath通常是"/data/../*.apk"，先用作创建pathList  

DexClassLoader不支持.os文件  

> 为了让应用程序能自动复制插件包到应用存储目录，需要支持.so后缀。做法就是模拟 压缩包加载代码块，创建一个dex元素，再反射添加到宿主class loader里的dexPathList。  


	Context context = getApplicationContext();
	File plugin = new File(context.getApplicationInfo().dataDir, "lib/**.so");
	Element element = makeDexElement(plugin); // dalvik.system.DexPathList$Element
	context.getClassLoader() // dalvik.system.DexClassLoader
        .@dexPathList // dalvik.system.DexPathList
        .@dexElements // dalvik.system.DexPathList$Element []
        .insert(element, 0);

## Android dynamic load resources ##

安卓资源由AssertManager加载  

> 应用启动时，系统会为其创建一个AssetManager实例，并由addAssetPath方法添加资源搜索路径，默认添加：
> 1."/framework/base.apk" - Android base resources (base)  
> 2."/data/app/*.apk" - The launching apk resources (host)  

所有的资源需要通过一个唯一的id来访问  
为避免id冲突，宿主以及各个插件之间的资源id需要分段处理。  

	使用Gradle插件修改aapt生成的资源id  

我们的最终方案是对资源包进行重新打包，重设资源id  

## Android dynamic register activities ##

Android activities 受 Instrumentation 监控。  

由startActivityForResult方法启动，通过instrumentation的execStartActivity方法激活生命周期。  (1)

在ActivityThread的performLaunchActivity方法中通过instrumentation的newActivity方法实例化Activity。  (2)

动态注册Activity，首先在宿主manifest中注册一个命名特殊的占坑Activity来欺骗(1)以获得生命周期，再欺骗(2)来获得插件Activity实例。  

	要做的就是封装一个instrumentatino，替换掉宿主的。

## gradle-small-plugin ##
Gradle插件，用来打包安卓组件包。  

gradle-small-plugin内部注册了5个插件  

工程配置插件 net.wequick.small  
宿主配置插件 net.wequick.small.host  
App组件打包插件 net.wequick.small.application  
Library组件打包插件 net.wequick.small.library  
资源组件打包插件  net.wequick.small.asset  

当在Root的build中配置apply plugin 'net.wequick.small'时，将会遍历其所有的子模块(subprojects):  

- 对app模块应用HostPlugin  
- 对app.*模块应用AppPlugin  
- 对lib.*模块应用LibraryPlugin  
- 对[others].*模块应用AssetPlugin  

#### 用于配置宿主 ####
- 增加jni libs 目录smallLibs  
	- 该目录用以存放其他组件包生成*.so文件
	- 宿主运行时将自动复制其下*.so文件至应用程序缓存。  
- 复制release签名配置到debug模式  
	- 各组件编译时使用宿主的release签名，以使得release版本的宿主可以通过对比签名来校验插件。为此在调试运行宿主的时候也使用release签名，避免调用时签名校验失败而中断。  
- 增加buildLib task
	- 宿主程序作为一个壳，通过该task预编译生成宿主的jar文件，供其他组件引用以便打包时分离。  

#### AppPlugin ####
用于App组件打包  
		
- 分离宿主、公共库的类与资源
	- 移除宿主的主题资源 (AppCompat)，将插件中的Theme.AppCompat等资源id替换为宿主程序对应的id (主题的递归应用在native层的ResourcesType.cpp中实现，无法在Java层做动态替换，为此我们需要在编译阶段，提前做好主题的资源id映射)
	- 移除其它公共资源
	- 移除公共类
- 分配资源id
	- 为保证整合在一起的程序资源id不冲突，对组件包分配[0x03，0x7e]之间的package id

#### LibraryPlugin ####
用于公共库组件打包:  
	
- 在编译App组件包时，使用com.android.library模式  
- 在编译自己时，切换为com.android.application模式，并按App组件打包  
- 增加buildLib task，生成jar包，供其它组件引用以便打包时分离  

#### AssetPlugin ####
用于资源组件打包:  

- 复制assets目录下文件
- 生成二进制AndroidManifest.xml
	- 携带versionCode以供插件版本对比
	- 使插件可以通过PackageManager的getPackageArchiveInfo方法获取签名信息
- 签名组件包

此类打包忽略所有java文件，直接将assets目录下的文件进行压缩打包  

arr-small内部内置了WebBundleLauncher(网页资源组件包加载器)用来加载web.*的网页组件。  

基于这个架构，可以扩展自己的自定义组件，比如扩展支持Markdown组件  
	
- 新建md.* 模块，该模块的src/main/assets 目录中添加index.md 文件  
- 新建MdBundleLauncher类来加载md.*模块


