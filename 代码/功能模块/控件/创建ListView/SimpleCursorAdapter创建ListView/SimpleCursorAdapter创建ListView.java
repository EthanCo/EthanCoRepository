//最好在创建数据库时id列名为_id
//注意一定要有_id这个列,可用 id as _id解决

public class SimpleCursorAdapterActivity extends Activity {
    private ListView personLV;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        PersonDao dao = new PersonDao(this);
        Cursor c = dao.queryAllCursor();
        
        personLV = (ListView) findViewById(R.id.personLV);		
        personLV.setAdapter(new SimpleCursorAdapter(this, R.layout.item, c	//
        		, new String[]{ "_id", "name", "balance" }	//
        		, new int[] { R.id.idTV, R.id.nameTV, R.id.balanceTV }));
        
        /*
         * 参数1: 上下文环境
         * 参数2: 布局文件资源ID
         * 参数3: 包含数据的游标
         * 参数4: 游标中的列名
         * 参数5: 条目中的组件的ID, 游标中的数据就会放在这些组件上
         */
        
		//添加监听事件
        personLV.setOnItemClickListener(new MyOnItemClickListener());
    }
	
	private class MyOnItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Cursor c = (Cursor) parent.getItemAtPosition(position);		// SimpleCursorAdapter返回的是一个Cursor, 已经移动到点击的索引了
			Toast.makeText(getApplicationContext(), c.getString(1), Toast.LENGTH_SHORT).show();
		}
	}
}