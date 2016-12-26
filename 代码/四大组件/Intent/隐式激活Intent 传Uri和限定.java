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
			intent.setDataAndType(Uri.parse("ziyiding:xxxxxxxxx"), "video/mpeg"); // miniType不能随便指定 可以在Tomocat中conf中web.xml中找
			// intent.setData(Uri.parse("ziyiding:")); intent.setType("video/mpeg");会清空data 所有用上面的方法
			startActivity(intent); // 默认添加了类别category
									// (android.intent.category.DEFAULT)