//兼容所有android版本
			File file=new File(Environment.getExternalStorageDirectory(),name);//获取外存储设备路径
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));{ //检查储存卡是否存在
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(content.getBytes());
				fos.close();
			}

//Jdk7可用
			try(FileOutputStream fos = new FileOutputStream("/mnt/sdcard/"+name);){
				fos.write(content.getBytes());
			}
			
//当进入前台运行时检查储存卡是否存在
			protected void onResume(){
				super.onResume();
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					findViewById(R.id.sdBT).setEnabled(true);
				}else{
					findViewById(R.id.sdBT).setEnabled(false);
				}
			}