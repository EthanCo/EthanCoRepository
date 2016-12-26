异常处理try-catch
try{
	........			
	Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
}catch(Exception e){
	e.printStackTrace(); //在日志里打印异常信息
	Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
}