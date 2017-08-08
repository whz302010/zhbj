package view;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.acer.zhbj.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Date;

/**
 * Created by acer on 2017/8/2.
 */

public class PullToListView extends ListView implements AbsListView.OnScrollListener{

    private int startY=-1;
    private final int STATE_PULL_TO_REFRESH=1;
    private final int STATE_RELEASE_TO_REFREASH=2;
    private final int STATE_REFRESHING=3;
    private int mCurrentState;
    private View view;
    private int measuredHeight;
    @ViewInject(R.id.tv_title)
    private TextView tv_titl;
    @ViewInject(R.id.tv_time)
    private TextView tv_date;
    @ViewInject(R.id.pb_loading)
    private ProgressBar pb_loading;
    @ViewInject(R.id.iv_arr)
    private ImageView iv_arr;
    @ViewInject(R.id.pb_footer)
    private ProgressBar pb_footer;
  @ViewInject(R.id.tv_loadmore)
    private TextView tv_loadmore;
    private RotateAnimation rotateUP;
    private RotateAnimation rotateDown;
    public onRefreshCompleted refreshListener;
    private View footView;
    private int mFooterHeight;
    private boolean isLoadMore=false;


    public PullToListView(Context context) {
        super(context);
        initHeadView();
        initFooter();
    }


    public PullToListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadView();
        initFooter();
    }
    public PullToListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
        initFooter();
    }

    private void initFooter() {
        footView = View.inflate(getContext(), R.layout.footer_pull_to_refresh, null);
        footView.measure(0,0);
        mFooterHeight = footView.getMeasuredHeight();
        ViewUtils.inject(this,footView);
        footView.setPadding(0,0,0,-mFooterHeight);
        addFooterView(footView);
        this.setOnScrollListener(this);

    }

    private void initHeadView() {
        view = View.inflate(getContext(), R.layout.head_pull_to_refresh, null);
        ViewUtils.inject(this,view);
        addHeaderView(view);
        view.measure(0,0);
        measuredHeight = view.getMeasuredHeight();
        view.setPadding(0,-measuredHeight,0,0);

        rotateUP = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateUP.setDuration(200);
        rotateUP.setFillAfter(true);
        rotateDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateUP.setDuration(200);
        rotateDown.setFillAfter(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
               startY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY==-1){
                    startY= (int) ev.getY();
                }
                if (mCurrentState==STATE_REFRESHING){
                    break;
                }

                if (isLoadMore){
                    break;
                }

                int endY= (int) ev.getY();
                int dy=endY-startY;
                int firstVisiblePosition = getFirstVisiblePosition();
                int padding=dy-measuredHeight;
                if (dy>0&&firstVisiblePosition==0){
                    view.setPadding(0,padding,0,0);
                    if (dy>measuredHeight+10&&mCurrentState!=STATE_RELEASE_TO_REFREASH){
                        mCurrentState=STATE_RELEASE_TO_REFREASH;
                        refresh();
                    }else {
                        if (mCurrentState!=STATE_PULL_TO_REFRESH){
                        mCurrentState=STATE_PULL_TO_REFRESH;
                        refresh();
                        }
                    }
                }
//
//            startY= (int) ev.getY();
                break;

            case MotionEvent.ACTION_UP:
                startY=-1;
                if (mCurrentState==STATE_RELEASE_TO_REFREASH){
                    mCurrentState=STATE_REFRESHING;
                    refresh();
                }else if (mCurrentState==STATE_PULL_TO_REFRESH){
                 view.setPadding(0,-measuredHeight,0,0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refresh() {
            switch (mCurrentState){
                case STATE_PULL_TO_REFRESH:
                    tv_titl.setText("下拉刷新");
                    iv_arr.setVisibility(VISIBLE);
                    pb_loading.setVisibility(INVISIBLE);
                    iv_arr.startAnimation(rotateDown);
                    break;
                case STATE_RELEASE_TO_REFREASH:
                    tv_titl.setText("释放刷新");
                    iv_arr.setVisibility(VISIBLE);
                    pb_loading.setVisibility(INVISIBLE);
                    iv_arr.startAnimation(rotateUP);
                    break;
                case STATE_REFRESHING:
                    view.setPadding(0,0,0,0);
                    iv_arr.setVisibility(INVISIBLE);
                    pb_loading.setVisibility(VISIBLE);
                    tv_titl.setText("刷新中.....");
                    refreshListener.onRefresh();
                    iv_arr.clearAnimation();
                    break;
            }
    }
    //滑动状态改变
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
       if (scrollState==SCROLL_STATE_IDLE){
           if (getLastVisiblePosition()==getCount()-1){

               footView.setPadding(0,0,0,0);
               setSelection(getCount()-1);
               refreshListener.onLoadMore();
               isLoadMore=true;
           }
       }

    }
//滑动过程监听
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface onRefreshCompleted{
        void onRefresh( );
        void onLoadMore();
    }
    public void setOnRefreshListener(onRefreshCompleted onRefreshListener){
        this.refreshListener=onRefreshListener;
    }
    public void setCurrentDate(){
        SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(new Date());
        tv_date.setText(format);

    }
    public void onRefreshCompleted(boolean success){
        view.setPadding(0,-measuredHeight,0,0);
        iv_arr.setVisibility(VISIBLE);
        pb_loading.setVisibility(INVISIBLE);
        mCurrentState=STATE_PULL_TO_REFRESH;
        tv_titl.setText("下拉刷新");
        if (success){
            setCurrentDate();
        }
    }
    public void loadCompleted(boolean hasMore){
        isLoadMore=false;
        if (hasMore){
            footView.setPadding(0,0,0,-mFooterHeight);
        }else {
            pb_loading.setVisibility(GONE);
            tv_loadmore.setText("没有更多数据了。。。。。");

        }
    }

}
