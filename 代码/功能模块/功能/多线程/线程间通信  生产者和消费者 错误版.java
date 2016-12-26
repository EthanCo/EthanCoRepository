/*
对于多个生产者和消费者
要用while判断标记
原因：让被唤醒的线程再做一次判断标记

用notifyAll
因为需要唤醒对方线程
因为只用notify,容易出现只唤醒本方线程的情况,导致程序中的所用线程都等待
*/

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
	
	public synchronized void set(String name)
	{
		if(flag)//这个当t2等待 , t3 t4都锁住时,
		//然后t1执行完也锁住时,就会唤醒t2,然后就会生产两次,所以要用notifyAll解决
			try{this.wait();}catch(Exception e){}
		this.name = name+"----"+count++;
		
		System.out.println(Thread.currentThread().getName()+"....生产者..."+this.name);
		flag = true;
		this.notify();
	}
	
	public synchronized void out()
	{
		if(!flag)
			try{this.wait();}catch(Exception e){}
		System.out.println(Thread.currentThread().getName()+"....消费者......."+this.name);
		flag = false;
		this.notify();
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
			res.set("+商品+");
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
			res.out();
		}
	}
}