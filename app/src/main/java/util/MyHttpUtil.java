package util;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by acer on 2017/7/23.
 */

public class MyHttpUtil {

    public static void requestHttp(String address, RequestCallBack callBack) {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, address, callBack);

    }
}
