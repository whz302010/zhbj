package com.example.acer.zhbj.Activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.acer.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


import org.zackratos.ultimatebar.UltimateBar;

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
        setColorBar(Color.RED,0);
//
//        //设置状态栏和导航栏
//        UltimateBar ultimateBar = new UltimateBar(this);
//        ultimateBar.setTransparentBar(Color.RED,2);
//        ultimateBar.setColorBar(Color.RED);




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

    public void setColorBar(@ColorInt int color, int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
//            View decorView = window.getDecorView();
//            int option =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int alphaColor = alpha == 0 ? color : calculateColor(color, alpha);
            window.setStatusBarColor(alphaColor);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


        @ColorInt
        private int calculateColor(@ColorInt int color, int alpha) {
            float a = 1 - alpha / 255f;
            int red = color >> 16 & 0xff;
            int green = color >> 8 & 0xff;
            int blue = color & 0xff;
            red = (int) (red * a + 0.5);
            green = (int) (green * a + 0.5);
            blue = (int) (blue * a + 0.5);
            return 0xff << 24 | red << 16 | green << 8 | blue;
        }

    private void setRootView(Activity activity, boolean fit) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(fit);
                ((ViewGroup)childView).setClipToPadding(fit);
            }
        }
    }

    }
