//д��
		SharedPreferences sp =getSharedPreferences("�ļ���", MODE_PRIVATE); 	//��ȡ����Ĭ���ڵ�ǰ���������ļ���
		// sp =  getSharedPreferences(MODE_PRIVATE)							//�����ļ�����Ĭ����MainActivity.xml
		Editor editor = sp.edit();											//��ñ༭��
		editor.putString("name",name);										//�洢����
		editor.putString("phoneNum", phoneNum);
		editor.putString("email", email);
		editor.commit();													//�ύ�޸�(�����ļ�)
		
//��ȡ
		SharedPreferences sp =getSharedPreferences("�ļ���", MODE_PRIVATE); 
		nameET.setText(sp.getString("name", ""));
		phoneNumET.setText(sp.getString("phoneNum", ""));
		emailET.setText(sp.getString("email", ""));