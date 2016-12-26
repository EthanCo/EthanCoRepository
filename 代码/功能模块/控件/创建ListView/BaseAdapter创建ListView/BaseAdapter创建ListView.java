//BaseAdapter��AndroidӦ�ó����о����õ��Ļ���������������������Ҫ��;��
//��һ�����ݴ�����ListView��Spinner��Gallery��GridView��UI��ʾ��������Ǽ̳��Խӿ���Adapter
//��BaseAdapter����ListView
//1.��activity_main.xml�д���һ�����Բ���,��һ��ListView�ؼ�
//2.����һ��Android Xml,����Ĳ�����һ�еĲ���
//3.��MainActivity��onCreate������
		PersonDao dao = new PersonDao(this);
		persons = dao.queryAll(); // ��ȡ����

		personLV = (ListView) findViewById(R.id.personLV); // ���ListView
	personLV.setAdapter(new MyBaseAdapter()); // ��ListView���Adapter,����adapter�еķ�����ListView�����Ŀ
//4.����һ��MyBaseAdapter��̳���BaseAdapter
//����Adapter,��ÿ��person��������һ����Ŀ,��������Ŀװ��ListView
	public class MyBaseAdapter extends BaseAdapter {
		public int getCount() { // ����ListView��Ҫװ�����Ŀ������
			return persons.size();
		}
		public Object getItem(int position) {// ����ָ��λ���ϵĶ���
			return persons.get(position);
		}
		public long getItemId(int position) {// ������Ŀ��id
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {// ����ָ��λ�õ�View
			View item = View.inflate(getApplicationContext(), R.layout.item,null);

			TextView idTV = (TextView) item.findViewById(R.id.idTV);
			TextView nameTV = (TextView) item.findViewById(R.id.nameTV);
			TextView balanceTV = (TextView) item.findViewById(R.id.balanceTV);

			PersonCn p = persons.get(position);

			idTV.setText(p.getId() + ""); // ע�������String���ͣ����������������int��String
			nameTV.setText(p.getName());
			balanceTV.setText(p.getBalance() + "");

			return item;
		}
	}

//��Ӽ����¼�
	��ͼ