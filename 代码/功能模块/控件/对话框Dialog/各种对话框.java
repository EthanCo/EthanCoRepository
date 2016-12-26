public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
	//��ͨ�Ի���
    public void commondialog(View v){
    	//1 ����������
    	//2 ����������������  ���� ����  ��ť  ���ò���
    	//3 ����dialog
    	//4 ��ʾ�Ի���
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(false);//�����˻��˼�
    	builder.setTitle("java��ѵ");
    	builder.setMessage("������ǲ��͵���վ");
    	builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.itcast.cn"));
				startActivity(intent);
			}
		});
    	builder.setNeutralButton("����", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
    //ѡ��Ի���
    public void selectdialog(View v){
    	final String[] items = new String[]{"java","C#","php"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("ѡ������");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "ѡ�����:" + items[which], 1).show();
			}
		});//����ѡ��
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
	//��ѡ�Ի���
    public void singleselectdialog(View v){
    	final String[] items = new String[]{"java","C#","php"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("ѡ������");
    	builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "ѡ�����:" + items[which], 1).show();
			}
		});
    	builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	AlertDialog dialog = builder.create();
    	dialog.show();
    }
    
	//��ѡ�Ի���
    public void multiselectdialog(View v){
    	final String[] items = new String[]{"java","C#","php"};
    	final boolean[] checkedItems = new boolean[]{true,false,false};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("ѡ������");
    	builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
			
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "ѡ�����:" + items[which] + "   " + isChecked, 0).show();
			}
		});
    	builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
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
    
	//�Զ���Ի��� ��Ҫ�Զ����xml
    public void customdialog(View v){
    	LayoutInflater mInflater = LayoutInflater.from(this);
    	View view = mInflater.inflate(R.layout.custom_dialog, null);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("��������");
    	builder.setView(view);
    	final AlertDialog dialog = builder.create();
    	dialog.show();
    	
    	Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
    	Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
    	
    	bt_ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();//�öԻ�����ʧ
			}
		});
    	bt_cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();//�öԻ�����ʧ
			}
		});
    }
}