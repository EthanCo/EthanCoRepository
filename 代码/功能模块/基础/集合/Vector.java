Vector v = new Vector();
V.add(E obj); //添加指定元素到末尾
V.addElement(E obj); //添加指定元素到末尾,其大小加1
V.elementAt(int index); //通过索引值拿元素,相当于get
V.firstElement(); //获取第一个
V.insertElement(); //插入
V.lastElement();   //获取最后一个
V.removeElement(Object obj)	//移除匹配项
V.removeElementAt(int index); //移除索引值项
V.elements() //返回枚举Enumeration(像迭代器)

Enumeration en V.elements();

while(en.hasMoreElements()){
	Sysout.out.println(en.nextElement());
}
