package base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.acer.zhbj.Activity.MainActivity;
import com.example.acer.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import util.ToastUtil;

/**
 * Created by acer on 2017/7/22.
 */

public class BasePager {
    public Activity mActivity;
    public ImageButton ib_title;
    public TextView tv_bar_title;
    public FrameLayout fl_container;
    private MainActivity UiMain;
    public View mView;

    public BasePager(Activity activity){
        mActivity=activity;
        UiMain= (MainActivity) activity;

        initView();
    }
    public void initData(){
    ib_title.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            toggle();


        }
    });
    }

    private void toggle() {

         SlidingMenu slideMenu = UiMain.slidemenu;

          slideMenu.toggle();


    }

    public View initView(){
        mView = View.inflate(mActivity, R.layout.base_pager, null);
        ib_title = (ImageButton) mView.findViewById(R.id.ib_title);
        tv_bar_title = (TextView) mView.findViewById(R.id.tv_bar_title);
        fl_container = (FrameLayout) mView.findViewById(R.id.vp_container);
        return mView;
    }
}
