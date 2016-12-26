import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText et_path;
	private ImageView iv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        et_path = (EditText) findViewById(R.id.et_path);
        iv = (ImageView) findViewById(R.id.iv);
    }
    
    public void look(View v){
    	String path = et_path.getText().toString();
    	if(path.equals("")){
    		Toast.makeText(this, "图片的路径为空", 1).show();
    	}else{
    		try {
				// 构建路径
				URL url = new URL(path);
				//打开连接
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//请求方式
				conn.setRequestMethod("GET");
				//超时时间
				conn.setConnectTimeout(5000);
				
				if(conn.getResponseCode() == 200){
					InputStream is = conn.getInputStream();
					
					//把流转化为图片
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					if(bitmap == null){
						Toast.makeText(this, "获取的图片流为空", 1).show();
					}else{
						iv.setImageBitmap(bitmap);
					}
				}else{
					Toast.makeText(this, "输入的路径不存在", 1).show();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				if(e instanceof MalformedURLException){
					Toast.makeText(this, "输入的路径格式错误", 1).show();
				}else if(e instanceof ConnectTimeoutException){
					Toast.makeText(this, "连接超时错误", 1).show();
				}else if(e instanceof IOException){
					Toast.makeText(this, "获取数据错误", 1).show();
				}else{
					Toast.makeText(this, "未知错误", 1).show();
				}
			}
    	}
    }
}