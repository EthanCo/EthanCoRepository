
/*
final�ؼ�������һ���������͵ı���ʱ���ñ����������¸�ֵ����һ�ε�ֵΪ���յġ�
fianl�ؼ�������һ���������ͱ���ʱ���ñ�����������ָ���µĶ���
final�ؼ�������һ��������ʱ�򣬸ú������ܱ���д��
final�ؼ�������һ�����ʱ�򣬸��಻�ܱ��̳С�
*/

class Student {
	
	public Student(String name){
		this.name = name;
	}
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

public class Test {
	
	public static void main(String[] args) {
		//ֵ���� ֱ�Ӹ�ֵ
		final int a = 10;
		int b = a;
		b=20;
		System.out.println(""+a);
		System.out.println(""+b);
		
		//�ı�������
		final String str = "����str";
		String str2 = str;
		str2 ="str2";
		System.out.println(str);
		System.out.println(str2);
		
		//�ı�������
		final Student stu = new Student("zs");
		Student stu2 = stu;
		stu2 = new Student("lisi");
		System.out.println(stu.getName());
		System.out.println(stu2.getName());
		
		//��û�иı�����
		final Student zhangSan= new Student("����");
		Student liSi = zhangSan;
		liSi.setName("����");
		System.out.println(zhangSan.getName());
		System.out.println(liSi.getName());
	}
}
