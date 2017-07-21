package com.example.acer.zhbj.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.acer.zhbj.R;

import java.util.ArrayList;

import util.SpreUtil;

import static android.content.ContentValues.TAG;

public class GuideActivity extends Activity {

    private ViewPager viewPager;
    private Button btn_begin;
    private LinearLayout ll_point_container;
    private int[] mImageIds;
    private ArrayList<ImageView> mImageList;
    private View red_point;
    private RelativeLayout rl_container;
    private int pointDis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_begin = (Button) findViewById(R.id.btn_begin);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_container);
        rl_container = (RelativeLayout) findViewById(R.id.rl_container);
        red_point = findViewById(R.id.red_point);
        mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

        initData();
        viewPager.setAdapter(new GuideAdapter());
        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpreUtil.putBoolean(getApplicationContext(),"isfirst",false);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointDis = ll_point_container.getChildAt(1).getLeft()-ll_point_container.getChildAt(0).getLeft();
                Log.d(TAG, "onGlobalLayout: "+pointDis);

                red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             //positionOffsetPixels为两页面距离百分比
//             Toast.makeText(getApplicationContext(),"移动了"+pointDis,Toast.LENGTH_SHORT).show();
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) red_point.getLayoutParams();
             params.leftMargin= (int) (pointDis*(positionOffset+position));

             red_point.setLayoutParams(params);

         }

         @Override
         public void onPageSelected(int position) {
             if (position==mImageIds.length-1){
                 btn_begin.setVisibility(View.VISIBLE);
             }else {
                 btn_begin.setVisibility(View.INVISIBLE);
             }

         }

         @Override
         public void onPageScrollStateChanged(int state) {

         }
     });


    }

    private void initData() {

        mImageList = new ArrayList<>();
        for (int i=0;i<mImageIds.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageList.add(imageView);

            //加载三个点
           ImageView point=new ImageView(this);
            point.setImageResource(R.drawable.item_point_gray);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    ,LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i>0){
                params.leftMargin=40;
            }

            point.setLayoutParams(params);

            ll_point_container.addView(point);

        }


    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view=mImageList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
