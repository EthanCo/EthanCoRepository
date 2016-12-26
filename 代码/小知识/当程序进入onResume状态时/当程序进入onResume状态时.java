	protected void onResume(){
		super.onResume();
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			findViewById(R.id.sdBT).setEnabled(true);
		}else{
			findViewById(R.id.sdBT).setEnabled(false);
		}
	}