import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

//��Ҫ����Mysql���ݿ�����
public class MyWeb {
	@Test //���Ǹ�������,��Junit Test ����
	public void demo1() throws SQLException, ClassNotFoundException
	{
		//��һ��JDBC����,����MYSQL���ݿ�
		
		//����һ���������ݿ�JDBCʵ��--�������� 
//		DriverManager.registerDriver(new Driver()); //�ᵼ��������������
		Class.forName("com.mysql.jdbc.Driver");
		//��������������ݿ����� 
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/day13","root","123");
		//����������sql��䷢�͸����ݿ�ִ�� 
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from users");
		while(rs.next()){
		   System.out.println(rs.getString("name"));
		   System.out.println(rs.getString("email"));
		}
		//�����ģ��ͷ���Դ
		rs.close();
		stmt.close();
		conn.close();
	}
}