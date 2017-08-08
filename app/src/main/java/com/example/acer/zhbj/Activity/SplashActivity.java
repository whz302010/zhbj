package com.example.acer.zhbj.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import util.SpreUtil;
import com.example.acer.zhbj.R;

import org.zackratos.ultimatebar.UltimateBar;

public class SplashActivity extends Activity {

    private static final String ISFIST = "isfirst";
    private ImageView mBackground;
    private Boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setColorBar(Color.RED,0);
        mBackground = (ImageView) findViewById(R.id.splash_bg);
       initAnima();
        isFirst = SpreUtil.getBoolean(getApplicationContext(),ISFIST,true);





    }

    private void initAnima() {
        //旋转动画
        RotateAnimation rotate=new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //缩放动画
        ScaleAnimation scale=new ScaleAnimation(0,1,0,1);

        //渐变动画
        AlphaAnimation alpha=new AlphaAnimation(0,1);
        AnimationSet set=new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        set.setDuration(2000);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isFirst){
                    //进入引导页面
                    Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                    startActivity(intent);

                }else {
                    //进入主页面
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBackground.startAnimation(set);
    }
}
