## Class类 ##
Java程序中的各个Java类属于同一种事物,描述这类事物的Java类名就是Class  
人 -> Person  
Java类 -> Class
### 获取各个字节码对应的实例对象 ###
方法一:类名:class  

	Class cls1 = Person.class;

方法二:对象:getClass  

	Class cls2 = p1.getClass();

方法三:forName

	Class cls3 = Class.forName("java.lang.String");

方法四
	
	 Class clazz = getClassLoader().getClass("java.lang.String");

#### .class()、getClass()、Class.forName()、getClassLoader()的区别 ####
.class，是获取声明时的类。  
getClass()，是获取运行时的类。  
Class.forName()是通过类名来获得类。  
getClassLoader()是获得类的加载器。  

若字节码曾经被加载过，已经待在Java虚拟机里面了,可以直接返回,如方法一和方法二  
若Java虚拟机中还没有这份字节码,则用类加载器去加载,把加载的字节码缓存到虚拟机中,以后,使用这份字节码就不需要加载了,如方法三  
### 预定义对象 ###
有9个预定义对象，8个基本类型和void

	Class class1 = boolean.class;
	Class class2 = int.class;
	//.......
	Class class3 = Void.class;

用isPrimitive 判断是否是基本类型  

	int.class.isPrimitive

用isArray判断是否是数组
	
	int[].class.isArray

获得类型  
	getclass().getName();

## 反射 ##
	反射就是把Java类中的各种成分映射成相应的Java类
Field:成员变量  
Method:方法  
Constructor:构造方法  
Package:包

### Constructor ###
#### 得到某个类所有的构造方法 ####
	Class.forName("java.lang.String").getConstructors();
#### 得到某一个构造方法 ####
根据getConstructor中的参数返回相应的构造方法  

	Class.forName("java.lang.String").getConstructor(StringBuffer.class);

#### Constructor可以干什么 ####
获取实例对象(一个constructor可以new出很无数个实例对象)  

	(强制转换)constructor1.newInstance(参数要和getConstructor中的一致);

 
通过默认构造方法创建实例(内部会缓存实例，第二次获取就不会再创建，直接获取)
	
	String obj = (String) Class.frName("java.lang.String").newInstance();

	原版反射的步骤是 Class -> Constructor -> newInstance  
	用Class.newInstance()直接在内部Constructor，即直接  
	Class -> Class.newInstance()即可获得默认构造方法的实例

### Field ###
代表某个类中的一个成员变量  

Person类

	public class Person {
		public String name;
	
		private int age;
	
		public int getAge() {
			return age;
		}
	
		public void setAge(int age) {
			this.age = age;
		}
	}

通过反射获取成员变量  

	Person person = new Person();
	person.setAge(20);
	person.name = "EthanCo";
	//Field fieldName = person.getClass().getField("name"); 都可以
	Field fieldName = Class.forName("reflectTest.Person").getField("name");
	//fieldName的值为多少？不是EthanCo，因为fieldName不是对象身上的变量，而是类上，要通过field.get("实例")或getDeclaredField来获取该实例上的值
	String name = (String) fieldName.get(person);
	System.out.println("name:" + name);

	//对于私有变量，需暴力破解
	Field fieldAge = person.getClass().getDeclaredField("age"); //getDeclaredField 不管是私有还是public的，都可以获取
	fieldAge.setAccessible(true); //设置可以访问 fieldAge.get("age")
	int age = (int) fieldAge.get(person);
	System.out.println("age:" + age);  

#### 获得所有的成员变量 ####
	
	obj.getClass().getFields();

	
> getFields和getDeclaredFields的区别:  
> getFields()只能访问类中声明为公有的字段,私有的字段它无法访问，能访问从其它类继承来的公有方法  
> getDeclaredFields()能访问类中所有的字段,与public,private,protect无关，不能访问从其它类继承来的方法

#### 比较成员变量类型 ####
	if(field.getType() == String.class)
#### 设置值 ####

	field.set(obj,新的值);

#### 将实例中的String类型的成员变量值的b都换成a ####

![将实例中的String类型的成员变量值的b都换成a](http://img-storage.qiniudn.com/15-10-31/32766448.jpg)

### Method ###

#####代表某个类中的一个成员方法  
method.invoke(owner, args)：


- owner:执行这个方法的对象，args:参数，可以这么理解：owner对象中带有参数args的method方法。
- 返回值是Object，也既是该方法的返回值。  

####  ####
	String s = "abc";  
	Method methodCharAt = String.class.getMethod("charAt", int.class);
	String newString =methodCharAt.invoke(s, 1);//invoke是方法身上的方法，s是执行该方法的对象(必须是实例化的对象)
	System.out.println(newString); 

#### 调用静态方法 ####
	methodCharAt.invoke(null, 值);

### 反射数组 ###

- 若数组的维数(一维、二维等)和数组类型都相等，那么他们的字节码是同一个
- 代表数组的Class实例对象的getSuperClass()方法返回的父类为Object类对应的Class
- 基本类型的一维数组可以被当做Object类型使用，不能当做Object[]类型使用
- 非基本类型的一维数组，既可以当做Object类型使用，又可以当做Object[]类型使用

#### ArrayList.asList时int[]和String[]的差别 ####
因为asList接收Object[]类型，但int[]不是Object[]类型，所以交给jdk1.5之后的asList(T...t)处理，将int[]作为一个对象处理
	
	Arrays.asList(new int[]{1,2,3}); //打印内容为:[@hascode]
	Arrays.asList(new String[]{"1","2","3"}); //打印内容为:[1,2,3]

#### 数组的反射应用 ####
输出数组

	private static void printObject(Object obj) {
		Class clazz = obj.getClass();
		if (clazz.isArray()) { //如果是数组
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				System.out.println(Array.get(obj, i));
			}
		} else {
			System.out.println(obj);
		}
	}  

调用  

	String[] array = new String[] { "1", "2", "3" };
	String valueString = "test";
	printObject(array);
	printObject(valueString);

### 反射的作用 ###
实现框架的功能  
![实现框架的功能](http://img-storage.qiniudn.com/15-10-31/45318048.jpg)

