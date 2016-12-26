		 SQLiteDatabase db=helper.getWritableDatabase();
		 //����
		 ContentValues values=new ContentValues();//������Map����(��ֵ��),����String,��Ҫ���������
		 values.put("name", "NewTest");//ĳЩ����£���������һ��ContentValues����,���������ַ�ʽ����ȽϷ���
		 values.put("balance", "99999");
		 db.insert("person", "name", values); //��һ������������ 
		 //�ڶ����������дһ����������,�����������һ����������ȫ��Ϊ�յļ�¼ʱʹ��
		 //����ֵ�����ز����id

		 //ɾ�� �ڶ�������������
		 db.delete("person", "id=?", new String[]{"100"});

		 //���� �ڶ�������������
		 ContentValues values=new ContentValues();
		 values.put("name", "NewTest");
		 values.put("balance", "11111");
		 db.update("person", values, "id=?", new String[]{"idvalue"});
		 db.close();

		 //��ѯ
		 SQLiteDatabase db = helper.getReadableDatabase();
		 //�ڶ�����������(���Ϊnull����������) �������������� ���ĸ����� ������ֵ �����groupBy ������having ���߸�orderBy
		 Cursor c = db.query("person", new String[] { "id,name,balance" },
		 "id=?", new String[] { "idvalue" }, null, null, null);
		 db.close();
		 PersonCn p = null;
		 if (c.moveToNext()) {// �ж��α��Ƿ������һ����¼���������,���α�����ƶ�һλ
		 //
		 //��ѯ�õ���cursor��ָ���һ����¼֮ǰ��,��˲�ѯ�õ�cursor���һ�ε���moveToFirst��moveToNext�����Խ�cursor�ƶ�����һ����¼��
		 String name = c.getString(c.getColumnIndex("name"));//��ȡname�ֶε�����,Ȼ�����������ȡ����,תΪString
		 int balance = c.getInt(1);// ��ȡ1�������ϵ�����,תΪint
		 p = new PersonCn(id, name, balance);// ����person����
		 }
		 c.close();
		 db.close();// ���ֻ��һ���̣߳����Բ������ݿ⣬���������Ч��
		 return p;