�����߳���

/*
���жϵ�ǰ���߳��Ƿ���UI�߳�,�������UI�߳�,�ͻ����handler.post()
*/
Activity��runOnUiThread
	runOnUiThread(new Runnable(){
		public void run(){
			
		}
	})
	
//��ʵ�ǵ�����sendMessageDelayed����
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
	
//���ж�Runnable�Ƿ�Ϊ��,�����Ϊ��,�����handler.post(),��Ϊ��,��ViewRootImpl.getRunQueue().post()
view.post
	textVIew.post(new Runnable(){
		public void run(){
			
		}
	})
	
	
˵���׻�����handler�ķ���������UI