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
		// simpleAdapter传入指定的数据和布局文件，以及匹配关系，自动生成View,装入ListView
		// 参数1：上下文环境
		// 参数2：数据,List<Map<String,Object>>,每个Person的数据装入一个Map,将所有Map装入List
		// 参数3：布局文件的资源id
		// 参数4：Map的key值,和参数5中的id对应
		// 参数5：View中的id
	}
}

添加监听事件