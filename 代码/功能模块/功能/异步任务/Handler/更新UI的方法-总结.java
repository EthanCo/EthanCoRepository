在子线程中

/*
会判断当前的线程是否是UI线程,如果不是UI线程,就会调用handler.post()
*/
Activity的runOnUiThread
	runOnUiThread(new Runnable(){
		public void run(){
			
		}
	})
	
//其实是调用了sendMessageDelayed方法
Handler.post
	handler.post(new Runnable(){
		public void run(){
			
		}
	})
	
//
Handler.sendMessage
	Message msg = new Message();
	msg.what=;
	msg.obj=;
	handler.sendMessage(msg);
	
//会判断Runnable是否为空,如果不为空,则调用handler.post(),若为空,则ViewRootImpl.getRunQueue().post()
view.post
	textVIew.post(new Runnable(){
		public void run(){
			
		}
	})
	
	
说到底还是用handler的方法来更新UI