//д��rom
		public void SaveToRom(String name,String content) throws IOException{
			//context.MODE_PRIVATE			 ˽�еģ�ֻ�б������ܶ������������ܶ�Ҳ����д
			//context.MODE_WORLD_READABLE	 �����Զ�
			//context.MODE_WORLD_WRITEABLE   ������д 
			//context.MODE_WORLD_READABLE+context.MODE_WORLD_READABLE		�ܶ���д
			FileOutputStream fos = context.openFileOutput(name,context.MODE_PRIVATE); 
			fos.write(content.getBytes());
			fos.close();
		}