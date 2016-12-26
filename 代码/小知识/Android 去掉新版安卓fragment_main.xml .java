/*新版ADT创建项目时会有两个XML文件:activity_main.xml和fragment_main.xml,不习惯的可以如下处理.
 
1.删除fragment_main.xml整个文件
 2.打开activity_main.xml删除里面的内容,切换到Graphical Layout视图,选择Layouts放入一个LinearLayout.
 3.打开MainActivity.java，替换如下内容.
 
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