/*�°�ADT������Ŀʱ��������XML�ļ�:activity_main.xml��fragment_main.xml,��ϰ�ߵĿ������´���.
 
1.ɾ��fragment_main.xml�����ļ�
 2.��activity_main.xmlɾ�����������,�л���Graphical Layout��ͼ,ѡ��Layouts����һ��LinearLayout.
 3.��MainActivity.java���滻��������.
 
 */
 

 
import android.app.Activity;
 import android.os.Bundle;
 import android.view.Menu;
 
public class MainActivity extends Activity {
 
    @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
     }
 
    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.main, menu);
         return true;
     }
 
}