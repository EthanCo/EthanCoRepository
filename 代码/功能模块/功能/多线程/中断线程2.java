/*
stop�����Ѿ���ʱ

���ֹͣ�߳�
ֻ��һ��,run��������
�������߳�����,���д���ͨ����ѭ���ṹ

ֻҪ����ѭ��,�Ϳ�����run��������,Ҳ�����߳̽���

�������
���̴߳��ڶ���(�ȴ�)״̬
�Ͳ����ȡ�����,��ô�߳̾Ͳ������
��interrupt(ǿ��ֹͣ����״̬,�ָ�������״̬��) --sleep,joinҲ������
����߳��ڵ���object���wait(),wait(long),wait(long,int)����,
���߸����join(),join(long),join(long,int),sleep(long),sleep(long,int)
��������������,�����ж�(����)״̬�������,�������յ�һ��InterrupteException

��û��ָ���ķ�ʽ�ö�����ָ̻߳�������״̬ʱ,��ʱ��Ҫ�Զ���������
ǿ�����ָ̻߳�������״̬����,�����Ϳ��Բ���������߳̽���
Thread���ṩ�˸÷���
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
				flag=false;//����
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