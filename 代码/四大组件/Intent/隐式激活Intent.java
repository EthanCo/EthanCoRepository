//һ���ڿ�Ӧ�õ�ʱ��ʹ��


//�������category
        <activity
            android:name=".Main3Activity"
            android:label="new Activity">
            <intent-filter>
               	<action android:name="zhkmain3"/>
               	<category android:name="android.intent.category.DEFAULT"/>//��������DEFAULT
				//����ָ�����category
            </intent-filter>
        </activity>

		Intent intent = new Intent();
		intent.setAction("zhkmain3");//Intent�е��ĸ���Ҫ���ԡ���Action��Data��Category��Extras 
		startActivity(intent); // Ĭ����������category  (android.intent.category.DEFAULT)

//�������
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			intent.setData(Uri.parse("http://www.baidu.com"));
			startActivity(intent);