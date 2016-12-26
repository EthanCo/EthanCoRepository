//ActionBar的折叠按钮的实现
protected void getOverflowMenu() {         
	try {
	   ViewConfiguration config = ViewConfiguration.get(this);
	   Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	   if(menuKeyField != null) {
		   menuKeyField.setAccessible(true);
		   menuKeyField.setBoolean(config, false);
	   }
   } catch (Exception e) {
	   e.printStackTrace();
   }
}