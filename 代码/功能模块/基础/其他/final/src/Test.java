
/*
final关键字修饰一个基本类型的变量时，该变量不能重新赋值，第一次的值为最终的。
fianl关键字修饰一个引用类型变量时，该变量不能重新指向新的对象。
final关键字修饰一个函数的时候，该函数不能被重写。
final关键字修饰一个类的时候，该类不能被继承。
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
		//值类型 直接赋值
		final int a = 10;
		int b = a;
		b=20;
		System.out.println(""+a);
		System.out.println(""+b);
		
		//改变了引用
		final String str = "这是str";
		String str2 = str;
		str2 ="str2";
		System.out.println(str);
		System.out.println(str2);
		
		//改变了引用
		final Student stu = new Student("zs");
		Student stu2 = stu;
		stu2 = new Student("lisi");
		System.out.println(stu.getName());
		System.out.println(stu2.getName());
		
		//并没有改变引用
		final Student zhangSan= new Student("张三");
		Student liSi = zhangSan;
		liSi.setName("李四");
		System.out.println(zhangSan.getName());
		System.out.println(liSi.getName());
	}
}
