/*
stop方法已经过时

如何停止线程
只有一种,run方法结束
开启多线程运行,运行代码通常是循环结构

只要控制循环,就可以让run方法结束,也就是线程结束

特殊情况
当线程处于冻结(等待)状态
就不会读取到标记,那么线程就不会结束
用interrupt(强制停止冻结状态,恢复到运行状态来) --sleep,join也可以用
如果线程在调用object类的wait(),wait(long),wait(long,int)方法,
或者该类的join(),join(long),join(long,int),sleep(long),sleep(long,int)
方法过程中受阻,则其中断(冻结)状态将被清除,它还将收到一个InterrupteException

当没有指定的方式让冻结的线程恢复到运行状态时,这时需要对冻结进行清除
强制让线程恢复到运行状态中来,这样就可以操作标记让线程结束
Thread类提供了该方法
*/

class StopThread implements Runnable
{
	private boolean flag =true;
	public synchronized void run()
	{
		while(flag)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				System.out.println(Thread.currentThread().getName()+"......exception");
				flag=false;//结束
			}
			System.out.println(Thread.currentThread().getName()+"......run");			
		}
	}
	public void chengeFlag()
	{
		flag=false;
	}
	
}

class StopThreadDemo
{
	public static void main(String[] args)
	{
		StopThread st = new StopThread();
		
		Thread t1 = new Thread(st);
		Thread t2 = new Thread(st);
		
		t1.start();
		t2.start();
		
		int num =0;
		
		while(true)
		{
			if(num++==60)
			{
				//st.chengeFlag();
				t1.interrupt();
				t2.interrupt();
				break;
			}
			System.out.println(Thread.currentThread().getName()+"......"+num);
		}
		System.out.println("over");
	}
}