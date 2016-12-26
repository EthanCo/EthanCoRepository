//BaseAdapter是Android应用程序中经常用到的基础数据适配器，它的主要用途是
//将一组数据传到像ListView、Spinner、Gallery及GridView等UI显示组件，它是继承自接口类Adapter
//用BaseAdapter创建ListView
//1.在activity_main.xml中创建一个线性布局,及一个ListView控件
//2.创建一个Android Xml,里面的布局是一行的布局
//3.在MainActivity的onCreate方法中
		PersonDao dao = new PersonDao(this);
		persons = dao.queryAll(); // 获取数据

		personLV = (ListView) findViewById(R.id.personLV); // 获得ListView
	personLV.setAdapter(new MyBaseAdapter()); // 给ListView添加Adapter,按照adapter中的方法对ListView添加条目
//4.创建一个MyBaseAdapter类继承自BaseAdapter
//定义Adapter,把每个person对象生成一个条目,将所有条目装入ListView
	public class MyBaseAdapter extends BaseAdapter {
		public int getCount() { // 返回ListView中要装入的条目的数量
			return persons.size();
		}
		public Object getItem(int position) {// 返回指定位置上的对象
			return persons.get(position);
		}
		public long getItemId(int position) {// 返回条目的id
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {// 返回指定位置的View
			View item = View.inflate(getApplicationContext(), R.layout.item,null);

			TextView idTV = (TextView) item.findViewById(R.id.idTV);
			TextView nameTV = (TextView) item.findViewById(R.id.nameTV);
			TextView balanceTV = (TextView) item.findViewById(R.id.balanceTV);

			PersonCn p = persons.get(position);

			idTV.setText(p.getId() + ""); // 注意必须是String类型，这个方法的重载有int和String
			nameTV.setText(p.getName());
			balanceTV.setText(p.getBalance() + "");

			return item;
		}
	}

//添加监听事件
	如图