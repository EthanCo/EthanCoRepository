//写入
		SharedPreferences sp =getSharedPreferences("文件名", MODE_PRIVATE); 	//获取对象，默认在当前程序所在文件夹
		// sp =  getSharedPreferences(MODE_PRIVATE)							//不设文件名，默认起MainActivity.xml
		Editor editor = sp.edit();											//获得编辑器
		editor.putString("name",name);										//存储数据
		editor.putString("phoneNum", phoneNum);
		editor.putString("email", email);
		editor.commit();													//提交修改(保存文件)
		
//读取
		SharedPreferences sp =getSharedPreferences("文件名", MODE_PRIVATE); 
		nameET.setText(sp.getString("name", ""));
		phoneNumET.setText(sp.getString("phoneNum", ""));
		emailET.setText(sp.getString("email", ""));