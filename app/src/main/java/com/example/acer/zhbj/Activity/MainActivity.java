package com.example.acer.zhbj.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.acer.zhbj.R;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import fragment.ContentFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CONTENTTAG = "contenttag";
    private long firstTime;
    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);

        // create menu items;
        String titles[] = { "新闻", "专题", "组图", "互动" };
        int icon[] = { R.drawable.menu_arr_select, R.drawable.menu_arr_select, R.drawable.menu_arr_select,
                R.drawable.menu_arr_select};

        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }

        initFragment();
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        ContentFragment contentFragment = new ContentFragment();
        fragmentTransaction.replace(R.id.container,contentFragment,CONTENTTAG);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view) {
// TODO: 2017/7/21
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
    @Override
    public void onBackPressed() {
        firstTime = System.currentTimeMillis();


        if (firstTime+2000>System.currentTimeMillis()){

            super.onBackPressed();
        }else {

            Toast.makeText(this,"连续点击两次退出应用",Toast.LENGTH_SHORT).show();
        }
    }
}
