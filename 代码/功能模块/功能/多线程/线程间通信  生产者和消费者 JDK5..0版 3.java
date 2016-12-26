//会出现只唤醒本方线程的情况,导致程序中的所用线程都等待
//改为signalAll();问题解决,但和以前一样,还会唤醒本方
//JDK5.0中提供了多线程升级解决方案
//将同步synchronized替换成显示Lock操作
//将object中的wait,notify,notifyAll 替换成了Condition对象
//该对象可以Lock锁 可以定义多个condition
//在该示例中,实现了本方只唤醒对方的操作
//把notify封装成了Condition对象
import java.util.concurrent.locks.*; //需要导包

class ProducerConsumerDemo
{
	public static void main(String[] args)
	{
		System.out.println("Begin");
		Resource r = new Resource();
		
		Producer pro = new Producer(r);
		Consumer con = new Consumer(r);
		
		Thread t1 = new Thread(pro);
		Thread t2 = new Thread(pro);
		Thread t3 = new Thread(con);
		Thread t4 = new Thread(con);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}

class Resource
{
	private String name;
	private int count = 1;
	private boolean flag = false;
	
	private Lock lock = new ReentrantLock(); //定义锁
	private Condition condition_pro =lock.newCondition();
	private Condition condition_con =lock.newCondition(); //定义两个,分别用于生产者和消费者
	
	public void set(String name) throws InterruptedException
	{		
		/*在sdk5.0中 Lock提供了比 synchronized更广泛的锁定操作,支持Condition对象
		Lock() 获得锁 unLock 释放锁
		Condition 接口 将object监视器方法(wait,notify,notifyAll) 分解成截然不同的对象,以便通过将这些对象与任意Lock实现组合使用
		await() signal();  signalAll();
		*/
		lock.lock(); //拿到锁
		try
		{
			while(flag)
				condition_pro.await(); //生产者等待

			this.name = name+"----"+count++;
			System.out.println(Thread.currentThread().getName()+"....生产者..."+this.name);
			flag = true;
			condition_con.signalAll(); //唤醒消费者 等待线程
		}
		finally
		{
			lock.unlock();//释放锁 [一定要执行]
		}
	}
	
	public void out() throws InterruptedException
	{
		try
		{
			lock.lock(); //拿到锁
			while(!flag)
				condition_con.await(); //消费者等待
			System.out.println(Thread.currentThread().getName()+"....消费者......."+this.name);
			flag = false;
			condition_pro.signalAll();//唤醒生产者 等待线程
		}
		finally
		{
			lock.unlock(); //释放锁 [一定要执行]
		}
	}
}

//生产者
class Producer implements Runnable
{
	private Resource res;
	
	Producer(Resource res)
	{
		this.res = res;
	}
	public void run()
	{
		while(true)
		{
			try
			{
				res.set("+商品+");
			}
			catch(InterruptedException e)
			{
				//
			}
		}
	}
}

//消费者
class Consumer implements Runnable
{
	private Resource res;
	
	Consumer(Resource res)
	{
		this.res = res;
	}
	public void run()
	{
		while(true)
		{
		try
			{
				res.out();
			}
			catch(InterruptedException e)
			{
				//
			}
		}
	}
}