/*
	setDaemon 设置后台线程
	当前台线程全部结束时,后台线程随之结束
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

class setDaemon
{
	public static void main(String[] args)
	{
		StopThread st = new StopThread();
		
		Thread t1 = new Thread(st);
		Thread t2 = new Thread(st);
		
		t1.setDaemon(true);
		t1.setdaemon(true);
		
		t1.start();
		t2.start();
		
		int num =0;
		
		while(true)
		{
			if(num++==60)
			{
				break;
			}
			System.out.println(Thread.currentThread().getName()+"......"+num);
		}
		System.out.println("over");
	}
}