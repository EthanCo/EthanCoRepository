[Activity1]

// 打开Activity2
public void get(View v){
    	Intent intent = new Intent(this,Main2Activity.class);
    	//请求码  标示请求的来源
    	startActivityForResult(intent, 100);
    }

//重写onActivityResult
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(requestCode == 100){
    		if(data != null){
    			String name = data.getStringExtra("name");
    			et_name.setText(name);
    		}
    	}
    }
	
[Activity2]

//选择数据数据 点击返回
public class Main2Activity extends Activity {

	private ListView listview;
	private MyAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main2);
		
		listview = (ListView) findViewById(R.id.listview);
		
		adapter = new MyAdapter();
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new MyOnItemClickListener());
		
		
	}
	
	private final class MyOnItemClickListener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			
			String name = (String) adapter.getItem(position);
			
			intent.putExtra("name", name);
			//设置返回的数据   结果码：标示数据是从哪个activity返回的
			setResult(200, intent);
			
			finish();//关闭当前的activity
		}
		
	}
	
	private class MyAdapter extends BaseAdapter{
		
		private String[] names = new String[]{"周杰伦","蔡依林","干露露","李连杰","成龙"};

		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return names[position];
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			TextView tv = new TextView(getApplicationContext());
			tv.setText(names[position]);
			tv.setTextSize(25);
			tv.setTextColor(Color.GREEN);
			
			return tv;
		}
		
	}
}

[main1.xml]
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical" >

    <TextView
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="用户名" />
    
    <EditText android:id="@+id/et_name"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"/>
    
    <Button 
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="get"
        android:text="获取"/>

</LinearLayout>


[main2.xml]
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <ListView android:id="@+id/listview"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"/>

</LinearLayout>

