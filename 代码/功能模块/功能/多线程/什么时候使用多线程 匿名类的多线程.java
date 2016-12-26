什么时候用多线程？

当某些代码需要同时被执行时,相对独立,就用单独的线程进行封装
方法一. 封装成类
Class Test1 extends Thread
{
	public void run()
	{
		//代码
	}
}
new Test1().start();


方法二
new Thread()
{
	public void run()
	{
		//代码
	}
}.start();

方法三
Runnable r = new Runnable()
{
	public void run()
	{
		//代码
	}
};
new Thread(r).start();