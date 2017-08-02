package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by acer on 2017/7/30.
 */

public class TopNewsViewPager extends ViewPager {

    private float startY;
    private float startX;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int currentItem = getCurrentItem();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                float endX=ev.getX();
                float endY=ev.getY();
                if (Math.abs(endX- startX)>Math.abs(endY-startY)){
                    //左右滑動
                    if (endX>startX){
                        //向左劃動
                        if (currentItem==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else {
                        //向右滑動
                        if (currentItem==getChildCount()-1){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                startX = ev.getX();
                startY = ev.getY();
                break;

        }

        return super.dispatchTouchEvent(ev);
    }
}
