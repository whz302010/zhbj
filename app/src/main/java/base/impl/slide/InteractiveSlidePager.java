package base.impl.slide;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import base.SlideDetailPager;
import gloable.MyApplication;

/**
 * Created by acer on 2017/7/24.
 */

public class InteractiveSlidePager extends SlideDetailPager {


    public InteractiveSlidePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView=new TextView(MyApplication.getContext());
        textView.setText("互动");
        textView.setGravity(Gravity.CENTER);
        return  textView;
    }


}
