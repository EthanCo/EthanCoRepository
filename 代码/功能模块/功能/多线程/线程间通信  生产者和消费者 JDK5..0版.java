//�����ֻ���ѱ����̵߳����,���³����е������̶߳��ȴ�
import java.util.concurrent.locks.*; //��Ҫ����

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
	
	private Lock lock = new ReentrantLock(); //������
	private Condition condition =lock.newCondition();
	
	public void set(String name) throws InterruptedException
	{		
		/*��sdk5.0�� Lock�ṩ�˱� synchronized���㷺����������,֧��Condition����
		Lock() ����� unLock �ͷ���
		Condition �ӿ� ��object����������(wait,notify,notifyAll) �ֽ�ɽ�Ȼ��ͬ�Ķ���,�Ա�ͨ������Щ����������Lockʵ�����ʹ��
		await() signal();  signalAll();
		*/
		lock.lock(); //�õ���
		try
		{
			while(flag)
				condition.await(); //�ȴ�

			this.name = name+"----"+count++;
			System.out.println(Thread.currentThread().getName()+"....������..."+this.name);
			flag = true;
			condition.signal(); //���ѵȴ��߳�
		}
		finally
		{
			lock.unlock();//�ͷ���
		}
	}
	
	public void out() throws InterruptedException
	{
		try
		{
			lock.lock(); //�õ���
			while(!flag)
				condition.await(); //�ȴ�
			System.out.println(Thread.currentThread().getName()+"....������......."+this.name);
			flag = false;
			condition.signal();//���ѵȴ��߳�
		}
		finally
		{
			lock.unlock(); //�ͷ���
		}
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
			try
			{
				res.set("+��Ʒ+");
			}
			catch(InterruptedException e)
			{
				//
			}
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