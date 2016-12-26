public class MainActivity extends Activity {
	
	private EditText et_path;
	private TextView tv_html;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et_path = (EditText) findViewById(R.id.et_path);
        tv_html = (TextView) findViewById(R.id.tv_html);
    }
    
    public void look(View v){
    	String path = et_path.getText().toString();
    	if(TextUtils.isEmpty(path)){
    		Toast.makeText(this, "输入的路径为空", 1).show();
    	}else{
    		try {
				URL url = new URL(path);
				
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				conn.setRequestMethod("GET");
				
				conn.setConnectTimeout(5000);
				
				if(conn.getResponseCode() == 200){
					InputStream is = conn.getInputStream();
					
					//把流转化为文本信息  String 
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while((len = is.read(buffer)) != -1){
						bos.write(buffer, 0, len);
					}
					String html = bos.toString();
					bos.close();
					is.close();
					tv_html.setText(html);
				}else{
					Toast.makeText(this, "输入的路径不存在", 1).show();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "获取网页失败", 1).show();
			}
    	}
    }
}