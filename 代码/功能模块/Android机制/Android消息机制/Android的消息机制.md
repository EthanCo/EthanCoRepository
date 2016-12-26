# Android消息机制 #
## Android的消息机制分析 ##
### ThreadLocal的工作原理 ###


- 线程内部的数据存储类  
	- 通过它可以在指定的线程中存储数据，
	- 存储的数据，只有在指定的线程可以获取到

### 消息队列的工作原理 ###

MessageQueue主要包含两个操作，插入和读取  

插入:enqueueMessage 往消息队列中插入一条消息  
读取:next 从消息队列中取出一条消息并将其从消息队列中移除  

   
### Looper的工作原理 ###
消息循环  


- 不停的从MessageQueue中查看是否有新消息
	- 如果有新消息就立即处理
	- 否则就一直阻塞在那里


##### 在构造方法中会创建一个MessageQueue，然后将当前线程的对象保存起来
    private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }

##### 创建一个Looper
	new Thread(){
		run(){
			Looper.prepare();
			handler handler = new Handler();
			Looper.loop();
		}
	}

##### Looper也是可以退出的
quit:直接退出  
quitSafely:等所有消息都已经处理完毕后才安全退出  

Looper退出后，通过Handler发送的消息会失败，这个时候Handler的send方法会返回false  

在子线程中，如果手动为其创建了Looper，那么所有的事情完成后应该调用quit方法来终止消息循环
否则这个子线程就会一直处于等待状态，而如果推出Looper以后，这个线程就会立刻终止，因此，建议不需要的时候终止Looper

### Handler的工作原理 ###
负责消息的发送和接收  

发送消息的过程其实仅仅是向消息队列中插入了一条消息

	queue.enqueueMessage(msg,uptimeMillis);

MessageQueue的next方法会取到一条消息给Looper，Looper收到消息后就开始处理了，最终消息由Looper交给Handler处理，即Handler的dispatchMessage方法会被调用  
在dispatchMessage方法里  

	handleMessage(msg);

### 主线程的消息循环 ###
Activity的主线程就是ActivityThread，主线程的入口方法是main  
在main方法中系统会通过Looper.prepareMainLooper()来创建主线程的Looper以及MessageQueue，并通过Looper.loop()来开启主线程的消息循环。  

