�쳣����try-catch
try{
	........			
	Toast.makeText(getApplicationContext(),"����ɹ�",Toast.LENGTH_SHORT).show();
}catch(Exception e){
	e.printStackTrace(); //����־���ӡ�쳣��Ϣ
	Toast.makeText(getApplicationContext(), "����ʧ��", Toast.LENGTH_SHORT).show();
}