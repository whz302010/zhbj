package base.impl.slide.tabNews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.acer.zhbj.Activity.NewsDetailActivity;
import com.example.acer.zhbj.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bean.NewsMenu;
import Bean.TabNews;
import base.SlideDetailPager;
import gloable.ConstentValue;
import gloable.MyApplication;
import util.HandleData;
import util.MyHttpUtil;
import util.SpreUtil;
import util.ToastUtil;
import view.PullToListView;

/**
 * Created by acer on 2017/7/27.
 */

public class TabDetailPager extends SlideDetailPager {
    private  NewsMenu.NewsTabData tabNewsData;
    private TextView textView;
    private PullToListView lv_hotNews;
    private ViewPager viewPager;
    private TextView tv_news_title;
    private CirclePageIndicator circlePageIndicator;
    private String tabDetailUrl;
    private ArrayList<TabNews.NewsTop> topnews;
    private ArrayList<TabNews.ListItem> listItems;
    private View headViewpager;
    private TabNews tabNews;
    private String moreUrl;
    private ListNewsAdapter mListAdapter;
    private Handler mHandler;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData data) {
        super(activity);
        this.tabNewsData=data;

    }

    @Override
    public View initView() {
        View view = View.inflate(activity, R.layout.tab_detail_pager, null);
        headViewpager = View.inflate(activity, R.layout.tab_detail_head, null);
        lv_hotNews = (PullToListView) view.findViewById(R.id.lv_tab);
        viewPager = (ViewPager) headViewpager.findViewById(R.id.vp_tab);
        tv_news_title = (TextView) headViewpager.findViewById(R.id.tv_hotnews_title);
        circlePageIndicator = (CirclePageIndicator) headViewpager.findViewById(R.id.circleindicator);
        lv_hotNews.addHeaderView(headViewpager);


        lv_hotNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int headerViewsCount = lv_hotNews.getHeaderViewsCount();
                position=position-headerViewsCount;
                TabNews.ListItem listItem = listItems.get(position);
                String read_id = SpreUtil.getString(activity, "read_id", "");
                if (!read_id.contains(String.valueOf(listItem.id))){
                    read_id=read_id+listItem.id+",";
                    SpreUtil.putString(activity,"read_id",read_id);

                }
                TextView tv_news  = (TextView) view.findViewById(R.id.tv_news);
                tv_news.setTextColor(Color.GRAY);

                Intent intent = new Intent(activity, NewsDetailActivity.class);
                intent.putExtra("url",listItems.get(position).url);
                activity.startActivity(intent);

            }
        });
        lv_hotNews.setOnRefreshListener(new PullToListView.onRefreshCompleted() {
            @Override
            public void onRefresh() {

                getDataFromServor();
            }

            @Override
            public void onLoadMore() {
                getMoreFromServor();
                isLoadMore=true;
            }
        });
        return view;
    }

    private void getMoreFromServor() {

        if (!TextUtils.isEmpty(moreUrl)){
            MyHttpUtil.requestHttp(moreUrl, new RequestCallBack() {
                @Override
                public void onSuccess(ResponseInfo responseInfo) {
                    String result = (String) responseInfo.result;
                    processData(result);
                    ToastUtil.showTaost("tabDetail更多数据网络请求成功",activity);
                    SpreUtil.putString(activity,moreUrl,result);
                    lv_hotNews.loadCompleted(true);
                    isLoadMore=false;
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    ToastUtil.showTaost("tabDetail更多数据网络请求失败",activity);
                    lv_hotNews.loadCompleted(true);
                    isLoadMore=false;
                }
            });
        }else {
                lv_hotNews.loadCompleted(false);
                isLoadMore=false;
        }
        }


    @Override
    public void initData() {
        super.initData();

        tabDetailUrl = ConstentValue.SERVAL_URL+tabNewsData.url;
        String cacheData = SpreUtil.getString(activity, tabDetailUrl, null);
        if (!TextUtils.isEmpty(cacheData)){
            processData(cacheData);
        }
          getDataFromServor();


            circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_news_title.setText(topnews.get(position).title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });



    }


    private void getDataFromServor() {

        MyHttpUtil.requestHttp(tabDetailUrl, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                String result = (String) responseInfo.result;
                processData(result);
                ToastUtil.showTaost("tabDetail网络请求成功",activity);
                SpreUtil.putString(activity,tabDetailUrl,result);
                lv_hotNews.onRefreshCompleted(true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                ToastUtil.showTaost("tadetail网络请求失败",activity);
                lv_hotNews.onRefreshCompleted(false);

            }
        });
    }
        private boolean isLoadMore=false;

        private void processData(String data) {
            try {
                    JSONObject jsonObject = new JSONObject(data);
                    tabNews = new Gson().fromJson(jsonObject.get("data").toString(), TabNews.class);
                    listItems=tabNews.news;
                    topnews=tabNews.topnews;
                    moreUrl=ConstentValue.CATEGORY_URL+tabNews.more;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!isLoadMore){
                    if (listItems!=null){
                        mListAdapter = new ListNewsAdapter();
                        lv_hotNews.setAdapter(mListAdapter);
                                }

                if (topnews!=null){
                    viewPager.setAdapter(new HotNewsAdapter());
                    circlePageIndicator.setViewPager(viewPager);
                    circlePageIndicator.setSnap(true);//快照模式
                    circlePageIndicator.setCurrentItem(0);
                    tv_news_title.setText(topnews.get(0).title);
                }

                    if (mHandler==null) {
                        mHandler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                int currentItem = viewPager.getCurrentItem();
                                currentItem++;
                                if (currentItem == viewPager.getChildCount() - 1) {
                                    currentItem = 0;
                                }
                                viewPager.setCurrentItem(currentItem);
                                sendEmptyMessageDelayed(0, 3000);
                            }
                        };
                        mHandler.sendEmptyMessageDelayed(0,3000);

                        viewPager.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()){
                                    case MotionEvent.ACTION_DOWN:
                                        mHandler.removeCallbacksAndMessages(null);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        mHandler.sendEmptyMessageDelayed(0,3000);
                                        break;
                                    case MotionEvent.ACTION_CANCEL:
                                        mHandler.sendEmptyMessageDelayed(0,3000);
                                        break;
                                }
                                return false;
                            }
                        });
                    }
            }else {
                listItems.addAll(listItems);
                mListAdapter.notifyDataSetChanged();

            }




    }

    class ListNewsAdapter extends BaseAdapter{

        private final BitmapUtils bitmapUtils;

        public ListNewsAdapter(){

            bitmapUtils = new BitmapUtils(activity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
}
        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public TabNews.ListItem getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView!=null){
                viewHolder= (ViewHolder) convertView.getTag();
            }else {
               convertView= View.inflate(activity,R.layout.tabnews_list_item,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_news_list);
                viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_news);
                viewHolder.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(viewHolder);
            }
            viewHolder.tv_title.setText(listItems.get(position).title);
            viewHolder.tv_date.setText(listItems.get(position).pubdate);
            String read_id = SpreUtil.getString(activity, "read_id", "");
            if (read_id.contains(String.valueOf(position))){
                viewHolder.tv_title.setTextColor(Color.GRAY);
            }else {
                viewHolder.tv_title.setTextColor(Color.BLACK);
            }
            bitmapUtils.display(viewHolder.imageView,listItems.get(position).listimage);
            return convertView;
        }
        class ViewHolder{
            ImageView imageView;
            TextView tv_title;
            TextView tv_date;
        }
    }

    class HotNewsAdapter extends PagerAdapter{
        //网络加载图片
        BitmapUtils bitmapUtils;
        public HotNewsAdapter(){

            bitmapUtils = new BitmapUtils(activity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);

}

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(activity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//設置模式狂傲填充父親窗體，xytils底層setimagerResourse
            bitmapUtils.display(imageView,topnews.get(position).topimage);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }

}
