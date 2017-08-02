package com.example.acer.zhbj.Activity;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.acer.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


import fragment.ContentFragment;
import fragment.SlideFragment;


public class MainActivity extends SlidingFragmentActivity{

    private static final String CONTENTTAG = "contenttag";
    private static final String SLIDETAG = "slidetaag";
    private long firstTime;
    public SlidingMenu slidemenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // configure the SlidingMenu
        setBehindContentView(R.layout.slide_munu);
        slidemenu = getSlidingMenu();
        slidemenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidemenu.setBehindOffset(600);
        initFragment();

    }

    public SlideFragment getSlideFragment(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        SlideFragment slideFragment = (SlideFragment) fm.findFragmentByTag(SLIDETAG);
        return slideFragment;
    }

    public ContentFragment getContentFragment(){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        ContentFragment contentFragment = (ContentFragment) fm.findFragmentByTag(CONTENTTAG);
        return contentFragment;
    }

    private void initFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        ContentFragment contentFragment = new ContentFragment();
        SlideFragment slideFragment = new SlideFragment();
        fragmentTransaction.replace(R.id.container,contentFragment,CONTENTTAG);
        fragmentTransaction.replace(R.id.slide_container,slideFragment,SLIDETAG);
        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {

        if (firstTime+2000>System.currentTimeMillis()){
            super.onBackPressed();
        }else {
            Toast.makeText(this,"连续点击两次退出应用",Toast.LENGTH_SHORT).show();
        }
        firstTime = System.currentTimeMillis();
    }
}
