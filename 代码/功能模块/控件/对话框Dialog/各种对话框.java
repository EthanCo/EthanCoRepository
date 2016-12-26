public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
	//普通对话框
    public void commondialog(View v){
    	//1 创建构建器
    	//2 给构建器设置属性  标题 内容  按钮  设置布局
    	//3 创建dialog
    	//4 显示对话框
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(false);//屏蔽了回退键
    	builder.setTitle("java培训");
    	builder.setMessage("浏览传智播客的网站");
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.itcast.cn"));
				startActivity(intent);
			}
		});
    	builder.setNeutralButton("隐藏", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
    //选择对话框
    public void selectdialog(View v){
    	final String[] items = new String[]{"java","C#","php"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("选择语言");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "选择的是:" + items[which], 1).show();
			}
		});//设置选项
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
	//单选对话框
    public void singleselectdialog(View v){
    	final String[] items = new String[]{"java","C#","php"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("选择语言");
    	builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "选择的是:" + items[which], 1).show();
			}
		});
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
	//多选对话框
    public void multiselectdialog(View v){
    	final String[] items = new String[]{"java","C#","php"};
    	final boolean[] checkedItems = new boolean[]{true,false,false};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("选择语言");
    	builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
			
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "选择的是:" + items[which] + "   " + isChecked, 0).show();
			}
		});
    	builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				for(int i = 0;i< items.length;i++){
					sb.append(items[i] + ":" + checkedItems[i]);
				}
				Toast.makeText(getApplicationContext(), sb.toString(), 1).show();
			}
		});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
	//自定义对话框 需要自定义的xml
    public void customdialog(View v){
    	LayoutInflater mInflater = LayoutInflater.from(this);
    	View view = mInflater.inflate(R.layout.custom_dialog, null);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("输入密码");
    	builder.setView(view);
    	final AlertDialog dialog = builder.create();
    	dialog.show();
    	
    	Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
    	Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
    	
    	bt_ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();//让对话框消失
			}
		});
    	bt_cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();//让对话框消失
			}
		});
    }
}