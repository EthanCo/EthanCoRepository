# Android 随笔记 #
将会把一些小的细碎的知识点记录在此处，方便以后查阅。

##JVM和DVM的区别

> JVM：(Dalvik Virtual Machine)  Java虚拟机  
> DVM：(Dalvik Virtual Machine)  Dalvik虚拟机  

JVM编译后生成的是.class文件 最后.jar包  
而DVM，多了一步.dex文件，最后打包apk。  
所以JVM执行的是.class文件，而DVM执行的是.dex文件。  

	JVM    .Java—–>.class—–>.jar  
	运行在内存的 栈(栈是内存当中特殊的数据结构)  
	DVM    .java—–>.class——>.dex—–(加上其它资源文件)—->apk  
	运行在CPU的 寄存器  

JVM：里含有多个.class文件，每个.class文件都包含文件头，这样IO操作效率低。
但是DVM中，看不到.class文件了，一个都没看到，这是因为dex工具，去掉了app里所有.class文件的冗余信息，再整合到.dex文件中，减少了IO的操作，提高了查找速度。同时你会发现就一个.dex文件，是比较大的。

## 关于多进程保活 ##
在这一点上，我是极力反对通过多进程来进行所谓的保活的，保活应该是让用户建立对App的依赖，从而来提高留存，而不是通过所谓的后台唤起，这种只会让数据好看的方式。很多比较智障的产品经理，动不动就说，微信、QQ都可以保活呀，对于这种产品，我只想说，你TM能把App做到微信、QQ这种程度，我也能让你活！这些大厂的App的保活，基本上都是靠ROM的白名单，如果ROM想Kill你的进程，那真是可以让你有一万种死法。而大部分的ROM厂商，为了降低自己手机的功耗和内存，基本上对于第三方的App都会采取直接Kill的方式来管理，所以，如果再有产品让你来给应用保活，请直接向它抛出一个异常。 --- 徐宜生  

##Android Context完全解析，你所不知道的Context的各种细节##
[传送门](http://blog.csdn.net/guolin_blog/article/details/47028975)  

1. ContextWrapper是包装类，里面调用的ContextImpl  
2. Context数量 = Activity数量 + Service数量 + 1  
3. getApplicationContext()和getApplication()的区别在于getApplication()只能在Activity、Service中获取，而getApplicaationContext支持的范围更广。  
4. Application的执行顺序是构造方法->attachBaseContext()->onCreate()方法，在attachBaseContext中会传入一个ContextImpl对象，在这之前获取context将导致程序崩溃  
5. Application本身就是单例，无需再写单例。Application是应系统调用的，系统调用时会进行一系列初始化操作，而如果我们将其单例化，自己new出的Application其实并没有上下文功能，从而会导致空指针错误。  

##Assets目录下资源使用总结##
####android资源文件分类  
1. res目录下存放的可编译的资源文件，系统会自动生成资源文件ID(R.id.XXX)  
2. assets目录下存放的原生资源文件  

系统在编译的时候不会编译assets下的资源文件，所以我们不能通过R.XXX.ID端口方式访问他们。  
因为apk安装之后会放到/data/data/**.apk目录下，以apk形式存在，asset/res被绑定在apk里，并不会解压到/data/data/YourApp目录下，所以我们无法直接获取到assets的绝对路径，因为他们根本就没有。
Android系统为我们提供了一个AssetManager工具类。  

## 不要在Android的Application对象中缓存数据! ##
[传送门](http://zmywly8866.github.io/2014/12/26/android-do-not-store-data-in-the-application-object.html)  

在你的App中的很多地方都需要使用到数据信息，它可能是一个session token，一次费时计算的结果等等，通常为了避免Activity之间传递数据的开销，会将这些数据通过持久化来存储。  

有人建议将这些数据放在Application对象中方便所有的Activity访问，这个解决方案简单、优雅并且是……完全错误的。  
你如果你将数据缓存到Application对象中，那么有可能你的程序最终会由于一个NullPointerException异常而崩溃掉。  

## 为什么手机进入休眠后，还能收到电话、QQ的消息呢？ ##
[传送门](http://mp.weixin.qq.com/s?__biz=MzAxNzMxNzk5OQ==&mid=2649484680&idx=1&sn=bd9086a95b769af8d8644cf681ce66ec&scene=1&srcid=0830Zw5u5kq60HJ3HcW2oUq8#rd)  

在手机中，实际上有两个处理器，一个叫Application Processor，即AP处理器，一个叫Baseband Processor，即BP处理器。其中AP就是我们一般说的CPU，它通常是ARM架构，当然也有奇葩的x86架构，它用于运行我们的Android系统，在非睡眠情况下，AP的功耗非常高，特别是在绘图、计算、渲染等场景下。而BP则是另一个经常不为人知的处理器，他用于运行实时操作系统，手机最基本的通信协议栈就运行在BP的实时操作系统上，BP的功耗非常低，基本不会进入睡眠。  

