package Bean;

import java.util.ArrayList;

/**
 * Created by acer on 2017/7/23.
 */

public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> data;


 public    class  NewsMenuData{
     public ArrayList<NewsTabData> children;
     public int id;
     public String title;
     public int type;
 }
 public class  NewsTabData{
     public int id;
     public String title;
     public int type;
     public String url;

 }
}
