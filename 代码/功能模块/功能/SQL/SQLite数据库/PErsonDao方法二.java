		 SQLiteDatabase db=helper.getWritableDatabase();
		 //插入
		 ContentValues values=new ContentValues();//类似于Map容器(键值对),键是String,存要插入的数据
		 values.put("name", "NewTest");//某些情况下，程序会接收一个ContentValues参数,这是用这种方式储存比较方便
		 values.put("balance", "99999");
		 db.insert("person", "name", values); //第一个参数：表名 
		 //第二个参数随便写一个列名即可,用来在想插入一条除了主键全部为空的记录时使用
		 //返回值：返回插入的id

		 //删除 第二个参数是条件
		 db.delete("person", "id=?", new String[]{"100"});

		 //更新 第二个参数是条件
		 ContentValues values=new ContentValues();
		 values.put("name", "NewTest");
		 values.put("balance", "11111");
		 db.update("person", values, "id=?", new String[]{"idvalue"});
		 db.close();

		 //查询
		 SQLiteDatabase db = helper.getReadableDatabase();
		 //第二个参数列名(如果为null返回所有列) 第三个参数条件 第四个参数 条件的值 第五个groupBy 第六个having 第七个orderBy
		 Cursor c = db.query("person", new String[] { "id,name,balance" },
		 "id=?", new String[] { "idvalue" }, null, null, null);
		 db.close();
		 PersonCn p = null;
		 if (c.moveToNext()) {// 判断游标是否包含下一条记录，如果包含,将游标向后移动一位
		 //
		 //查询得到的cursor是指向第一条记录之前的,因此查询得到cursor后第一次调用moveToFirst或moveToNext都可以将cursor移动到第一条记录上
		 String name = c.getString(c.getColumnIndex("name"));//获取name字段的索引,然后根据索引获取数据,转为String
		 int balance = c.getInt(1);// 获取1号索引上的数据,转为int
		 p = new PersonCn(id, name, balance);// 创建person对象
		 }
		 c.close();
		 db.close();// 如果只有一个线程，可以不关数据库，还可以提高效率
		 return p;