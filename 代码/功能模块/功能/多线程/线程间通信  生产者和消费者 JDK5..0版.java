//会出现只唤醒本方线程的情况,导致程序中的所用线程都等待
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
	private Condition condition =lock.newCondition();
	
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
				condition.await(); //等待

			this.name = name+"----"+count++;
			System.out.println(Thread.currentThread().getName()+"....生产者..."+this.name);
			flag = true;
			condition.signal(); //唤醒等待线程
		}
		finally
		{
			lock.unlock();//释放锁
		}
	}
	
	public void out() throws InterruptedException
	{
		try
		{
			lock.lock(); //拿到锁
			while(!flag)
				condition.await(); //等待
			System.out.println(Thread.currentThread().getName()+"....消费者......."+this.name);
			flag = false;
			condition.signal();//唤醒等待线程
		}
		finally
		{
			lock.unlock(); //释放锁
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