/*
stop�����Ѿ���ʱ

���ֹͣ�߳�
ֻ��һ��,run��������
�������߳�����,���д���ͨ����ѭ���ṹ

ֻҪ����ѭ��,�Ϳ�����run��������,Ҳ�����߳̽���
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