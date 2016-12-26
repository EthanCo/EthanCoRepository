#Java中只有按值传递，没有引用传递！
[http://guhanjie.iteye.com/blog/1683637](http://guhanjie.iteye.com/blog/1683637)  


## 试验 ##

    public static void changeInt(int a, int b) {
        a = 6;
        b = 7;
    }

    public static void changeStr(String a, String b) {
        a = "hello";
        b = "world";
    }

    static class Person {
        public String name = "Jack";
        public int age = 20;
    }

    public static void main(String[] args) {
        int intA = 1;
        int intB = 10;
        String stringA = "mm";
        String stringB = "boy";
        changeInt(intA, intB);
        changeStr(stringA, stringB);
        System.out.println("intA:" + intA + " intB:" + intB);
        System.out.println("stringA:" + stringA + " stringB:" + stringB);

        Person person = new Person();
        System.out.println("name:" + person.name + " age:" + person.age);

        person.name = "Rose";
        person.age = 18;
        System.out.println("new name:" + person.name + " age:" + person.age);
    }                                                                                                              

### 输出结果 ###

	intA:1 intB:10
	stringA:mm stringB:boy
	name:Jack age:20
	new name:Rose age:18