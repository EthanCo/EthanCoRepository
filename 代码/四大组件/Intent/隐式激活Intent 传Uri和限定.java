        <activity
            android:name=".Main3Activity"
            android:label="new Activity">
            <intent-filter>
               	<action android:name="zhkmain3"/>
               	<category android:name="android.intent.category.DEFAULT"/>
               	<data android:scheme="ziyiding" android:mimeType="video/mpeg"/>
            </intent-filter>
        </activity>
		
		
		
			Intent intent = new Intent();
			intent.setAction("zhkmain3");
			intent.setDataAndType(Uri.parse("ziyiding:xxxxxxxxx"), "video/mpeg"); // miniType�������ָ�� ������Tomocat��conf��web.xml����
			// intent.setData(Uri.parse("ziyiding:")); intent.setType("video/mpeg");�����data ����������ķ���
			startActivity(intent); // Ĭ����������category
									// (android.intent.category.DEFAULT)