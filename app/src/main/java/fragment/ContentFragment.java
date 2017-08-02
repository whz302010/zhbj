package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.acer.zhbj.Activity.MainActivity;
import com.example.acer.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import base.BasePager;
import base.impl.main.GovAffairsPager;
import base.impl.main.HomePager;
import base.impl.main.NewsCenterPager;
import base.impl.main.SettingPager;
import base.impl.main.SmartServicePager;
import view.MyViewPager;

/**
 * Created by acer on 2017/7/21.
 */

public class ContentFragment extends BaseFragment {

    private MyViewPager viewPager;
    private RadioGroup rg_tabs;
    private ArrayList<BasePager> mPagers;
    private ContentAdapter contentAdapter;



//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Context context= new ContextThemeWrapper(getActivity(), R.style.StyleIndicator);
//        // clone the inflater using the ContextThemeWrapper
//        LayoutInflater  localInflater=inflater.cloneInContext(context);
//
//        return view;
//    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content,null);
        viewPager = (MyViewPager) view.findViewById(R.id.viewpager);
        rg_tabs = (RadioGroup) view.findViewById(R.id.radiogroup);
        return view;
    }

    @Override
    public void initData() {
        mPagers=new ArrayList<>();
        HomePager homePager = new HomePager(mActivity);
        NewsCenterPager newsCenterPager = new NewsCenterPager(mActivity);
        SettingPager settingPager = new SettingPager(mActivity);
        GovAffairsPager govAffairsPager = new GovAffairsPager(mActivity);
        SmartServicePager smartServicePager = new SmartServicePager(mActivity);
        mPagers.add(homePager);
        mPagers.add(newsCenterPager);
        mPagers.add(settingPager);
        mPagers.add(govAffairsPager);
        mPagers.add(smartServicePager);
        contentAdapter = new ContentAdapter();
        viewPager.setAdapter(contentAdapter);


        rg_tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.tab_home:
                        viewPager.setCurrentItem(0,false);

                        break;
                    case R.id.tab_news:
                        viewPager.setCurrentItem(1,false);

                        break;
                    case R.id.tab_smart:
                        viewPager.setCurrentItem(4,false);
                        break;
                    case R.id.tab_setting:
                        viewPager.setCurrentItem(2,false);
                        break;

                    case R.id.tab_gov:
                        viewPager.setCurrentItem(3,false);
                        break;
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagers.get(position).initData();
                if (position==1 && position==4){

                setSlideMenuEnable(true);

                }else {
                    setSlideMenuEnable(false);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //手动加载第一页数据
        mPagers.get(0).initData();
        setSlideMenuEnable(false);

    }

    private void setSlideMenuEnable(boolean b) {
        MainActivity mainUI= (MainActivity) getActivity();
        SlidingMenu slidemenu = mainUI.getSlidingMenu();
        if (b){
            slidemenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        }else {

            slidemenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    public NewsCenterPager getNewsCenter(){
        NewsCenterPager newsCenterPager = (NewsCenterPager) mPagers.get(1);
        return  newsCenterPager;
    }



    class ContentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = mPagers.get(position).mView;
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
