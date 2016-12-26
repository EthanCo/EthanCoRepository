public class PersonService{
	public List<Person> loadPersons(InputStream in) throws Exception{
		XmlPullParser parser = Xml.newPullParser();				//获得解析器
		parser.setInput(in, "UTF-8");							//设置输入流，指定码表
		ArrayList<Person> persons = new ArrayList<Person>();
		Person p = null;
		for(int type=parser.getEventType();type!=XmlPullParser.END_DOCUMENT;type=parser.next()){
			if(type==XmlPullParser.START_TAG){							//如果遇到了标签开始事件
				if(parser.getName().equals("person")){					//如果标签名为Pserson
					p = new Person();
					String id = parser.getAttributeValue(0);			//按索引取值
					//String id = (parser.getAttributeValue(namespace, name)) 按id名取值
					p.setId(Integer.parseInt(id));												
					persons.add(p);										//装入集合
				}else if(parser.getName().equals("name")){				//如果标签名为name
					//设置name
					p.setName(parser.nextText());
				}else if(parser.getName().equals("age")){				//如果标签名为age
					//设置age
					p.setAge(Integer.parseInt(parser.nextText()));
				}
			}
		}
		return persons;
	}
}

//模式
XmlPullparser.START_DOCUMENT 
XmlPullparser.START_TAG
XmlPullparser.TEXT
XmlPullparser.ENDTAG
XmlPullparser.END_DOCUMENT