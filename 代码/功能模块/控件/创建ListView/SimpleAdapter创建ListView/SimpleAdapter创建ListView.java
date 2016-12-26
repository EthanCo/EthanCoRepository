public class SimpleAdapterActivity extends ActionBarActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PersonDao dao = new PersonDao(this);
		List<PersonCn> persons = dao.queryAll();

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (PersonCn p : persons) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", p.getId());
			map.put("name", p.getName());
			map.put("balance", p.getBalance());
			data.add(map);
		}

		ListView personLV = (ListView) findViewById(R.id.personLV);
		personLV.setAdapter(new SimpleAdapter(this, data, R.layout.item,
				new String[] { "id", "name", "balance" }, new int[] {
						R.id.idTV, R.id.nameTV, R.id.balanceTV }));
		// simpleAdapter����ָ�������ݺͲ����ļ����Լ�ƥ���ϵ���Զ�����View,װ��ListView
		// ����1�������Ļ���
		// ����2������,List<Map<String,Object>>,ÿ��Person������װ��һ��Map,������Mapװ��List
		// ����3�������ļ�����Դid
		// ����4��Map��keyֵ,�Ͳ���5�е�id��Ӧ
		// ����5��View�е�id
	}
}

��Ӽ����¼�