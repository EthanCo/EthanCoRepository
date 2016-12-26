# Android的线程和线程池 #
- AsyncTask
	- 封装了线程池和Handler，方便开发者在子线程中更新UI  
- HandlerThread
	- 一种具有消息循环的线程，在它的内部可以使用Handler
- IntentService
	- 内部采用HandlerThread来执行任务，当任务完毕后IntentService会自动退出  
	
## Android中的线程池 ##
- 重用线程池里的线程
	- 避免因为线程的创建和销毁所带来的性能开销。
- 能有效控制线程池的最大并发数
	- 避免大量的线程之间因互相抢占系统资源而导致的阻塞现象。
- 能够对线程进行简单的管理
	- 并提供定时执行以及指定间隔循环执行等功能

### ThreadPoolExecutor ###
是线程池的真正实现，它的构造方法提供了一系列参数来配置线程池

#### ThreadPoolExcutor的构造方法参数 ####

- corePoolSize
	- 线程池的核心线程数
	- 默认核心线程一直存活
	- 可以设置allowCoreThreadTimeOUt为true，则等待超过keepAliveTime后即终止
- maximumPoolSize
	- 线程池所容纳的最大线程数
		- 当活动线程数达到这个数值后，后续的新线程将会被阻塞
- keepAliveTime
	- 非核心线程闲置时的超时时长
	- 如果核心线程的allowCoreThreadTimeOUt为true，则keepAliveTime同样会作用于核心线程
- unit
	- 用于指定keepAliveTime参数的时间单位，这是一个枚举
		- TimeUnit.MILLISECONDS (毫秒)
		- TimeUnit.SECONDS (秒)
		- TimeUnit.MINUTES (分钟)
- workQueue
	- 线程池中的任务队列
		- 通过线程池的execute方法提交的Runnable对象会储存在这个参数中
- threadFactory
	- 线程工厂，为线程池提供创建新线程的功能
		- ThreadFactory是一个接口，它只有一个方法:Thread newThread(Runnable r);
