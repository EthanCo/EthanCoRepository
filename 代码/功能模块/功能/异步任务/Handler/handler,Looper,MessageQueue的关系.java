Handler封装了消息的发送(主要包括消息发送给谁)
Looper 消息的载体
		内部有MessageQuery队列,所有Handler发送的消息都走向这个消息队列,可以添加消息,处理消息
	Looper.Looper 循环自己(就是一个死循环),不断地从MessageQueue获取消息,如有消息就处理消息,没有消息就阻塞
	
Handler也很简单,内部会跟Looper进行关联,也就是说在Handler的内部可以找到Looper,找到了Looper也就找到了MessageQueue,
在Handler中发消息,其实就是向MessageQueue队列中发送消息

总结：handler负责发送消息,Looper负责接收Handler发送的消息,并直接把消息回传给handler自己
	  MessageQueue 就是一个存储消息的容器
	  
	  //默认情况下,应用程序通过ActivityThread创建 其中->默认创建main线程 其中->默认创建Looper 其中->有messagequeue
	  
Handler一定要在主线程实例化吗?new Handler()和new Handler(Looper.getMainLooper())的区别
如果你不带参数的实例化：Handler handler = new Handler();那么这个会默认用当前线程的looper
一般而言，如果你的Handler是要来刷新操作UI的，那么就需要在主线程下跑。
情况:
1.要刷新UI，handler要用到主线程的looper。那么在主线程 Handler handler = new Handler();，如果在其他线程，也要满足这个功能的话，要Handler handler = new Handler(Looper.getMainLooper());
2.不用刷新ui,只是处理消息。 当前线程如果是主线程的话，Handler handler = new Handler();不是主线程的话，Looper.prepare(); Handler handler = new Handler();Looper.loop();或者Handler handler = new Handler(Looper.getMainLooper());
若是实例化的时候用Looper.getMainLooper()就表示放到主UI线程去处理。
如果不是的话，因为只有UI线程默认Loop.prepare();Loop.loop();过，其他线程需要手动调用这两个，否则会报错。

