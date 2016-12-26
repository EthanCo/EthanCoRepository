/*
stop方法已经过时

如何停止线程
只有一种,run方法结束
开启多线程运行,运行代码通常是循环结构

只要控制循环,就可以让run方法结束,也就是线程结束
*/

class StopThread implements Runnable
{
	private boolean flag =true;
	public void run()
	{
		while(flag)
		{
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
				st.chengeFlag();
				break;
			}
			System.out.println(Thread.currentThread().getName()+"......"+num);
		}
	}
}