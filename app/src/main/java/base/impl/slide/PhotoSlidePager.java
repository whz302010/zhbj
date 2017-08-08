package base.impl.slide;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zhbj.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bean.PhotoItem;
import base.SlideDetailPager;
import gloable.ConstentValue;
import gloable.MyApplication;
import util.MyHttpUtil;

/**
 * Created by acer on 2017/7/24.
 */

public class PhotoSlidePager extends SlideDetailPager {

    private final ImageButton btn;
    @ViewInject(R.id.lv_photo)
    private ListView lv_photo;
    @ViewInject(R.id.gv_photo)
    private GridView gv_photo;
    private PhotoItem photoItem;
    private ArrayList<PhotoItem.PhotoData> newsList;
    private boolean isListView=true;

    public PhotoSlidePager(Activity activity, ImageButton ib_list_grid) {
        super(activity);
        this.btn=ib_list_grid;
    }

    @Override
    public View initView() {
        View inflate = View.inflate(activity, R.layout.photo_slide_pager, null);
        ViewUtils.inject(this,inflate);

        return  inflate;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromServor();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isListView){
                    lv_photo.setVisibility(View.GONE);
                    gv_photo.setVisibility(View.VISIBLE);
                    btn.setBackgroundResource(R.drawable.icon_pic_list_type);
                    isListView=false;
                }else {
                    lv_photo.setVisibility(View.VISIBLE);
                    gv_photo.setVisibility(View.GONE);
                    btn.setBackgroundResource(R.drawable.icon_pic_grid_type);
                    isListView=true;
                }
            }
        });

    }

    private void getDataFromServor(){
        MyHttpUtil.requestHttp(ConstentValue.PHOTO_URL, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                String result = (String) responseInfo.result;
                processData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(activity,"組圖獲取失敗",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processData(String result) {

        try {
            Gson gson = new Gson();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject data = (JSONObject) jsonObject.get("data");
            photoItem = gson.fromJson(jsonObject.toString(), PhotoItem.class);
            newsList = photoItem.news;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (newsList!=null){
                lv_photo.setAdapter(new PhotoListAdapter());
                 gv_photo.setAdapter(new PhotoListAdapter());
        }
    }
    private class PhotoListAdapter extends BaseAdapter{

        private final BitmapUtils bitmapUtils;

        private PhotoListAdapter(){
            bitmapUtils = new BitmapUtils(activity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public PhotoItem.PhotoData getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if (convertView==null){
                holder=new ViewHolder();
                convertView=View.inflate(activity,R.layout.photo_slide_item,null);
                holder.imageView= (ImageView) convertView.findViewById(R.id.iv_photo);
                holder.textView= (TextView) convertView.findViewById(R.id.tv_photo_title);
                convertView.setTag(holder);
            }else {
                convertView.getTag();
            }
            bitmapUtils.display(holder.imageView,newsList.get(position).listimage);
            holder.textView.setText(newsList.get(position).title);
            return convertView;
        }
        class ViewHolder{
            ImageView imageView;
            TextView textView;
        }
    }
}
