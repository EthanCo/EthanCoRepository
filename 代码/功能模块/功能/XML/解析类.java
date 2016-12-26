//������Ϊ���ż���
	private List<HeaderNew> parserHeaderNews(InputStream is) throws Exception {
		// TODO Auto-generated method stub
		
		List<HeaderNew> headerNews = null;
		HeaderNew headerNew = null;
		//1 �õ�������
		XmlPullParser parser = Xml.newPullParser();
		
		//��ֵ������
		parser.setInput(is, "UTF-8");
		
		//�õ������¼�
		int eventType = parser.getEventType();
		
		while(eventType != XmlPullParser.END_DOCUMENT){
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if("HeaderNews".equals(parser.getName())){
					headerNews = new ArrayList<HeaderNew>();
				}else if("HeaderNew".equals(parser.getName())){
					headerNew = new HeaderNew();
				}else if("image".equals(parser.getName())){
					headerNew.setImage(parser.nextText());
				}else if("title".equals(parser.getName())){
					headerNew.setTitle(parser.nextText());
				}else if("content".equals(parser.getName())){
					headerNew.setContent(parser.nextText());
				}else if("count".equals(parser.getName())){
					headerNew.setCount(parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if("HeaderNew".equals(parser.getName())){
					headerNews.add(headerNew);
					headerNew = null;
				}
				break;
			default:
				break;
			}
			
			eventType = parser.next();
		}
		
		return headerNews;
	}