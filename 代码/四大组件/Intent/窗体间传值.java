[Activity1]
		String Id = userId.getText().toString();
		String pwd = userPsw.getText().toString();
		Intent intent = new Intent();
		intent.setAction("zzz");
		// intent.putExtra("userId", Id);
		// intent.putExtra("pwd", pwd);
		Bundle bundle = new Bundle();// �����ݽ��з�װ
		bundle.putString("userId", Id);
		bundle.putString("pwd", pwd);
		
		intent.putExtras(bundle);
		startActivity(intent);
[Activity2]
		Intent intent = getIntent();
		// String Id = intent.getStringExtra("userId");
		// String Pwd = intent.getStringExtra("pwd");
		Bundle bundle = intent.getExtras();
		String Id = bundle.getString("userId");
		String Pwd = bundle.getString("pwd");
		
		resultTV.setText("�˺ţ�" + Id + "���룺" + Pwd);


        <activity
            android:name=".Main2Activity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="zzz"/>
                <category android:name="android.intent.category.DEFAULT"></category>
            </intent-filter>
        </activity>