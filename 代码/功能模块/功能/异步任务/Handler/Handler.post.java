一般来说在工作线程中执行耗时任务，当任务完成时，会返回UI线程，一般是更新UI。这时有两种方法可以达到目的。
一种是handler.sendMessage。发一个消息，再根据消息，执行相关任务代码。
另一种是handler.post(r)。r是要执行的任务代码。意思就是说r的代码实际是在UI线程执行的。可以写更新UI的代码。（工作线程是不能更新UI的）

建议最好用下面的方法：  定义一个线程。 
 class MyThread extends Thread{
	 Handler   mHandler;
	 Boolean  boo;
	 public MyThread(Handler handler){
		 mHandler = handler;         
		 }         
	 public void setBoo(boolean b) {boo = b; }
     publid void run(){
		 if(boo){
			 getWeatherInfo();//耗时操作	 
			 analyzing();//耗时操作
			 mHandler.post(new Runnable() {
				 public void run() {
					 setWeather();//更新UI
					 }
					 )
					 boo = true;              
					 }       
					 }}
	//在处理单击事件时：
	sureButton.setOnClickListener(new Button.OnClickListener(){
		public void onClick(View view){
			setBoo(true);
			myThread.start();
			}
			});
	在activity中：
	MyThread myThread = new MyThread(mHandler);