# MVVM #
MVVM是MVP模式发展演变过来的一种新型框架。

每一个同事角色(View)都知道中介者角色(ViewModel)，而且与其他的同事角色(View)通信时，一定要通过中介者角色(ViewModel)协调  

## MVC、MVP、MVVM的区别 ##
**MVC**  

他们的通信方式也如上图所示，即View层触发操作通知到业务层完成逻辑处理，业务层完成业务逻辑之后通知Model层更新数据，数据更新完之后通知View层展现。在实际运用中人们发现View和Model之间的依赖还是太强，希望他们可以绝对独立的存在，慢慢的就演化出了MVP。

**MVP**

Presenter 替换掉了Controller，不仅仅处理逻辑部分。而且还控制着View的刷新，监听Model层的数据变化。这样隔离掉View和Model的关系后使得View层变的非常的薄，没有任何的逻辑部分又不用主动监听数据，被称之为“被动视图”。

**MVVM**

至于MVVM基本上和MVP一模一样，感觉只是名字替换了一下。他的关键技术就是今天的主题(Data Binding)。View的变化可以自动的反应在ViewModel，ViewModel的数据变化也会自动反应到View上。就样开发者不用就处理接收事件和View更新的工作，框架就帮你做好了。

## MVVM的优点 ##
###低耦合
视图（View）可以独立于Model变化和修改，一个ViewModel可以绑定到不同的"View"上，当View变化的时候Model可以不变，当Model变化的时候View也可以不变。
###可重用性
你可以把一些视图逻辑放在一个ViewModel里面，让很多view重用这段视图逻辑。
###独立开发(共同开发)
开发人员可以专注于业务逻辑和数据的开发（ViewModel），设计人员可以专注于页面设计。
###可测试
界面素来是比较难于测试的，而现在测试可以针对ViewModel来写。

###使Activity代码更加简洁

在传统的项目中 Activity 兼顾着 Controller 和 View，这使得其代码分分钟上千行（本人深受其害），这使得代码难以理解难以维护，看到这样的 Activity 就想吐。

使用 MVP 之后，Activity 就能瘦身许多了，基本上只有 FindView、SetListener 以及 Init 的代码。其他的就是对 Presenter 的调用，还有对 View接口 的实现。这种情形下阅读代码就容易多了。

而且你只要看 Presenter 的接口，就能明白这个模块都有哪些业务，很快就能定位到具体代码。Activity 变得容易看懂，容易维护，以后要调整业务、删减功能也就变得简单许多。



## 如何选择 ##
根据项目的需求来进行选择。  

MVC 构建一个项目会较为迅速，适合于要快速完成的项目。 而MVP/MVVM需要生产很多的接口，开发速度就相对慢了，而且会导致接口的膨胀，维护接口成本较高。   
但是MVC不便于单元测试，对于后期的维护和迭代来说，没有MVP/MVVM来的优秀。
同时，MVP/MVVM 方便单元测试，耦合更低，复用性高，不会造成Activity代码过多的情况，能够更好地维护。  

## 传送门 ##
[MVP模式解析实践](http://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650236921&idx=1&sn=4b2826b600a26b1cd3349ac91593b361&scene=1&srcid=0919S7UcqWNHoQFVwDqGE6je#rd)
  

