import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

public class TestActivity extends Activity {
        private long mExitTime;

        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);

        }
        
        public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if ((System.currentTimeMillis() - mExitTime) > 2000) {
                                Object mHelperUtils;
                                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                                mExitTime = System.currentTimeMillis();

                        } else {
                                finish();
                        }
                        return true;
                }
                return super.onKeyDown(keyCode, event);
        }
}