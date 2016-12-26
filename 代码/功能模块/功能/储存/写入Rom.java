//写入rom
		public void SaveToRom(String name,String content) throws IOException{
			//context.MODE_PRIVATE			 私有的，只有本程序能读，其他程序不能读也不能写
			//context.MODE_WORLD_READABLE	 都可以读
			//context.MODE_WORLD_WRITEABLE   都可以写 
			//context.MODE_WORLD_READABLE+context.MODE_WORLD_READABLE		能读能写
			FileOutputStream fos = context.openFileOutput(name,context.MODE_PRIVATE); 
			fos.write(content.getBytes());
			fos.close();
		}