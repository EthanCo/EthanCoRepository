public class DBOpenHelper extends SQLiteOpenHelper { // 定义工具类,继承SQLiteOpenHelper

	public DBOpenHelper(Context context) { // 创建对象的时候,需要传入上下文环境
		super(context, "zhk.db", null, 2);
		// 由于父类没有无参构造函数,必须显示调用有参的构造函数
		// 参数1：上下文环境,用来确定数据库文件储存的目录
		// 参数2：数据库文件的名称
		// 参数3：生成游标的工厂(用来指向数据库里面的某一行),填null就是使用默认的
		// 参数4：数据库的版本,从1开始
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建
		java.lang.System.out.println("创建");
		db.execSQL("CREATE TABLE person(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20))");// 执行SQL语句,创建表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 升级
		java.lang.System.out.println("修改");
		db.execSQL("ALTER TABLE person ADD balance INTEGER"); // 插入列
	}
}