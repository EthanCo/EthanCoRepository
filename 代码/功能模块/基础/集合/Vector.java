Vector v = new Vector();
V.add(E obj); //���ָ��Ԫ�ص�ĩβ
V.addElement(E obj); //���ָ��Ԫ�ص�ĩβ,���С��1
V.elementAt(int index); //ͨ������ֵ��Ԫ��,�൱��get
V.firstElement(); //��ȡ��һ��
V.insertElement(); //����
V.lastElement();   //��ȡ���һ��
V.removeElement(Object obj)	//�Ƴ�ƥ����
V.removeElementAt(int index); //�Ƴ�����ֵ��
V.elements() //����ö��Enumeration(�������)

Enumeration en V.elements();

while(en.hasMoreElements()){
	Sysout.out.println(en.nextElement());
}
