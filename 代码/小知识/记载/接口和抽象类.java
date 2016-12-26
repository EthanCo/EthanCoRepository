1.abstract class 在c#语言中表示的是一种继承关系，一个类只能使用一次继承关系。但是，一个类却可以实现多个interface。


2.在abstract class 中可以有自己的数据成员，也可以有非abstarct的成员方法，而在interface中，只能够有静态的不能被修改的数据成员（也就是必须是static final的，不过在 interface中一般不定义数据成员），所有的成员方法都是abstract的。


3.abstract class和interface所反映出的设计理念不同。其实abstract class表示的是"is-a"关系，interface表示的是"like-a"关系。


4.实现抽象类和接口的类必须实现其中的所有方法。抽象类中可以有非抽象方法。接口中则不能有实现方法。


5.接口中定义的变量默认是public static final 型，且必须给其初值，所以实现类中不能重新定义，也不能改变其值。


6.抽象类中的变量默认是 friendly 型，其值可以在子类中重新定义，也可以重新赋值。


7.接口中的方法默认都是 public,abstract 类型的。