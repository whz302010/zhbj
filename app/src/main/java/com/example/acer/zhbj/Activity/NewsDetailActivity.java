package com.example.acer.zhbj.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.example.acer.zhbj.R;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import util.SpreUtil;

public class NewsDetailActivity extends Activity implements View.OnClickListener{

    private ImageButton iv_share;
    private ImageButton iv_textsiez;
    private WebView wb;
    private ImageButton ib_back;
    private String [] mTextSizes;
    private int mTemptItem;
    private int mCurrentItem=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        findViewById(R.id.tv_bar_title).setVisibility(View.INVISIBLE);
        findViewById(R.id.ib_title).setVisibility(View.INVISIBLE);
        iv_share = (ImageButton) findViewById(R.id.ib_share);
        iv_textsiez = (ImageButton) findViewById(R.id.ib_font);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        wb = (WebView) findViewById(R.id.wb_news);
        ib_back.setVisibility(View.VISIBLE);
        iv_share.setVisibility(View.VISIBLE);
        iv_textsiez.setVisibility(View.VISIBLE);

        ib_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_textsiez.setOnClickListener(this);

        mTextSizes=new String[]{"超大字体","大字体","普通字体","小字体","超小号字体"};

        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//双击缩放
        settings.setBuiltInZoomControls(true);//显示缩放按钮
        String url = getIntent().getStringExtra("url");
        wb.loadUrl(url);
        wb.goBack();
        wb.goForward();
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        //加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        //所有链接都走次方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_font:
                selectTextSize();
                break;
            case R.id.ib_share:
               showShare();

                break;
        }
    }

    private void selectTextSize() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置字体");
        builder.setSingleChoiceItems(mTextSizes, mCurrentItem, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTemptItem = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebSettings settings = wb.getSettings();
                switch (mTemptItem){
                 case 0:
                     settings.setTextSize(WebSettings.TextSize.LARGEST);
                     break;
                 case 1:
                     settings.setTextSize(WebSettings.TextSize.LARGER);
                     break;
                 case 2:
                     settings.setTextSize(WebSettings.TextSize.NORMAL);
                     break;
                 case 3:
                     settings.setTextSize(WebSettings.TextSize.SMALLER);
                     break;
                 case 4:
                     settings.setTextSize(WebSettings.TextSize.SMALLEST);
                     break;
             }
               mCurrentItem=mTemptItem;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();

//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
