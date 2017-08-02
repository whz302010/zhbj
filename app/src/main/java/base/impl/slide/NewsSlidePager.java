package base.impl.slide;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.zhbj.Activity.MainActivity;
import com.example.acer.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnChildClick;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import Bean.NewsMenu;
import base.SlideDetailPager;
import base.impl.slide.tabNews.TabDetailPager;
import gloable.MyApplication;
import util.ToastUtil;

/**
 * Created by acer on 2017/7/24.
 */

public class NewsSlidePager extends SlideDetailPager implements ViewPager.OnPageChangeListener{
    private ArrayList<NewsMenu.NewsTabData> mNewsData;
    private ArrayList<TabDetailPager> mDetailPagers;
    @ViewInject(R.id.vp_tab)
    private ViewPager vp_tab;
    @ViewInject(R.id.indicator)
    private TabPageIndicator indicator;
    public NewsSlidePager(Activity activity,  ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        mNewsData=children;

    }


    @Override
    public View initView() {
        View view = View.inflate(MyApplication.getContext(),R.layout.slide_news_detail, null);
        ViewUtils.inject(this,view);
        return  view;
    }

    @Override
    public void initData() {
        super.initData();
        mDetailPagers=new ArrayList<>();
        for (int i=0;i<mNewsData.size();i++){
            TabDetailPager tabDetailPager = new TabDetailPager(activity,mNewsData.get(i));
            Log.e("22222222222222"+i, "initData: "+mNewsData.get(i) );
            mDetailPagers.add(tabDetailPager);
        }

        vp_tab.setAdapter(new TabNewsAdapter());


          //注意如果viewpager没有数据会报异常
            indicator.setViewPager(vp_tab);
            indicator.setOnPageChangeListener(this);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

           if (position==0){
             setSlideEnable(true);
            }else {
               setSlideEnable(false);
           }
    }
private void setSlideEnable(boolean slide){
        MainActivity mainUI= (MainActivity) activity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (slide){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

}
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TabNewsAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mNewsData.size();

        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = mDetailPagers.get(position);
            View mRootView = tabDetailPager.mRootView;
            container.addView(mRootView);
            //初始化数据

                tabDetailPager.initData();


            return mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsData.get(position).title;

        }
    }
            @OnClick(R.id.btn_next)
            public void nextPager(View view){
                int currentItem=vp_tab.getCurrentItem();
                currentItem++;
                vp_tab.setCurrentItem(currentItem);
                }
}
