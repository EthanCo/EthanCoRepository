	
	//ͨ���ڳ����ڲ�ʹ��

		// Intent ��ͼ ���ã��������
		Intent intent = new Intent();
		// ָ��Ҫ�������� (��ʽ��ͼ�����ȷָ��Ҫ��������)
		// intent.setClass(this, Main2Activity.class); //����һ
		// intent.setClassName(this, "cn.zhk.Multiactivity.Main2Activity");//������
		// intent.setClassName(getPackageName(),"cn.zhk.Multiactivity.Main2Activity");//������
		// intent.setComponent(new ComponentName(this, Main2Activity.class));//������

		// ����Activity
		startActivity(intent);

��
Intent intent = new Intent(this, Main2Activity.class); //������
		startActivity(intent);