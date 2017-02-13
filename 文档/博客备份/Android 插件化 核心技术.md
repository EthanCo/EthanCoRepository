# Android 插件化 核心技术 #
## 加载类 ##

### ClassLoader ###
Java代码都是写在Class里面的，程序运行在虚拟机上时，虚拟机需要把需要的Class加载进来才能创建实例对象并工作，而完成这一个加载工作的角色就是ClassLoader。   
classLoader主要有以下两个子类  

- DexClassLoader 可以加载jar/apk/dex，可以从SD卡中加载未安装的apk;
- PathClassLoader只能加载系统中已经安装过的apk;   

classLoader是插件化开发的基础 ，一般会继承或使用DexClassLoader进行开发。  

### 进行加载 ###
#### 初始化 ####

1. 将apk文件拷贝到私有目录中
2. 遍历该私有目录，获取所有的apk文件  
2. 为每一个apk文件都创建一个单独的DexClassLoader  
3. 将该DexClassLoader加入存储ClassLoader的列表(以下称作AList)  

#### 加载 ####
1. 先从context.getClassLoader中进行查找Class
	1. 如果查找到，直接返回
	2. 如果没有查找到，再到AList列表中查找
3. 通过反射，将该class实例化、调用相关方法、成员变量  

## 加载资源 ##
#### Resources对象 ####

2. 创建AssetManager对象，通过反射调用其addAssetPath方法，传入apk路径
3. 通过该AssetManager对象，创建Resources对象
4. 对该resources对象加入到Map中
5. 返回Resources对象

### 获取插件资源 ###

	String str = resources.getString(resources.getIdentifier("app_name", "string", "com.ethanco.bundleapk"));
    Toast.makeText(MainActivity.this, "" + str, Toast.LENGTH_SHORT).show();

    int id = resources.getIdentifier("ic_launcher", "mipmap", "com.ethanco.bundleapk");
    Drawable drawable = resources.getDrawable(id);
    imgView.setImageDrawable(drawable);  

### 资源ID冲突问题 ###
插件和宿主的资源ID会有冲突，主流解决方案有以下几种  

1. 重写Context的getAssert、getResource之类的方法。(百度任玉刚方案 DynamicLoadApk) 但由于会导致资源ID冲突，需要重写AAPT，不同的插件使用不同的前缀，解决ID冲突，打包生成R文件(携程 DynamicAPK)
1. 重写AMS中保存的插件列表，从而让宿主和插件分别取加载各自的资源而不会冲突 (360张勇方案 DroidPlugin)
1. 在打包后，执行一种脚本，修改生成包中的资源ID (林广亮 Small方案)

## 启动Activity ##
### 动态替换 ###
动态替换，也就是Hook，可以在不同层次进行Hook  

#### 直接在Activity进行Hook ####
重写getAssert的几个方法，从而使用自己的ResourceManager和AssertPath (百度任玉刚方案 DynamicLoadApk)  

#### 在startActivity方法的位置进行Hook ####
可以在更抽象的层面，也就是startActivity方法的位置做Hook，涉及的类包括ActivityThread、Instrumentation等  (林广亮 Small方案)

#### 在AMS上进行Hook ####
最高层次则是在AMS上做修改(360张勇方案 DroidPlugin)，需要修改的类非常多，AMS、PMS等都需要改动。  

在越抽象的层次上做Hook，需要做的改动就越大，但好处是更加灵活

### 静态代理 ###
写一个PluginActivity继承自Activity基类，把Activity基类里面涉及生命周期的方法全都重写一遍，插件中的Activity是没有生命周期的，所以要让插件中的Activity都继承自PluginActivity，这样就有生命周期了。  

#### 反射方式 ####
在代理Activity中，通过反射的方式调用插件Activity的生命周期，从而启动插件Activity。

#### 接口方式 (百度任玉刚方案 DynamicLoadApk) ####
将Activity的生命周期提取为一个接口，插件Activity继承该接口，代理Activity通过该进口调用插件Activity的生命周期，从而启动Activity  

## 热修复 ##
### 通过Dex加载顺序 ###
由于ClassLoader的双亲代理模型，加载有个顺序问题。有两个类名相同的类，会使用先被加载的类。通过让插件的Dex优先加载，从而实现热更新。 (阿里巴巴 AndFix方案)  

### Instant run ###
Instant run是Android2.0的新特性，它可以在不重启Activity的情况下实现代码的替换。  

通过第一次编译的时候注入```$change```字段 (宿主类的代理对象，包含宿主类的所有方法)。    
宿主在执行方法时，会先判断它的代理（```$change```）是否为空，如果不为空，就执行代理里的方法。这样当我们修改了某个类方法里的代码，AS会自动的创建一个该类的代理（```xx$override.java
```),并将代理赋值给该类的```$chang```字段，这样我们的修改在不重启Activity的情况下也能生效了。  

基于Instant run的思想，会涌现出一大批的热修复的方案。 比如美团的robust，期待开源。  


