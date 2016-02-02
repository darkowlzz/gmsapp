package space.darkowlzz.globalmeditationscope.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import space.darkowlzz.globalmeditationscope.R;
import space.darkowlzz.globalmeditationscope.utils.TinyDB;

/**
 * Created by sunny on 30/11/15.
 */
public class CoverActivity extends AppCompatActivity {

    private Thread thread;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        tinyDB = new TinyDB(getApplicationContext());

        final CoverActivity coverActivity = this;

        thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                    }
                } catch (InterruptedException ex) {

                }

                if (! tinyDB.getBoolean("firstRunDone")) {
                    Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                    tinyDB.putBoolean("firstRunDone", true);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                }
            }
        };

        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (thread) {
                thread.notifyAll();
            }
        }
        return true;
    }
}
