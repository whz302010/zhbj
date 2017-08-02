package util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by acer on 2017/7/22.
 */

public class ToastUtil {
    public static void showTaost(String toast,Context context){
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
    }
}
