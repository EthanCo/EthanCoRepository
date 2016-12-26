//一般在跨应用的时候使用


//必须添加category
        <activity
            android:name=".Main3Activity"
            android:label="new Activity">
            <intent-filter>
               	<action android:name="zhkmain3"/>
               	<category android:name="android.intent.category.DEFAULT"/>//必须配置DEFAULT
				//可以指定多个category
            </intent-filter>
        </activity>

		Intent intent = new Intent();
		intent.setAction("zhkmain3");//Intent中的四个重要属性——Action、Data、Category、Extras 
		startActivity(intent); // 默认添加了类别category  (android.intent.category.DEFAULT)

//打开浏览器
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			intent.setData(Uri.parse("http://www.baidu.com"));
			startActivity(intent);