toString()	线程的toString重写了 object类的toString() 返回 线程名称,优先级,线程组

线程组  一般情况下谁开启你,你就属于哪个组
		ThreadGroup 对象 可以设置线程组
		
优先级	setPrioruty(int newPriority); 设置线程的优先级
		默认优先级是5 范围(1-10)
		MAX_PRIORITY  10
		MIN_PRIORITY  1
		NORM-PRIORITY 5
		
yield()	暂停当前正在执行的线程对象,并执行其他线程
		可以稍微减缓线程的运行,达到线程都有机会平均运行的效果 