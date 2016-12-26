[Activity1]

// ��Activity2
public void get(View v){
    	Intent intent = new Intent(this,Main2Activity.class);
    	//������  ��ʾ�������Դ
    	startActivityForResult(intent, 100);
    }

//��дonActivityResult
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

//ѡ���������� �������
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
			//���÷��ص�����   ����룺��ʾ�����Ǵ��ĸ�activity���ص�
			setResult(200, intent);
			
			finish();//�رյ�ǰ��activity
		}
		
	}
	
	private class MyAdapter extends BaseAdapter{
		
		private String[] names = new String[]{"�ܽ���","������","��¶¶","������","����"};

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
        android:text="�û���" />
    
    <EditText android:id="@+id/et_name"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"/>
    
    <Button 
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="get"
        android:text="��ȡ"/>

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

