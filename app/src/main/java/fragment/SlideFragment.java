package fragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.zhbj.Activity.MainActivity;
import com.example.acer.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;




import java.util.ArrayList;

import Bean.NewsMenu;
import base.impl.main.NewsCenterPager;
import util.ToastUtil;

/**
 * Created by acer on 2017/7/22.
 */

public class SlideFragment extends BaseFragment {
    @ViewInject(R.id.lv_left_menu)
    private ListView lv_left_menu;
    private ArrayList<NewsMenu.NewsMenuData> data;
    private SlideAdapter slideAdapter;
    private int mCurrentPosition;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_slide, null);
      //xutils view框架
        ViewUtils.inject(this,view);



        return view;
    }

    @Override
    public void initData() {

    }

    public void setSlideData(ArrayList<NewsMenu.NewsMenuData> data){
        this.data=data;
        mCurrentPosition=0;
        slideAdapter = new SlideAdapter();
        lv_left_menu.setAdapter(slideAdapter);

        lv_left_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentPosition = i;
               slideAdapter.notifyDataSetChanged();
                //侧边栏关
                toggle();


                //切換到新聞頁
//               MainActivity mainActivity= (MainActivity) mActivity;
//                ContentFragment contentFragment = mainActivity.getContentFragment();
//                contentFragment.setCurrentPage(1);
                //刷新新闻页FramLayout
                setCurrentNews(mCurrentPosition);


            }
        });

    }

    private void setCurrentNews(int position ) {
        MainActivity activity= (MainActivity) mActivity;
        ContentFragment contentFragment = activity.getContentFragment();
        NewsCenterPager newsCenter = contentFragment.getNewsCenter();
        newsCenter.setCurrentDetailPager(position);


    }

    private void toggle() {
      MainActivity activity= (MainActivity) mActivity;
        SlidingMenu slideMenu = activity.slidemenu;
        slideMenu.toggle();

    }

    class SlideAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public NewsMenu.NewsMenuData getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {

            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(getContext(), R.layout.slide_list_item, null);
            TextView tv_item= (TextView) view.findViewById(R.id.tv_slide_menu);
            tv_item.setText(data.get(i).title);


            if (mCurrentPosition==i){
                tv_item.setEnabled(true);//文字变红
            }else {
                tv_item.setEnabled(false);//文字变白
            }
            return view;
        }
    }
}
