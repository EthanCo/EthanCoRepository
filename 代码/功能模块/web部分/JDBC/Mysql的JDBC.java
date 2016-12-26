import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

//需要导入Mysql数据库驱动
public class MyWeb {
	@Test //这是个测试类,用Junit Test 开启
	public void demo1() throws SQLException, ClassNotFoundException
	{
		//第一个JDBC程序,连接MYSQL数据库
		
		//步骤一：加载数据库JDBC实现--加载驱动 
//		DriverManager.registerDriver(new Driver()); //会导致驱动加载两次
		Class.forName("com.mysql.jdbc.Driver");
		//步骤二：建立数据库连接 
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day13","root","123");
		//步骤三：将sql语句发送给数据库执行 
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from users");
		while(rs.next()){
		   System.out.println(rs.getString("name"));
		   System.out.println(rs.getString("email"));
		}
		//步骤四：释放资源
		rs.close();
		stmt.close();
		conn.close();
	}
}