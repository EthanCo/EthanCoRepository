/*
���ڶ�������ߺ�������
Ҫ��while�жϱ��
ԭ���ñ����ѵ��߳�����һ���жϱ��

��notifyAll
��Ϊ��Ҫ���ѶԷ��߳�
��Ϊֻ��notify,���׳���ֻ���ѱ����̵߳����,���³����е������̶߳��ȴ�
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
		if(flag)//�����t2�ȴ� , t3 t4����סʱ,
		//Ȼ��t1ִ����Ҳ��סʱ,�ͻỽ��t2,Ȼ��ͻ���������,����Ҫ��notifyAll���
			try{this.wait();}catch(Exception e){}
		this.name = name+"----"+count++;
		
		System.out.println(Thread.currentThread().getName()+"....������..."+this.name);
		flag = true;
		this.notify();
	}
	
	public synchronized void out()
	{
		if(!flag)
			try{this.wait();}catch(Exception e){}
		System.out.println(Thread.currentThread().getName()+"....������......."+this.name);
		flag = false;
		this.notify();
	}
}

//������
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
			res.set("+��Ʒ+");
		}
	}
}

//������
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