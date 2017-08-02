package base.impl.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import base.BasePager;

/**
 * Created by acer on 2017/7/22.
 */

public class GovAffairsPager extends BasePager {


    public GovAffairsPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        TextView view=new TextView(mActivity);
        view.setText("政務");
        view.setTextColor(Color.RED);
        view.setTextSize(30);
        view.setGravity(Gravity.CENTER);
        fl_container.addView(view);
        tv_bar_title.setText("政務");
        ib_title.setVisibility(View.GONE);
    }
}
