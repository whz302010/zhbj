package gloable;

import android.app.Application;
import android.content.Context;

import com.mob.MobApplication;
import com.mob.commons.SHARESDK;

/**
 * Created by acer on 2017/7/22.
 */

public class MyApplication extends MobApplication {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();

    }

    public static Context getContext(){
       return context;
    }
}
