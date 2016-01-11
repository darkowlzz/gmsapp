package space.darkowlzz.globalmeditationscope;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by sunny on 30/11/15.
 */
public class IntroActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private static final Integer NUM_PAGES = 6;

    Window window;

    private Button skipButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new IntroSlidePageAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == (NUM_PAGES - 1)) {
                    nextButton.setText(getText(R.string.intro_finish));
                } else {
                    nextButton.setText(getText(R.string.intro_next));
                }
                invalidateOptionsMenu();
            }
        });

        skipButton = (Button) findViewById(R.id.skip_button);
        nextButton = (Button) findViewById(R.id.next_button);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() < (NUM_PAGES - 1)) {
                    nextSlide();
                } else {
                    closeIntro();
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeIntro();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class IntroSlidePageAdapter extends FragmentStatePagerAdapter {
        public IntroSlidePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return IntroSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void nextSlide() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    public void closeIntro() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }
}
