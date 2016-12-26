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
    		Toast.makeText(this, "ͼƬ��·��Ϊ��", 1).show();
    	}else{
    		try {
				// ����·��
				URL url = new URL(path);
				//������
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//����ʽ
				conn.setRequestMethod("GET");
				//��ʱʱ��
				conn.setConnectTimeout(5000);
				
				if(conn.getResponseCode() == 200){
					InputStream is = conn.getInputStream();
					
					//����ת��ΪͼƬ
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					if(bitmap == null){
						Toast.makeText(this, "��ȡ��ͼƬ��Ϊ��", 1).show();
					}else{
						iv.setImageBitmap(bitmap);
					}
				}else{
					Toast.makeText(this, "�����·��������", 1).show();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				if(e instanceof MalformedURLException){
					Toast.makeText(this, "�����·����ʽ����", 1).show();
				}else if(e instanceof ConnectTimeoutException){
					Toast.makeText(this, "���ӳ�ʱ����", 1).show();
				}else if(e instanceof IOException){
					Toast.makeText(this, "��ȡ���ݴ���", 1).show();
				}else{
					Toast.makeText(this, "δ֪����", 1).show();
				}
			}
    	}
    }
}