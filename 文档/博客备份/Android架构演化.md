# Android架构演化 #
## MVC ##
![MVC](http://i1.piimg.com/567571/59afead9a8680a68.png)  

模型(Model)——视图(View)——控制器(Controller)

View触发事件,并通知Controller执行业务逻辑。  
Controller执行完业务逻辑后，通知Model更新数据。  
Model完成数据更新后，通知View更新数据。  

在传统的开发中Activity既充当了Controller又充当了View的作用。既需要接受用户响应操作Model,又要更新界面，十足的上帝类。  
这么做的好处就是数据的更新变得很简单，适合于快速开发。但是缺点也十分明显,Activity非常臃肿,后期维护困难。  

在实际运用中人们发现View和Model之间的依赖还是太强，希望他们可以绝对独立的存在，故逐渐的就演化出了MVP。

> MVC允许View和Model直接进行交互

## MVP ##
![MVP](http://i1.piimg.com/567571/2c1ad41eaf789126.png)  

MVP不允许View和Model直接交互，Presenter替换了Controller。  
通过Presenter将Model和View隔离开，并协调Model和View的协同工作，减少Model和View的依赖关系。  
Presenter不仅仅处理逻辑部分，还控制着View的刷新，监听Model层的数据变化。  

这样隔离掉View和Model的关系后使得View层变的非常的薄，没有任何逻辑部分，不主动监听数据。  

###优点  
 
**便于维护**  
View和Model之间的耦合度降低，结构清晰，维护方便。  
**可复用率提高**  
数据、逻辑和视图进行了分层，很容易进行重用  
**协同开发**    
可以共同开发，数据、逻辑和视图由不同开发者并行进行开发  
**局部开发**  
在已有数据接口，而设计稿没有完成的情况，可以先行开发Model层。  
在已有设计稿，而数据接口仍未确定的情况下，可以先行开发view层。    
**方便单元测试**    
分层明确，对单元测试支持良好。  
由于逻辑在Presenter中，可以不通过视图即可测试逻辑。  

###缺点  
接口和类增多  

## MVVM ##
![MVVM](http://i1.piimg.com/567571/adba97ef8f84be29.png)

MVVM模式基于MVP演化而来，最先使用在WPF中。  
通过Data Binding，实现View和ViewModel的双向绑定。使View的变化可以自动反应在ViewModel上，ViewModel的数据变化也会自动反应到View上。   

View的事件直接传递到ViewModel，ViewModel去对Model进行操作并接受更新，进而反馈到View上。  

### 优点 ###
拥有MVP的大部分的优点  
同时，相对MVP编写的类和接口有所减少  
数据的双向绑定使得UI的变化非常容易，UI的数据化也变得非常便捷   
### 缺点 ###
**数据的双向绑定使得 Bug 很难被调试**  
你看到界面异常了，有可能是你 View 的代码有 Bug，也可能是 Model 的代码有问题。数据绑定使得一个位置的 Bug 被快速传递到别的位置，要定位原始出问题的地方就变得不那么容易。  
**对于过大的项目，数据绑定需要花费更多的内存**  
**View和ViewModel的耦合较高**  
View和ViewModel的耦合较高，存在View的复用问题，因为去掉了Presenter，View层依旧过重。    

## MVPVM ##
![MVPVM](http://upload-images.jianshu.io/upload_images/1557345-a98bb1bd450061ea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  

MVPVM是MVP和MVVM的结合演化版本,降低了ViewModel与View的耦合,View只需要实现ViewModel的观察者接口（如Listener）实现更新.ViewModel不再对Model直接进行操作,而是交给了Presenter.Presenter操作Model并反馈到ViewModel上。

## 参考 ##
[Android MVP开发模式的结构及优缺点详解](http://www.maiziedu.com/article/8549/)  
[Android架构MVC,MVP与MVVM及MVPVM对比分析](http://mp.weixin.qq.com/s?__biz=MzI2OTQxMTM4OQ==&mid=2247484130&idx=2&sn=8a5010cbbffa3a677fb7ca86d0999988&chksm=eae1f7b0dd967ea61cfffc3820e82574518db6ae3be7c08768bd6ad2cf43c74b90587ad98f5b&mpshare=1&scene=23&srcid=1101NJ9sLH6w9dzdqk5SdSUd#rd)  
[从零开始的Android新项目3 - MVPVM in Action, 谁告诉你MVP和MVVM是互斥的](http://www.jianshu.com/p/7d59f3642b89)