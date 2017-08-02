package util;

import com.google.gson.Gson;

import Bean.NewsMenu;

/**
 * Created by acer on 2017/7/23.
 */

public class HandleData {
    public static NewsMenu handle(String data){
        Gson gson = new Gson();
        NewsMenu newsMenu = gson.fromJson(data, NewsMenu.class);
        return newsMenu;
    }
}
