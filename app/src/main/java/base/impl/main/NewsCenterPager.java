package base.impl.main;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.acer.zhbj.Activity.MainActivity;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;

import Bean.NewsMenu;
import base.BasePager;
import base.SlideDetailPager;
import base.impl.slide.InteractiveSlidePager;
import base.impl.slide.NewsSlidePager;
import base.impl.slide.PhotoSlidePager;
import base.impl.slide.TopicSLidePager;
import fragment.SlideFragment;
import gloable.ConstentValue;
import util.HandleData;
import util.MyHttpUtil;
import util.SpreUtil;
import util.ToastUtil;

/**
 * Created by acer on 2017/7/22.
 */

public class NewsCenterPager extends BasePager {
    private ArrayList<SlideDetailPager> mSlidePagers;
    private NewsMenu newsCenterData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {

        return super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        mSlidePagers=new ArrayList<>();
        //向服务器请求数据
        final String responseDate = SpreUtil.getString(mActivity, ConstentValue.CATEGORY_URL, null);
        if (!TextUtils.isEmpty(responseDate)){

            newsCenterData= HandleData.handle(responseDate);
            passToSlide(newsCenterData);

        }
        getDataFromServor();

        if (newsCenterData!=null){

            NewsSlidePager newsSlidePager = new NewsSlidePager(mActivity, newsCenterData.data.get(0).children);
            mSlidePagers.add(newsSlidePager);
            mSlidePagers.add(new TopicSLidePager(mActivity));
            mSlidePagers.add(new PhotoSlidePager(mActivity));
            mSlidePagers.add(new InteractiveSlidePager(mActivity));
            setCurrentDetailPager(0);
        }



    }

    private void passToSlide(NewsMenu newsMenu) {
        MainActivity activity= (MainActivity) mActivity;
        SlideFragment slideFragment = activity.getSlideFragment();
        slideFragment.setSlideData(newsMenu.data);

    }

    private void getDataFromServor() {
        MyHttpUtil.requestHttp(ConstentValue.CATEGORY_URL, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                String result = (String) responseInfo.result;
                newsCenterData = HandleData.handle(result);
                passToSlide(newsCenterData);
                ToastUtil.showTaost("新闻中心请求成功" ,mActivity);
                SpreUtil.putString(mActivity,ConstentValue.CATEGORY_URL,result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtil.showTaost("新闻中心网络请求失败",mActivity);
            }
        });
    }
    public void setCurrentDetailPager(int position){
        SlideDetailPager slideDetailPager = mSlidePagers.get(position);
        View mRootView = slideDetailPager.mRootView;
        fl_container.removeAllViews();
        fl_container.addView(mRootView);

        //初始化页面数据
        slideDetailPager.initData();

        //更新标题
        tv_bar_title.setText(newsCenterData.data.get(position).title);
    }
}
