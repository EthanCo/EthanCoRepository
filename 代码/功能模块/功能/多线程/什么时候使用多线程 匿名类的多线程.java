ʲôʱ���ö��̣߳�

��ĳЩ������Ҫͬʱ��ִ��ʱ,��Զ���,���õ������߳̽��з�װ
����һ. ��װ����
Class Test1 extends Thread
{
	public void run()
	{
		//����
	}
}
new Test1().start();


������
new Thread()
{
	public void run()
	{
		//����
	}
}.start();

������
Runnable r = new Runnable()
{
	public void run()
	{
		//����
	}
};
new Thread(r).start();