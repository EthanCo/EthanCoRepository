//����ڴ������ݿ�ʱid����Ϊ_id
//ע��һ��Ҫ��_id�����,���� id as _id���

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
         * ����1: �����Ļ���
         * ����2: �����ļ���ԴID
         * ����3: �������ݵ��α�
         * ����4: �α��е�����
         * ����5: ��Ŀ�е������ID, �α��е����ݾͻ������Щ�����
         */
        
		//��Ӽ����¼�
        personLV.setOnItemClickListener(new MyOnItemClickListener());
    }
	
	private class MyOnItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Cursor c = (Cursor) parent.getItemAtPosition(position);		// SimpleCursorAdapter���ص���һ��Cursor, �Ѿ��ƶ��������������
			Toast.makeText(getApplicationContext(), c.getString(1), Toast.LENGTH_SHORT).show();
		}
	}
}