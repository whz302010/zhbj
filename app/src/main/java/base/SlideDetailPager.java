package base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by acer on 2017/7/24.
 */

public abstract class SlideDetailPager {
    public  Activity activity;
    public View mRootView;


    public SlideDetailPager(Activity activity){
        this.activity=activity;
        mRootView= initView();
    }



    public abstract View initView();
    public void initData(){

    };
}
