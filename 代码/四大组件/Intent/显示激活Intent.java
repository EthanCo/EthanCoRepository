	
	//通常在程序内部使用

		// Intent 意图 作用：激活组件
		Intent intent = new Intent();
		// 指定要激活的组件 (显式意图激活：明确指定要激活的组件)
		// intent.setClass(this, Main2Activity.class); //方法一
		// intent.setClassName(this, "cn.zhk.Multiactivity.Main2Activity");//方法二
		// intent.setClassName(getPackageName(),"cn.zhk.Multiactivity.Main2Activity");//方法三
		// intent.setComponent(new ComponentName(this, Main2Activity.class));//方法四

		// 激活Activity
		startActivity(intent);

或
Intent intent = new Intent(this, Main2Activity.class); //方法五
		startActivity(intent);